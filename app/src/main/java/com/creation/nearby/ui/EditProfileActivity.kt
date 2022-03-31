package com.creation.nearby.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ListAdapter
import android.widget.ListPopupWindow
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.adapter.EditProfileInterestAdapter
import com.creation.nearby.adapter.ImageAdapter
import com.creation.nearby.adapter.ManualInterestAdapter
import com.creation.nearby.adapter.ZodiacAdapter
import com.creation.nearby.databinding.ActivityEditProfileBinding
import com.creation.nearby.getZodiac
import com.creation.nearby.model.*
import com.creation.nearby.showToast
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.ImagePickerUtility
import com.creation.nearby.viewmodel.ProfileVM
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class EditProfileActivity : ImagePickerUtility(), View.OnClickListener {

    private lateinit var imageAdapter: ImageAdapter

    private val popupList = ArrayList<PopupModel>()
    private var cal: Calendar = Calendar.getInstance()

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var popup: ListPopupWindow
    private lateinit var zodiacAdapter: ListAdapter

    private var interestsList = ArrayList<InterestListResponse.Body>()
    private val interestGenericList = ArrayList<GenericModel>()
    private lateinit var interestsAdapter: EditProfileInterestAdapter
    private val manualInterestList = ArrayList<ManualInterestModel>()
    private lateinit var manualInterestAdapter: ManualInterestAdapter

    private var responseImageList: MutableList<FileUploadModel.Body> = ArrayList()
    private var imageArrString: String = ""
    private var latitude: String = ""
    private var longitude: String = ""
    private var selectedGender: String = ""
    private var selectedZodiac: String = ""
    private var selectedInterestedIn: String = ""
    private var profileImage: String = ""
    private var jsonArr = JSONArray()
    private var selectedImageList: ArrayList<String> = ArrayList()

    private var isMainPhoto: Boolean = false

    private val profileViewModel: ProfileVM by viewModels()

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data

                data?.let {
                    val place = Autocomplete.getPlaceFromIntent(result.data!!)
                    latitude = place.latLng!!.latitude.toString()
                    longitude = place.latLng!!.longitude.toString()
                    binding.userLocation.setText(place.name.toString())
                }
            }
        }

    override fun selectedImage(imagePath: String?) {

        if (isMainPhoto) {
            isMainPhoto = false
            Glide.with(this).load(imagePath).into(binding.userProfilePic)

            profileViewModel.profileImage.set(imagePath)
            //api call for upload image
            profileViewModel.uploadProfileImageApi(this)

        } else {
            selectedImageList.clear()
            selectedImageList.add(imagePath.toString())
            //set image path
            profileViewModel.image.set(imagePath)
            //api call for upload image
            profileViewModel.uploadGalleryImageApi(this)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        binding.profileVM = profileViewModel
        setContentView(binding.root)
        val apiKey = getString(R.string.map_key)
        Places.initialize(this, apiKey)

        initAdapter()

        popupList.add(PopupModel("Aries"))
        popupList.add(PopupModel("Taurus"))
        popupList.add(PopupModel("Gemini"))
        popupList.add(PopupModel("Cancer"))
        popupList.add(PopupModel("Leo"))
        popupList.add(PopupModel("Virgo"))
        popupList.add(PopupModel("Libra"))
        popupList.add(PopupModel("Scorpio"))
        popupList.add(PopupModel("Sagittarius"))
        popupList.add(PopupModel("Capricorn"))
        popupList.add(PopupModel("Aquarius"))
        popupList.add(PopupModel("Pisces"))

        popup = ListPopupWindow(this)
        zodiacAdapter = ZodiacAdapter(popupList)

        popup.setOnItemClickListener { parent, view, position, id ->
            popup.dismiss()
            val name = parent.getItemAtPosition(position).toString()
            binding.zodiacTv.text = name.substring(22, name.length - 1)
            selectedZodiac = binding.zodiacTv.text.toString().trim()
        }

        binding.selectZodiacLayout.setOnClickListener(this)
        binding.maleGender.setOnClickListener(this)
        binding.femaleGender.setOnClickListener(this)
        binding.otherGender.setOnClickListener(this)
        binding.maleLooking.setOnClickListener(this)
        binding.femaleLooking.setOnClickListener(this)
        binding.bothLooking.setOnClickListener(this)
        binding.userProfilePic.setOnClickListener(this)
        binding.backBtn2.setOnClickListener(this)
        binding.finishBtn.setOnClickListener(this)
        binding.cameraPictureBtn.setOnClickListener(this)
        binding.editTextDOB.setOnClickListener(this)
        binding.ivAddImage.setOnClickListener(this)
        binding.ivManualInterest.setOnClickListener(this)
        binding.userLocation.setOnClickListener(this)

        //api call
        profileViewModel.getInterests(this)


        //observe api response
        profileViewModel.getInterestListResponse.observeForever { response ->
            response?.let {
                interestsList.addAll(it)
                //get profile detail api call
                profileViewModel.getProfileApi(this)
            }
        }

        profileViewModel.profileData.observeForever { response ->
            response?.let { profileRes ->

                val profileImage = profileRes.body?.image
                if (profileImage?.isNotEmpty() == true) {
                    Glide.with(this)
                        .load("${Constants.IMAGE_BASE_URL}$profileImage")
                        .placeholder(R.drawable.placeholder)
                        .into(binding.userProfilePic)
                }

                binding.etFirstName.setText(profileRes.body?.firstname)
                binding.etLastName.setText(profileRes.body?.lastname)
                if (profileRes.body?.countryCode!!.isNotEmpty()) {
                    binding.countryCodePicker.setCountryForPhoneCode(profileRes.body.countryCode.toInt())
                }
                binding.etPhoneNumber.setText(profileRes.body.phone)
                binding.editTextDOB.setText(profileRes.body.dob)
                binding.editTextDiscription.setText(profileRes.body.biography)
                val zodiac = profileRes.body.zodiac
                if (zodiac?.isNotEmpty() == true) {
                    binding.zodiacTv.text = zodiac
                }
                selectedZodiac = zodiac.toString()
                binding.userLocation.setText(profileRes.body.location)
                latitude = profileRes.body.latitude.toString()
                longitude = profileRes.body.longitude.toString()

                when (profileRes.body.gender) {
                    1 -> {
                        selectedGender = "1"
                        binding.maleGender.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.sky_blue)
                        binding.maleGender.setTextColor(ContextCompat.getColor(this, R.color.white))
                        binding.femaleGender.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.femaleGender.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.black
                            )
                        )
                        binding.otherGender.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.otherGender.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.black
                            )
                        )
                    }
                    2 -> {
                        selectedGender = "2"
                        binding.maleGender.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.maleGender.setTextColor(ContextCompat.getColor(this, R.color.black))
                        binding.femaleGender.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.sky_blue)
                        binding.femaleGender.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        binding.otherGender.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.otherGender.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.black
                            )
                        )
                    }
                    3 -> {
                        selectedGender = "3"
                        binding.maleGender.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.maleGender.setTextColor(ContextCompat.getColor(this, R.color.black))
                        binding.femaleGender.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.femaleGender.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.black
                            )
                        )
                        binding.otherGender.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.sky_blue)
                        binding.otherGender.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                    }
                }

                when (profileRes.body.intrestedIn) {
                    "Men" -> {
                        selectedInterestedIn = "Men"
                        binding.maleLooking.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.sky_blue)
                        binding.maleTv.setTextColor(ContextCompat.getColor(this, R.color.white))
                        binding.maleColor.imageTintList =
                            ActivityCompat.getColorStateList(this, R.color.white)
                        binding.femaleLooking.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.femaleTv.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.black
                            )
                        )
                        binding.femaleColor.imageTintList =
                            ActivityCompat.getColorStateList(this, R.color.black)
                        binding.bothLooking.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.bothTv.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.black
                            )
                        )
                        binding.bothColor.imageTintList =
                            ActivityCompat.getColorStateList(this, R.color.black)
                    }
                    "Women" -> {
                        selectedInterestedIn = "Women"
                        binding.maleLooking.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.maleTv.setTextColor(ContextCompat.getColor(this, R.color.black))
                        binding.maleColor.imageTintList =
                            ActivityCompat.getColorStateList(this, R.color.black)
                        binding.femaleLooking.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.sky_blue)
                        binding.femaleTv.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        binding.femaleColor.imageTintList =
                            ActivityCompat.getColorStateList(this, R.color.white)
                        binding.bothLooking.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.bothTv.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.black
                            )
                        )
                        binding.bothColor.imageTintList =
                            ActivityCompat.getColorStateList(this, R.color.black)
                    }
                    "Both" -> {
                        selectedInterestedIn = "Both"
                        binding.maleLooking.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.maleTv.setTextColor(ContextCompat.getColor(this, R.color.black))
                        binding.maleColor.imageTintList =
                            ActivityCompat.getColorStateList(this, R.color.black)
                        binding.femaleLooking.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.edittext_grey)
                        binding.femaleTv.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.black
                            )
                        )
                        binding.femaleColor.imageTintList =
                            ActivityCompat.getColorStateList(this, R.color.black)
                        binding.bothLooking.backgroundTintList =
                            ContextCompat.getColorStateList(this, R.color.sky_blue)
                        binding.bothTv.setTextColor(
                            ContextCompat.getColor(
                                this,
                                R.color.white
                            )
                        )
                        binding.bothColor.imageTintList =
                            ActivityCompat.getColorStateList(this, R.color.white)
                    }
                }

                /*set interests*/
                interestGenericList.clear()
                val interests = profileRes.body.interests
                if (interests?.isNotEmpty() == true) {
                    val myList = interests.split(",")
                    for (index in interestsList.indices) {
                        if (myList.contains(interestsList[index].name)) {
                            interestGenericList.add(
                                GenericModel(
                                    interestsList[index].id,
                                    interestsList[index].name,
                                    1
                                )
                            )
                        } else {
                            interestGenericList.add(
                                GenericModel(
                                    interestsList[index].id,
                                    interestsList[index].name,
                                    0
                                )
                            )
                        }
                    }
                    interestsAdapter.notifyDataSetChanged()
                }
                /**
                 * @author Pardeep Sharma
                 * send list , if there is no interests
                 */
                else{
                     for (i in interestsList.indices){
                         interestGenericList.add(
                             GenericModel(
                                 interestsList[i].id,
                                 interestsList[i].name,
                                 0
                             )
                         )
                     }
                    interestsAdapter.notifyDataSetChanged()
                }

                /*set interests*/
//                val manualInterest = profileRes.body.manualInterest
//                manualInterestList.clear()
//                if (manualInterest?.isNotEmpty() == true) {
//                    val myManualInterestList = manualInterest.split(",")
//                    if (myManualInterestList.isNotEmpty()) {
//                        //for multiple value
//                        for (i in myManualInterestList.indices) {
//                            manualInterestList.add(ManualInterestModel(myManualInterestList[i], 1))
//                        }
//                    } else {
//                        manualInterestList.add(
//                            ManualInterestModel(
//                                manualInterest,
//                                1
//                            )
//                        )  //for single value
//                    }
//                    manualInterestAdapter.notifyDataSetChanged()
//                }

                /*gallery images parsing*/
                val galleryImages: MutableList<GetProfileResponse.Body.UserImage> =
                    ArrayList()
                galleryImages.addAll(profileRes.body.userImages as MutableList<GetProfileResponse.Body.UserImage>)
                responseImageList.clear()

                for (i in galleryImages.indices) {

                    val image = profileRes.body.userImages[i].image
                    val str = image?.replace(".png", "")

                    val fileUpload = FileUploadModel.Body(
                        str.toString(),
                        "image",
                        "booking",
                        profileRes.body.userImages[i].image.toString(),
                        profileRes.body.userImages[i].image.toString()
                    )
                    responseImageList.add(fileUpload)
                }
                imageAdapter.notifyDataSetChanged()
            }
        }

        profileViewModel.galleryImageUploadResponse.observeForever { response ->
            response?.let {
                responseImageList.addAll(it.body as MutableList<FileUploadModel.Body>)
                binding.myGallaryRecyclerView.smoothScrollToPosition((responseImageList.size) - 1)
                imageAdapter.notifyDataSetChanged()
            }
        }

        profileViewModel.profileImageUploadResponse.observeForever { response ->
            response?.let {
                profileImage = it.body[0].image

                Glide.with(this)
                    .load("${Constants.IMAGE_BASE_URL}$profileImage")
                    .placeholder(R.drawable.placeholder)
                    .into(binding.userProfilePic)
            }
        }

        profileViewModel.editProfileResponse.observeForever { response ->
            showToast(response.message.toString())
            finish()
        }
    }

    private fun initAdapter() {

        /* val onActionListener = object : OnActionListener<ImageModel> {
             override fun notify(model: ImageModel, position: Int,view: View) {

                 pos = position
                 when(view.id){
                     R.id.layoutAdd->{
                             optionsDialog()

                     }
                     R.id.close_image->{
                         images.removeAt(position)
                         imageAdapter?.notifyDataSetChanged()
                     }
                 }

             }
         }*/

        imageAdapter =
            ImageAdapter(this, responseImageList, object : ImageAdapter.OnItemClickListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onRemoveImage(position: Int) {
                    responseImageList.removeAt(position)
                    imageAdapter.notifyDataSetChanged()
                }
            })
        binding.myGallaryRecyclerView.adapter = imageAdapter

        interestsAdapter = EditProfileInterestAdapter(
            this,
            interestGenericList)
        binding.interestsRecyclerView.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW)
        binding.interestsRecyclerView.adapter = interestsAdapter

        manualInterestAdapter = ManualInterestAdapter(
            this,
            manualInterestList)
        binding.manualInterestsRecyclerView.layoutManager =
            FlexboxLayoutManager(this, FlexDirection.ROW)
        binding.manualInterestsRecyclerView.adapter = manualInterestAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onClick(v: View?) {

        when (v) {
            binding.backBtn2 -> {
                onBackPressed()
            }
            binding.finishBtn -> {

                /**
                 * converting model class response to json array (backend requirement)
                 */

                jsonArr = JSONArray()
                imageArrString = if (responseImageList.isNotEmpty()) {
                    for (i in responseImageList.indices) {
                        val gson = Gson()
                        val json = gson.toJson(responseImageList[i])
                        try {
                            val request = JSONObject(json)
                            jsonArr.put(request)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    jsonArr.toString()
                } else {
                    ""
                }

                val editProfileRequest = EditProfileRequest().also {
                    it.firstname = binding.etFirstName.text.toString().trim()
                  //  it.lastname = binding.etLastName.text.toString().trim()
                    it.countryCode = binding.countryCodePicker.selectedCountryCodeWithPlus
                    it.phone = binding.etPhoneNumber.text.toString().trim()
                    it.dob = binding.editTextDOB.text.toString().trim()
                    it.location = binding.userLocation.text.toString().trim()
                    it.latitude = latitude
                    it.longitude = longitude
                    it.gender = selectedGender
                    it.biography = binding.biograph.text.toString().trim()
                    it.gallary_images = imageArrString
                    it.zodiac = selectedZodiac
                    it.intrested_in = selectedInterestedIn
                    if (profileImage!="")
                    it.image = profileImage
                }
                //api call
                profileViewModel.editProfileApi(
                    this,
                    editProfileRequest,
                    interestGenericList,
                    manualInterestList
                )
            }
            binding.userProfilePic -> {
                isMainPhoto = true
                getImage(this, 0)
            }
            binding.cameraPictureBtn -> {
                isMainPhoto = true
                getImage(this, 0)
            }
            binding.editTextDOB -> {
                datePickerDialog()
            }
            binding.selectZodiacLayout -> {
                popup.anchorView = v
                popup.isModal = true
                popup.width = binding.selectZodiacLayout.width
                popup.setAdapter(zodiacAdapter)
                popup.show()
            }
            binding.maleGender -> {
                selectedGender = "1"
                binding.maleGender.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.sky_blue)
                binding.maleGender.setTextColor(ContextCompat.getColor(this, R.color.white))

                binding.femaleGender.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.edittext_grey)
                binding.femaleGender.setTextColor(ContextCompat.getColor(this, R.color.black))

                binding.otherGender.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.edittext_grey)
                binding.otherGender.setTextColor(ContextCompat.getColor(this, R.color.black))

            }
            binding.femaleGender -> {
                selectedGender = "2"
                binding.maleGender.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.edittext_grey)
                binding.maleGender.setTextColor(ContextCompat.getColor(this, R.color.black))

                binding.femaleGender.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.sky_blue)
                binding.femaleGender.setTextColor(ContextCompat.getColor(this, R.color.white))

                binding.otherGender.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.edittext_grey)
                binding.otherGender.setTextColor(ContextCompat.getColor(this, R.color.black))

            }
            binding.otherGender -> {
                selectedGender = "3"
                binding.maleGender.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.edittext_grey)
                binding.maleGender.setTextColor(ContextCompat.getColor(this, R.color.black))

                binding.femaleGender.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.edittext_grey)
                binding.femaleGender.setTextColor(ContextCompat.getColor(this, R.color.black))

                binding.otherGender.backgroundTintList =
                    ContextCompat.getColorStateList(this, R.color.sky_blue)
                binding.otherGender.setTextColor(ContextCompat.getColor(this, R.color.white))

            }
            binding.maleLooking -> {
                selectedInterestedIn = "Men"
                binding.maleLooking.backgroundTintList =
                    ActivityCompat.getColorStateList(this, R.color.sky_blue)
                binding.maleTv.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.maleColor.imageTintList =
                    ActivityCompat.getColorStateList(this, R.color.white)

                binding.femaleLooking.backgroundTintList =
                    ActivityCompat.getColorStateList(this, R.color.edittext_grey)
                binding.femaleTv.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.femaleColor.imageTintList =
                    ActivityCompat.getColorStateList(this, R.color.black)

                binding.bothLooking.backgroundTintList =
                    ActivityCompat.getColorStateList(this, R.color.edittext_grey)
                binding.bothTv.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.bothColor.imageTintList =
                    ActivityCompat.getColorStateList(this, R.color.black)


            }
            binding.femaleLooking -> {
                selectedInterestedIn = "Women"
                binding.maleLooking.backgroundTintList =
                    ActivityCompat.getColorStateList(this, R.color.edittext_grey)
                binding.maleTv.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.maleColor.imageTintList =
                    ActivityCompat.getColorStateList(this, R.color.black)

                binding.femaleLooking.backgroundTintList =
                    ActivityCompat.getColorStateList(this, R.color.sky_blue)
                binding.femaleTv.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.femaleColor.imageTintList =
                    ActivityCompat.getColorStateList(this, R.color.white)

                binding.bothLooking.backgroundTintList =
                    ActivityCompat.getColorStateList(this, R.color.edittext_grey)
                binding.bothTv.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.bothColor.imageTintList =
                    ActivityCompat.getColorStateList(this, R.color.black)

            }
            binding.bothLooking -> {
                selectedInterestedIn = "Both"
                binding.maleLooking.backgroundTintList =
                    ActivityCompat.getColorStateList(this, R.color.edittext_grey)
                binding.maleTv.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.maleColor.imageTintList =
                    ActivityCompat.getColorStateList(this, R.color.black)

                binding.femaleLooking.backgroundTintList =
                    ActivityCompat.getColorStateList(this, R.color.edittext_grey)
                binding.femaleTv.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.femaleColor.imageTintList =
                    ActivityCompat.getColorStateList(this, R.color.black)

                binding.bothLooking.backgroundTintList =
                    ActivityCompat.getColorStateList(this, R.color.sky_blue)
                binding.bothTv.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.bothColor.imageTintList =
                    ActivityCompat.getColorStateList(this, R.color.white)
            }
            binding.ivAddImage -> {
                getImage(this, 0)
            }
            binding.ivManualInterest -> {
                val manualInterest = binding.etEnterInterests.text.toString()
                if (manualInterest.isNotEmpty()) {
                    manualInterestList.add(ManualInterestModel(manualInterest, 1))
                    binding.etEnterInterests.setText("")
                }
                manualInterestAdapter.notifyDataSetChanged()
            }
            binding.userLocation -> {
                val fields = listOf(
                    Place.Field.ID,
                    Place.Field.NAME,
                    Place.Field.LAT_LNG,
                    Place.Field.ADDRESS_COMPONENTS,
                    Place.Field.ADDRESS
                )

                val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(this)
                resultLauncher.launch(intent)
            }
        }
    }

    private fun datePickerDialog() {
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                updateDateInView()
            }
        binding.editTextDOB.setOnClickListener {
          val picker =  DatePickerDialog(
                this, R.style.DialogTimePicker,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            )
            picker.datePicker.maxDate = System.currentTimeMillis()
            picker.show()
        }

    }

    private fun updateDateInView() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.editTextDOB.setText(sdf.format(cal.time))

        /**
         * Set Zodiac According to Date of Birth
         */
        val day = sdf.format(cal.time).split("-")[2]
        val month = sdf.format(cal.time).split("-")[1]

        binding.zodiacTv.text = getZodiac(day.toInt(),month.toInt())
        selectedZodiac = binding.zodiacTv.text.toString().trim()
    }
}

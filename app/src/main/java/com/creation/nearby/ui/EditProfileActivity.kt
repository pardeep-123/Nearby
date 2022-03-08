package com.creation.nearby.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListAdapter
import android.widget.ListPopupWindow
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.adapter.ImageAdapter
import com.creation.nearby.adapter.InterestsAdapter
import com.creation.nearby.adapter.ZodiacAdapter
import com.creation.nearby.databinding.ActivityEditProfileBinding
import com.creation.nearby.model.ImageModel
import com.creation.nearby.model.InterestedModel
import com.creation.nearby.model.PopupModel
import com.creation.nearby.utils.AppUtils
import com.creation.nearby.utils.ImagePickerUtility
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.permissionx.guolindev.PermissionX
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class EditProfileActivity : ImagePickerUtility(),View.OnClickListener {

    private var images = ArrayList<ImageModel>()
    private lateinit var imageAdapter: ImageAdapter

    private val popupList  = ArrayList<PopupModel>()
    var cal: Calendar = Calendar.getInstance()

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var popup: ListPopupWindow
    private lateinit var zodiacAdapter: ListAdapter

    private var interestsList = ArrayList<InterestedModel>()
    private lateinit var interestsAdapter: InterestsAdapter

    private var isMainPhoto: Boolean = false

    override fun selectedImage(imagePath: String?) {

        if (isMainPhoto){

            isMainPhoto = false
            Glide.with(this).load(imagePath).into(binding.userProfilePic)

        }else{
            images[tempPos].imagePath=imagePath!!
            images[tempPos].isDeleteL=false
            imageAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


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

        popup.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            popup.dismiss()

            val name = parent.getItemAtPosition(position).toString()
            binding.zodiacTv.text = name.substring(22,name.length-1)
        })

        interestsList.add(InterestedModel("Travel",false,false))
        interestsList.add(InterestedModel("Chatting",false,false))
        interestsList.add(InterestedModel("Athlete",true,false))
        interestsList.add(InterestedModel("Music",false,false))
        interestsList.add(InterestedModel("House Parties",true,false))
        interestsList.add(InterestedModel("Astrology",true,false))

        interestsAdapter = InterestsAdapter(interestsList)
        binding.interestsRecyclerView.layoutManager = FlexboxLayoutManager(this,FlexDirection.ROW)
      //    binding.interestsRecyclerView.layoutManager = GridLayoutManager(this,3,RecyclerView.VERTICAL,false)

        binding.interestsRecyclerView.adapter = interestsAdapter

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

        images.add(ImageModel("",false))
        images.add(ImageModel("",false))
        images.add(ImageModel("",false))
        images.add(ImageModel("",false))
        images.add(ImageModel("",false))
        images.add(ImageModel("",false))
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

        imageAdapter = ImageAdapter(this, images,this@EditProfileActivity)
        binding.myGallaryRecyclerView.adapter = imageAdapter
    }

    var tempPos=0

    fun getPosition(pos:Int){
        tempPos=pos
        getImage(this, 0)
    }
    override fun onClick(v: View?) {

        when(v){

            binding.backBtn2->{
                onBackPressed()
            }

            binding.finishBtn->{
                onBackPressed()
            }

            binding.userProfilePic->{
                isMainPhoto = true
                getImage(this,0)
            }

            binding.cameraPictureBtn->{
                isMainPhoto = true
                getImage(this,0)
            }
            binding.editTextDOB->{
                datePickerDialog()
            }

            binding.selectZodiacLayout->{
                popup.anchorView = v
                popup.isModal = true
                popup.width = binding.selectZodiacLayout.width
                popup.setAdapter(zodiacAdapter)
                popup.show()
            }

            binding.maleGender->{

                binding.maleGender.backgroundTintList = resources.getColorStateList(R.color.sky_blue)
                binding.maleGender.setTextColor(ContextCompat.getColor(this,R.color.white))

                binding.femaleGender.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
                binding.femaleGender.setTextColor(ContextCompat.getColor(this,R.color.black))

                binding.otherGender.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
                binding.otherGender.setTextColor(ContextCompat.getColor(this,R.color.black))

            } binding.femaleGender->{

            binding.maleGender.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
            binding.maleGender.setTextColor(ContextCompat.getColor(this,R.color.black))

            binding.femaleGender.backgroundTintList = resources.getColorStateList(R.color.sky_blue)
            binding.femaleGender.setTextColor(ContextCompat.getColor(this,R.color.white))

            binding.otherGender.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
            binding.otherGender.setTextColor(ContextCompat.getColor(this,R.color.black))

        }binding.otherGender->{

            binding.maleGender.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
            binding.maleGender.setTextColor(ContextCompat.getColor(this,R.color.black))

            binding.femaleGender.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
            binding.femaleGender.setTextColor(ContextCompat.getColor(this,R.color.black))

            binding.otherGender.backgroundTintList = resources.getColorStateList(R.color.sky_blue)
            binding.otherGender.setTextColor(ContextCompat.getColor(this,R.color.white))

        }
              binding.maleLooking->{

                binding.maleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.sky_blue)
                binding.maleTv.setTextColor(ContextCompat.getColor(this,R.color.white))
                  binding.maleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.white)

                  binding.femaleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                  binding.femaleTv.setTextColor(ContextCompat.getColor(this,R.color.black))
                  binding.femaleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)

                  binding.bothLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                  binding.bothTv.setTextColor(ContextCompat.getColor(this,R.color.black))
                  binding.bothColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)


            }
            binding.femaleLooking->{

                binding.maleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                binding.maleTv.setTextColor(ContextCompat.getColor(this,R.color.black))
                binding.maleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)

                binding.femaleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.sky_blue)
                binding.femaleTv.setTextColor(ContextCompat.getColor(this,R.color.white))
                binding.femaleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.white)

                binding.bothLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                binding.bothTv.setTextColor(ContextCompat.getColor(this,R.color.black))
                binding.bothColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)

        }
            binding.bothLooking->{

                binding.maleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                binding.maleTv.setTextColor(ContextCompat.getColor(this,R.color.black))
                binding.maleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)

                binding.femaleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                binding.femaleTv.setTextColor(ContextCompat.getColor(this,R.color.black))
                binding.femaleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)

                binding.bothLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.sky_blue)
                binding.bothTv.setTextColor(ContextCompat.getColor(this,R.color.white))
                binding.bothColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.white)
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
        binding.editTextDOB!!.setOnClickListener {
            DatePickerDialog(
                this,R.style.DialogTimePicker,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.editTextDOB.setText(sdf.format(cal.time))
    }
}
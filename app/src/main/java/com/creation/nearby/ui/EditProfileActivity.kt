package com.creation.nearby.ui

import android.app.Activity
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.creation.nearby.R
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.adapter.ImageAdapter
import com.creation.nearby.adapter.InterestsAdapter
import com.creation.nearby.adapter.ZodiacAdapter
import com.creation.nearby.databinding.ActivityEditProfileBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.ImageModel
import com.creation.nearby.model.InterestedModel
import com.creation.nearby.model.PopupModel
import com.creation.nearby.model.SuggestionsModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.permissionx.guolindev.PermissionX


class EditProfileActivity : AppCompatActivity(),View.OnClickListener {

    private var images = ArrayList<ImageModel>()
    private lateinit var imageAdapter: ImageAdapter

    private val popupList  = ArrayList<PopupModel>()

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var popup: ListPopupWindow
    private lateinit var zodiacAdapter: ListAdapter

    private var interestsList = ArrayList<InterestedModel>()
    private lateinit var interestsAdapter: InterestsAdapter

    private var isMainPhoto: Boolean = false


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

        binding.interestsRecyclerView.adapter = interestsAdapter

        binding.selectZodiacLayout.setOnClickListener(this)
        binding.maleGender.setOnClickListener(this)
        binding.femaleGender.setOnClickListener(this)
        binding.otherGender.setOnClickListener(this)
        binding.menTv.setOnClickListener(this)
        binding.womenTv.setOnClickListener(this)
        binding.bothTv.setOnClickListener(this)
        binding.userProfilePic.setOnClickListener(this)
        binding.backBtn2.setOnClickListener(this)
    }


    private fun initAdapter() {

        val onActionListener = object : OnActionListener<ImageModel> {
            override fun notify(model: ImageModel, position: Int,view: View) {
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
        }

        imageAdapter = ImageAdapter(this, images, onActionListener)
        binding.myGallaryRecyclerView.adapter = imageAdapter
    }


    private fun optionsDialog() {
        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                android.R.color.transparent
            )
        )
        dialog.setContentView(R.layout.image_picker_bottom_sheet)

        val tvCamera: TextView? = dialog.findViewById(R.id.select_camera)
        val tvGallery: TextView? = dialog.findViewById(R.id.select_photo_library)
        val tvCancel: TextView? = dialog.findViewById(R.id.cancel)

        tvCamera?.setOnClickListener {
            dialog.dismiss()
            openResourceWithPermissionCheck(isCameraRequest = true)
        }

        tvGallery?.setOnClickListener {
            dialog.dismiss()
            openResourceWithPermissionCheck(isCameraRequest = false)
        }

        tvCancel?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun openResourceWithPermissionCheck(isCameraRequest: Boolean) {

        PermissionX.init(this)
            .permissions(android.Manifest.permission.CAMERA,android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .onExplainRequestReason{ scope,deniedList->

                scope.showRequestReasonDialog(deniedList,"You need to allow permissions, to select photo.",
                    "Allow",
                    "Deny")

            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "Open Settings",
                    "Cancel"
                )
            }
            .request { allGranted, _, _ ->
                if (allGranted) {
                    if (isCameraRequest)
                        getImageFromCamera()
                    else
                        getImageFromGallery()
                } else
                    ToastUtils.showToast("Unable to perform action due to permissions",this)
            }

    }

    private fun getImageFromCamera() {
        cameraResultLauncher.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    }

    private fun getImageFromGallery() {
        galleryResultLauncher.launch(
            Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            },
        )
    }


    private var cameraResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap?
                val uri = getImageUri(imageBitmap)
                if(isMainPhoto){
                    isMainPhoto = false
                    binding.userProfilePic.setImageURI(uri)

                }else{
                    addImageToList(uri)
                }
            }
        }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (isMainPhoto){
                    isMainPhoto = false
                    binding.userProfilePic.setImageURI(data?.data)
                }else{
                    addImageToList(data?.data)
                }

            }
        }

    private fun addImageToList(uri: Uri?) {

            images.add(ImageModel(uri))
            imageAdapter?.notifyDataSetChanged()

        binding.myGallaryRecyclerView.scrollToPosition(images.size)
    }

    private fun getImageUri(inImage: Bitmap?): Uri? {
        val outImage = Bitmap.createScaledBitmap(inImage!!, 1000, 1000, true)
        val path = MediaStore.Images.Media.insertImage(
            baseContext.contentResolver,
            outImage,
            "Title",
            null
        )
        return Uri.parse(path)
    }


    override fun onClick(v: View?) {

        when(v){

            binding.backBtn2->{
                onBackPressed()
            }

            binding.userProfilePic->{
                isMainPhoto = true
                optionsDialog()
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
            binding.menTv->{

                binding.menTv.backgroundTintList = resources.getColorStateList(R.color.sky_blue)
                binding.menTv.compoundDrawables[0].setTint(resources.getColor(R.color.white))
                binding.menTv.setTextColor(ContextCompat.getColor(this,R.color.white))

                binding.womenTv.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
                binding.womenTv.compoundDrawables[0].setTint(resources.getColor(R.color.black))
                binding.womenTv.setTextColor(ContextCompat.getColor(this,R.color.black))

                binding.bothTv.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
                binding.bothTv.compoundDrawables[0].setTint(resources.getColor(R.color.black))
                binding.bothTv.setTextColor(ContextCompat.getColor(this,R.color.black))

        }binding.womenTv->{

            binding.menTv.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
            binding.menTv.compoundDrawables[0].setTint(resources.getColor(R.color.black))
            binding.menTv.setTextColor(ContextCompat.getColor(this,R.color.black))

            binding.womenTv.backgroundTintList = resources.getColorStateList(R.color.sky_blue)
            binding.womenTv.compoundDrawables[0].setTint(resources.getColor(R.color.white))
            binding.womenTv.setTextColor(ContextCompat.getColor(this,R.color.white))

            binding.bothTv.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
            binding.bothTv.compoundDrawables[0].setTint(resources.getColor(R.color.black))
            binding.bothTv.setTextColor(ContextCompat.getColor(this,R.color.black))

        }binding.bothTv->{

            binding.menTv.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
            binding.menTv.compoundDrawables[0].setTint(resources.getColor(R.color.black))
            binding.menTv.setTextColor(ContextCompat.getColor(this,R.color.black))

            binding.womenTv.backgroundTintList = resources.getColorStateList(R.color.edittext_grey)
            binding.womenTv.compoundDrawables[0].setTint(resources.getColor(R.color.black))
            binding.womenTv.setTextColor(ContextCompat.getColor(this,R.color.black))

            binding.bothTv.backgroundTintList = resources.getColorStateList(R.color.sky_blue)
            binding.bothTv.compoundDrawables[0].setTint(resources.getColor(R.color.white))
            binding.bothTv.setTextColor(ContextCompat.getColor(this,R.color.white))

        }

        }
    }
}
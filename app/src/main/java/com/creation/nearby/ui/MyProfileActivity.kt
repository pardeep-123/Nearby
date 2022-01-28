package com.creation.nearby.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.GallaryAdapter
import com.creation.nearby.adapter.ImageAdapter
import com.creation.nearby.adapter.InterestsAdapter
import com.creation.nearby.databinding.ActivityMyProfileBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.GallaryModel
import com.creation.nearby.model.ImageModel
import com.creation.nearby.model.InterestedModel
import com.creation.nearby.utils.ToastUtils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.permissionx.guolindev.PermissionX

class MyProfileActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityMyProfileBinding
    private  var interestsList = ArrayList<InterestedModel>()
    private lateinit var  interestsAdapter: InterestsAdapter

    private var gallaryList = ArrayList<GallaryModel>()
    private lateinit var gallaryAdapter: GallaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

        interestsList.add(InterestedModel("Travel",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Chatting",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Athlete",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("House Parties",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Cricket",isSelected = false,isProfile = true))


        interestsAdapter = InterestsAdapter(interestsList)
        binding.profileInterestRecView.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW)
        binding.profileInterestRecView.adapter = interestsAdapter
        interestsAdapter.notifyDataSetChanged()


        gallaryList.add(GallaryModel(R.drawable.my_profile_pic))
        gallaryList.add(GallaryModel(R.drawable.my_profile_pic))
        gallaryList.add(GallaryModel(R.drawable.my_profile_pic))
        gallaryList.add(GallaryModel(R.drawable.my_profile_pic))
        gallaryList.add(GallaryModel(R.drawable.my_profile_pic))

        binding.cameraIv.setOnClickListener(this)
        binding.editProfileIv.setOnClickListener(this)

    }

    private fun initAdapter() {

        val onActionListener = object : OnActionListener<ImageModel> {
            override fun notify(model: ImageModel, position: Int,view: View) {

            }
        }
        binding.gallaryRecyclerView.layoutManager = GridLayoutManager(this,3)
        gallaryAdapter = GallaryAdapter(this, gallaryList, onActionListener)
        binding.gallaryRecyclerView.adapter = gallaryAdapter
        gallaryAdapter.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        when(v){

            binding.cameraIv->{

                optionsDialog()

            }binding.editProfileIv->{

                startActivity(Intent(this,EditProfileActivity::class.java))
            }
        }
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
                    binding.userProfilePicMyProfile.setImageURI(uri)


            }
        }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                    binding.userProfilePicMyProfile.setImageURI(data?.data)


            }
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


}
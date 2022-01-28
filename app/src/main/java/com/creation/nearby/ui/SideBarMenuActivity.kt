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
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivitySideBarMenuBinding
import com.creation.nearby.utils.ToastUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.permissionx.guolindev.PermissionX

class SideBarMenuActivity : AppCompatActivity(),View.OnClickListener {

   private lateinit var binding: ActivitySideBarMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySideBarMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notificationLayout.setOnClickListener(this)
        binding.settingsLayout.setOnClickListener(this)
        binding.myProfileLayout.setOnClickListener(this)
        binding.toggleOnLayout.setOnClickListener(this)
        binding.toggleOffLayout.setOnClickListener(this)
        binding.userCamera.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when(v){

           binding.notificationLayout->{
               startActivity(Intent(this,NotificationsActivity::class.java))
           }

            binding.settingsLayout->{
                startActivity(Intent(this,SettingsActivity::class.java))
            }
            binding.myProfileLayout->{
                startActivity(Intent(this,MyProfileActivity::class.java))
            }
            binding.toggleOnLayout->{
                binding.toggleOffLayout.visibility = View.VISIBLE
                binding.toggleOnLayout.visibility = View.GONE
            }
            binding.toggleOffLayout->{
                binding.toggleOffLayout.visibility = View.GONE
                binding.toggleOnLayout.visibility = View.VISIBLE
            }
            binding.userCamera->{

                optionsDialog()

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
                binding.userImage.setImageURI(uri)


            }
        }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                binding.userImage.setImageURI(data?.data)


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
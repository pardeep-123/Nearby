package com.creation.nearby.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityAddEventBinding
import com.creation.nearby.databinding.ImagePickerBottomSheetBinding
import com.creation.nearby.utils.TimePickerUniversal
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*
import android.widget.TimePicker

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.Window
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.creation.nearby.model.ImageModel
import com.creation.nearby.utils.ToastUtils
import com.permissionx.guolindev.PermissionX
import java.text.SimpleDateFormat


class AddEventActivity : AppCompatActivity() {

    var MY_REQUEST = 1
    lateinit var binding: ActivityAddEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!Places.isInitialized()) {
            Places.initialize(this, resources.getString(R.string.map_key))
        }
        clickHandle()

    }

    private fun clickHandle() {

        binding.backBtn.setOnClickListener{
            onBackPressed()
        }

        binding.selectImageRv.setOnClickListener() {

            optionsDialog()
        }

        binding.location.setOnClickListener{
            openPlacePicker()
        }

        binding.timePicker.setOnClickListener{

            TimePickerUniversal(binding.timePicker,true)


        }
    }


    fun openPlacePicker() {
        MY_REQUEST = 1
        val fields = Arrays.asList(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS
        )
        val intent =
            Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
        startActivityForResult.launch(intent)
    }

    var startActivityForResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == AutocompleteActivity.RESULT_OK) {
                val place: Place? =
                    result.data?.let { Autocomplete.getPlaceFromIntent(it) }
                place?.let {
                    setPlaceData(it)
                }
            }
        }

    private fun setPlaceData(it: Place) {
        binding.location.setText(it.address.toString())
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
                    binding.selectImageRv.setImageURI(uri)

            }
        }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                    binding.selectImageRv.setImageURI(data?.data)


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
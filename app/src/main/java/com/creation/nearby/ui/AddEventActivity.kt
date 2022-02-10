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
import com.bumptech.glide.Glide
import com.creation.nearby.model.ImageModel
import com.creation.nearby.utils.ImagePickerUtility
import com.creation.nearby.utils.ToastUtils
import com.permissionx.guolindev.PermissionX
import java.text.SimpleDateFormat


class AddEventActivity : ImagePickerUtility(){

    var MY_REQUEST = 1
    lateinit var binding: ActivityAddEventBinding
    override fun selectedImage(imagePath: String?) {
        Glide.with(this).load(imagePath).into(binding.selectImageRv)
    }

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

            getImage(this,0)
        }

        binding.location.setOnClickListener{
            openPlacePicker()
        }

        binding.timePicker.setOnClickListener{

            TimePickerUniversal(binding.timePicker,true)


        }
        binding.addEventBtn.setOnClickListener{
            finish()
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

}
package com.creation.nearby.ui

import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityAddEventBinding
import com.creation.nearby.utils.ImagePickerUtility
import com.creation.nearby.utils.TimePickerUniversal
import com.creation.nearby.viewmodel.AddEventVm
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*


class AddEventActivity : ImagePickerUtility(){

    var MY_REQUEST = 1
    lateinit var binding: ActivityAddEventBinding
    val AddEventVm : AddEventVm by viewModels()
    override fun selectedImage(imagePath: String?) {
        Glide.with(this).load(imagePath).into(binding.selectImageRv)
        AddEventVm.image.set(imagePath)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
         binding.addEventVM = AddEventVm
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
         AddEventVm.latitude.set(it.latLng?.latitude.toString())
         AddEventVm.longitude.set(it.latLng?.longitude.toString())
    }

}
package com.creation.nearby.ui.authentication

import android.app.Activity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivitySignUpBinding
import com.creation.nearby.isGone
import com.creation.nearby.isVisible
import com.creation.nearby.viewmodel.SignupVm
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivitySignUpBinding

    private val signupVm : SignupVm by viewModels()

    // create launcher for startAxtivity for result
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result : ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK){
            val data = result.data

            data?.let {
                val place = Autocomplete.getPlaceFromIntent(result.data!!)

                user_location.setText(place.name.toString())

                signupVm.latitude.set(place.latLng!!.latitude.toString())
                signupVm.longitude.set(place.latLng!!.longitude.toString())
                signupVm.location.set(place.name.toString())

            }
        }


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
       binding.signUpVM = signupVm
        binding.goBack1.setOnClickListener(this)
        binding.showSignup.setOnClickListener(this)
        binding.hideSignup.setOnClickListener(this)
        binding.showConfirmSignup.setOnClickListener(this)
        binding.hideConfirmSignup.setOnClickListener(this)
        binding.alreadyHaveAnAccount.setOnClickListener(this)

        /**
         *@author Pardeep Sharma
         *  Created on 08 March 2022
         *  function for select the location from map
         */
        val apiKey = getString(R.string.map_key)
        Places.initialize(this, apiKey)
        //set On click listener on location EditText
        user_location.setOnClickListener {
            // Set the fields to specify which types of place data to
// return after the user has made a selection.
            val fields = listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS_COMPONENTS,
                Place.Field.ADDRESS
            )

// Start the autocomplete intent.
            val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this)
         resultLauncher.launch(intent)

        }

        /**
         *  Ends here
         */

    }

    override fun onClick(v: View?) {

        when (v) {
            binding.goBack1 -> {
                onBackPressed()
            }

            binding.showSignup -> {

//                if (binding.showHideSignup.text.toString() == "Display") {
//                    binding.passwordSignup.transformationMethod = HideReturnsTransformationMethod.getInstance()
//                    binding.showHideSignup.text = "Hide"
//                    binding.passwordSignup.setSelection(binding.passwordSignup.length())
//                } else {
//                    binding.passwordSignup.transformationMethod = PasswordTransformationMethod.getInstance()
//                    binding.showHideSignup.text = "Display"
//                    binding.passwordSignup.setSelection(binding.passwordSignup.length())
//
//                }
                binding.showSignup.isGone()
                binding.hideSignup.isVisible()
                binding.passwordSignup.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.passwordSignup.setSelection(binding.passwordSignup.length())
            }
            binding.hideSignup ->{
                binding.hideSignup.isGone()
                binding.showSignup.isVisible()
                binding.passwordSignup.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.passwordSignup.setSelection(binding.passwordSignup.length())
            }
            binding.showConfirmSignup -> {

//                if (binding.showHideSignupConfirm.text.toString() == "Display") {
//                    binding.confirmPassword.transformationMethod =
//                        HideReturnsTransformationMethod.getInstance()
//                    binding.showHideSignupConfirm.text = "Hide"
//                    binding.confirmPassword.setSelection(binding.confirmPassword.length())
//                } else {
//                    binding.confirmPassword.transformationMethod =
//                        PasswordTransformationMethod.getInstance()
//                    binding.showHideSignupConfirm.text = "Display"
//                    binding.confirmPassword.setSelection(binding.confirmPassword.length())
//
//                }
                binding.showConfirmSignup.isGone()
                binding.hideConfirmSignup.isVisible()
                binding.confirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.confirmPassword.setSelection(binding.confirmPassword.length())
            }

            binding.hideConfirmSignup ->{
                binding.hideConfirmSignup.isGone()
                binding.showConfirmSignup.isVisible()
                binding.confirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.confirmPassword.setSelection(binding.confirmPassword.length())
            }


            binding.alreadyHaveAnAccount -> {

                onBackPressed()
            }

        }

    }

}


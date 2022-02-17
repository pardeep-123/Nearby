package com.creation.nearby.ui.authentication

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.databinding.ActivitySignUpBinding
import com.creation.nearby.isGone
import com.creation.nearby.isVisible
import com.creation.nearby.viewmodel.SignupVm

class SignUpActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivitySignUpBinding

    val signupVm : SignupVm by viewModels()
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


        /*   binding.signUpBtn.isEnabled = false
           binding.signUpBtn.alpha = 0.5f*/

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


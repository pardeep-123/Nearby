package com.creation.nearby.ui.authentication

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivitySignUpBinding
import com.creation.nearby.isGone
import com.creation.nearby.isVisible
import com.creation.nearby.ui.PrivacyPolicyActivity
import com.creation.nearby.ui.TermsOfUse
import com.creation.nearby.viewmodel.SignupVm

class SignUpActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {
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
        binding.signUpBtn.setOnClickListener(this)
        binding.termsOfUse.setOnClickListener(this)
        binding.privacyPolicy.setOnClickListener(this)
        binding.alreadyHaveAnAccount.setOnClickListener(this)

        binding.userFirstName.addTextChangedListener(this)
        binding.userLastName.addTextChangedListener(this)
        binding.userEmail.addTextChangedListener(this)
        binding.referralCode.addTextChangedListener(this)
        binding.passwordSignup.addTextChangedListener(this)
        binding.confirmPassword.addTextChangedListener(this)

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

            binding.termsOfUse -> {

                startActivity(Intent(this, TermsOfUse::class.java))

            }
            binding.privacyPolicy -> {

                startActivity(Intent(this, PrivacyPolicyActivity::class.java))

            }
            binding.alreadyHaveAnAccount -> {

//                startActivity(Intent(this, LoginActivity::class.java))
                //              finish()
                onBackPressed()
            }
            binding.signUpBtn -> {

                confirmationDialog()
            }
        }

    }

    private fun confirmationDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                android.R.color.transparent
            )
        )
        dialog.setContentView(R.layout.send_verification_dialog)

        val ok: AppCompatButton? = dialog.findViewById(R.id.ok)

        ok?.setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


        dialog.show()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


    }

    override fun afterTextChanged(s: Editable?) {

        /* if (binding.userFirstName.text.isNotEmpty()) {

             if (binding.userLastName.text.isNotEmpty()) {
                 if (binding.userEmail.text.isNotEmpty()) {
                     if (binding.passwordSignup.text.isNotEmpty()) {
                         if (binding.confirmPassword.text.isNotEmpty()) {
                             if (binding.referralCode.text.isNotEmpty()) {
                                 binding.signUpBtn.isEnabled = true
                                 binding.signUpBtn.alpha = 1f
                             } else {
                                 binding.signUpBtn.isEnabled = false
                                 binding.signUpBtn.alpha = 0.5f
                             }

                         } else {

                             binding.signUpBtn.isEnabled = false
                             binding.signUpBtn.alpha = 0.5f

                         }
                     } else {
                         binding.signUpBtn.isEnabled = false
                         binding.signUpBtn.alpha = 0.5f
                     }
                 } else {
                     binding.signUpBtn.isEnabled = false
                     binding.signUpBtn.alpha = 0.5f
                 }

             } else {
                 binding.signUpBtn.isEnabled = false
                 binding.signUpBtn.alpha = 0.5f
             }

         } else {
             binding.signUpBtn.isEnabled = false
             binding.signUpBtn.alpha = 0.5f
         }*/
    }
}


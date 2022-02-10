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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivitySignUpBinding
import com.creation.nearby.ui.PrivacyPolicyActivity
import com.creation.nearby.ui.TermsOfUse

class SignUpActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goBack1.setOnClickListener(this)
        binding.showHideSignup.setOnClickListener(this)
        binding.showHideSignupConfirm.setOnClickListener(this)
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

            binding.showHideSignup -> {

                if (binding.showHideSignup.text.toString() == "Display") {
                    binding.passwordSignup.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.showHideSignup.text = "Hide"
                    binding.passwordSignup.setSelection(binding.passwordSignup.length())
                } else {
                    binding.passwordSignup.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.showHideSignup.text = "Display"
                    binding.passwordSignup.setSelection(binding.passwordSignup.length())

                }
            }
            binding.showHideSignupConfirm -> {

                if (binding.showHideSignupConfirm.text.toString() == "Display") {
                    binding.confirmPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.showHideSignupConfirm.text = "Hide"
                    binding.confirmPassword.setSelection(binding.confirmPassword.length())
                } else {
                    binding.confirmPassword.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.showHideSignupConfirm.text = "Display"
                    binding.confirmPassword.setSelection(binding.confirmPassword.length())

                }
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

    override fun onBackPressed() {
        super.onBackPressed()
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


package com.creation.nearby.ui.authentication

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivitySignUpBinding


class SignUpActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goBack1.setOnClickListener(this)
        binding.showHideSignup.setOnClickListener(this)
        binding.showHideSignupConfirm.setOnClickListener(this)
        binding.signUpBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {


        when(v){

            binding.goBack1->{
                onBackPressed()
            }

            binding.showHideSignup->{

                if( binding.showHideSignup.text.toString() == "Display"){
                    binding.passwordSignup.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    binding.showHideSignup.text = "Hide"
                    binding.passwordSignup.setSelection(binding.passwordSignup.length())
                } else{
                    binding.passwordSignup.transformationMethod = PasswordTransformationMethod.getInstance()
                    binding.showHideSignup.text = "Display"
                    binding.passwordSignup.setSelection(binding.passwordSignup.length())

                }
            }

            binding.showHideSignupConfirm->{

                if( binding.showHideSignupConfirm.text.toString() == "Display"){
                    binding.confirmPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    binding.showHideSignupConfirm.text = "Hide"
                    binding.confirmPassword.setSelection(binding.confirmPassword.length())
                } else{
                    binding.confirmPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                    binding.showHideSignupConfirm.text = "Display"
                    binding.confirmPassword.setSelection(binding.confirmPassword.length())

                }
            }

            binding.alreadyHaveAnAccount->{

                startActivity(Intent(this,LoginActivity::class.java))

            }
            binding.signUpBtn->{

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
            ContextCompat.getDrawable(this,
                android.R.color.transparent
            )
        )
        dialog.setContentView(R.layout.send_verification_dialog)

        val ok: AppCompatButton? = dialog.findViewById(R.id.ok)

        ok?.setOnClickListener {
            dialog.dismiss()
        }


        dialog.show()
    }


}
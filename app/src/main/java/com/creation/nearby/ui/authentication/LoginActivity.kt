package com.creation.nearby.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.databinding.ActivityLoginBinding
import com.creation.nearby.ui.MainActivity


class LoginActivity : AppCompatActivity(),View.OnClickListener,TextWatcher {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgotPassword.setOnClickListener(this)
        binding.goBack.setOnClickListener(this)
        binding.signUp.setOnClickListener(this)
        binding.showHide.setOnClickListener(this)
        binding.signInLogin.setOnClickListener(this)


    }

    override fun onClick(v: View?) {

        when(v){

            binding.forgotPassword->{
             startActivity(Intent(this,ForgotPasswordActivity::class.java))

            }
            binding.goBack->{
               finish()
            }

            binding.signUp->{
                startActivity(Intent(this,SignUpActivity::class.java))

            }

            binding.showHide->{

                if(binding.showHide.text.toString() == "Display"){
                    binding.passwordLogin.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    binding.showHide.text = "Hide"
                    binding.passwordLogin.setSelection(binding.passwordLogin.length())
                } else{
                    binding.passwordLogin.transformationMethod = PasswordTransformationMethod.getInstance()
                    binding.showHide.text = "Display"
                    binding.passwordLogin.setSelection(binding.passwordLogin.length())

                }

            }
            binding.signInLogin->{
                startActivity(Intent(this,MainActivity::class.java))

            }



        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {



    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (binding.emailLogin.text.toString().isNotEmpty()){

            binding.signInLogin.isEnabled = binding.passwordLogin.text.toString().isNotEmpty() && binding.passwordLogin.text.toString().length >= 7

        }else{
            binding.signInLogin.isEnabled = false
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }
}
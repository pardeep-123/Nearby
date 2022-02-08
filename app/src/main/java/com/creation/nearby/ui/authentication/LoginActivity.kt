package com.creation.nearby.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityLoginBinding
import com.creation.nearby.ui.MainActivity


class LoginActivity : AppCompatActivity(),View.OnClickListener,TextWatcher {
    lateinit var binding: ActivityLoginBinding

    var isChecked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.forgotPassword.setOnClickListener(this)
        binding.goBack.setOnClickListener(this)
        binding.signUp.setOnClickListener(this)
        binding.showHide.setOnClickListener(this)
        binding.signInLogin.setOnClickListener(this)
        binding.rememberCheckbox.setOnClickListener(this)

        binding.emailLogin.addTextChangedListener(this)
        binding.passwordLogin.addTextChangedListener(this)

        binding.signInLogin.isEnabled = false
        binding.signInLogin.alpha = 0.5f

    }

    override fun onClick(v: View?) {

        when(v){

            binding.forgotPassword->{
             startActivity(Intent(this,ForgotPasswordActivity::class.java))

            }
            binding.goBack->{
               onBackPressed()
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
            binding.rememberCheckbox->{
                isChecked = !isChecked
                if (isChecked)
                    binding.rememberCheckbox.setImageResource(R.drawable.checked)
                else
                    binding.rememberCheckbox.setImageResource(R.drawable.unchecked)
            }



        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {



    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {

        if (binding.emailLogin.text.isNotEmpty()){

            if(binding.passwordLogin.text.isNotEmpty()){

                binding.signInLogin.isEnabled =  true
                binding.signInLogin.alpha = 1f

            }else{
                binding.signInLogin.isEnabled = false
                binding.signInLogin.alpha = 0.5f
            }

        }else{
            binding.signInLogin.isEnabled = false
            binding.signInLogin.alpha = 0.5f
        }

    }
}
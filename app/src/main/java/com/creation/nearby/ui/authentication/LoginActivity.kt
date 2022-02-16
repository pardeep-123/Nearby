package com.creation.nearby.ui.authentication

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityLoginBinding
import com.creation.nearby.viewmodel.LoginVm


class LoginActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var binding: ActivityLoginBinding
    val loginVm : LoginVm by viewModels()
    var isChecked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginVM = loginVm
        binding.showHide.setOnClickListener(this)
        binding.rememberCheckbox.setOnClickListener(this)


    }

    override fun onClick(v: View?) {

        when(v){

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

            binding.rememberCheckbox->{
                isChecked = !isChecked
                if (isChecked)
                    binding.rememberCheckbox.setImageResource(R.drawable.checked)
                else
                    binding.rememberCheckbox.setImageResource(R.drawable.unchecked)
            }



        }

    }


}
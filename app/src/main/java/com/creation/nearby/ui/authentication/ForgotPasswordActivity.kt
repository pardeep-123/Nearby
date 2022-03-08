package com.creation.nearby.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import com.creation.nearby.databinding.ActivityForgotPasswordBinding
import com.creation.nearby.utils.AppUtils
import com.creation.nearby.viewmodel.ForgetVM

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityForgotPasswordBinding
    val ForgetVM : ForgetVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.forgetPasswordVm = ForgetVM
        binding.goBackForgot.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v){

            binding.goBackForgot->{
                onBackPressed()
            }

        }
    }
}
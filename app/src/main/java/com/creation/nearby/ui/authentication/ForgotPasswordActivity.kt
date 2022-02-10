package com.creation.nearby.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.creation.nearby.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener,TextWatcher {
    lateinit var binding: ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goBackForgot.setOnClickListener(this)
        binding.forgotEmail.addTextChangedListener(this)
        binding.sendRequestBtn.setOnClickListener(this)

     /*   binding.sendRequestBtn.isEnabled = true
        binding.sendRequestBtn.alpha = 0.5*/
    }

    override fun onClick(v: View?) {

        when(v){

            binding.goBackForgot->{
                onBackPressed()
            }
            binding.sendRequestBtn->{
                finish()
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(s: Editable?) {

     /*   if (binding.forgotEmail.text.isNotEmpty()){

            binding.sendRequestBtn.isEnabled = true
            binding.sendRequestBtn.alpha = 1f
        }else{
            binding.sendRequestBtn.isEnabled = true
            binding.sendRequestBtn.alpha = 0.5f
        }
*/
    }
}
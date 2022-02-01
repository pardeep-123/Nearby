package com.creation.nearby.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity(),View.OnClickListener,TextWatcher {
    private lateinit var binding: ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goBackForgot.setOnClickListener(this)
        binding.showHide.setOnClickListener(this)
        binding.showHideConfirmNew.setOnClickListener(this)
        binding.showHideNew.setOnClickListener(this)

        binding.newPassword.addTextChangedListener(this)
        binding.confirmNewPassword.addTextChangedListener(this)
        binding.confirmLatestPassword.addTextChangedListener(this)

        binding.saveBtn.isEnabled = false
        binding.saveBtn.alpha = 0.5f

    }

    override fun onClick(v: View?) {

        when(v){
            binding.goBackForgot->{
                onBackPressed()
            }

            binding.showHide -> {

                if (binding.showHide.text.toString() == "Display") {
                    binding.passwordChange.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.showHide.text = "Hide"
                    binding.passwordChange.setSelection(binding.passwordChange.length())
                } else {
                    binding.passwordChange.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.showHide.text = "Display"
                    binding.passwordChange.setSelection(binding.passwordChange.length())

                }
            }

            binding.showHideNew -> {

                if (binding.showHideNew.text.toString() == "Display") {
                    binding.newPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.showHideNew.text = "Hide"
                    binding.newPassword.setSelection(binding.newPassword.length())
                } else {
                    binding.newPassword.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.showHideNew.text = "Display"
                    binding.newPassword.setSelection(binding.newPassword.length())

                }
            }

            binding.showHideConfirmNew -> {

                if (binding.showHideConfirmNew.text.toString() == "Display") {
                    binding.confirmLatestPassword.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.showHideConfirmNew.text = "Hide"
                    binding.confirmLatestPassword.setSelection(binding.confirmLatestPassword.length())
                } else {
                    binding.confirmLatestPassword.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.showHideConfirmNew.text = "Display"
                    binding.confirmLatestPassword.setSelection(binding.confirmLatestPassword.length())

                }
            }


        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {



    }

    override fun afterTextChanged(s: Editable?) {

        if (binding.passwordChange.text.isNotEmpty()){

            if(binding.newPassword.text.isNotEmpty()){
                if (binding.confirmLatestPassword.text.isNotEmpty()){

                    binding.saveBtn.isEnabled = true
                    binding.saveBtn.alpha = 1f

                }else{
                    binding.saveBtn.isEnabled = false
                    binding.saveBtn.alpha = 0.5f
                }
            }else{
                binding.saveBtn.isEnabled = false
                binding.saveBtn.alpha = 0.5f
            }

        }else{
            binding.saveBtn.isEnabled = false
            binding.saveBtn.alpha = 0.5f
        }

    }
}
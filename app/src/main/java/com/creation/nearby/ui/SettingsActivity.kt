package com.creation.nearby.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.creation.nearby.databinding.ActivitySettingsBinding
import com.creation.nearby.databinding.ActivitySideBarMenuBinding
import com.creation.nearby.ui.authentication.ResetPasswordActivity

class SettingsActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.userInfoLayout.setOnClickListener(this)
        binding.changePasswordActivity.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v){

            binding.userInfoLayout->{
                startActivity(Intent(this,PersonalInfoActivity::class.java))
            }

            binding.changePasswordActivity->{
                startActivity(Intent(this,ResetPasswordActivity::class.java))
            }

        }

    }
}
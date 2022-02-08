package com.creation.nearby.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.creation.nearby.databinding.ActivitySettingsBinding
import com.creation.nearby.ui.authentication.ChangePasswordActivity
import com.creation.nearby.ui.authentication.LoginActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickHandler()
    }

    private fun clickHandler() {

        binding.goBackBtn1.setOnClickListener{
            onBackPressed()
        }

        binding.userInfoLayout.setOnClickListener{
            startActivity(Intent(this,PersonalInfoActivity::class.java))
        }

        binding.changePasswordActivity.setOnClickListener{
            startActivity(Intent(this,ChangePasswordActivity::class.java))
        }
        binding.logoutBtn.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }

}
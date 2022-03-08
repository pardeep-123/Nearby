package com.creation.nearby.ui

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivitySettingsBinding
import com.creation.nearby.ui.authentication.ChangePasswordActivity
import com.creation.nearby.ui.authentication.LoginActivity
import com.creation.nearby.viewmodel.SettingVM

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    val settingVM : SettingVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.settingVM = settingVM
        clickHandler()
    }

    private fun clickHandler() {

        binding.goBackBtn1.setOnClickListener{
            onBackPressed()
        }
    }



}
package com.creation.nearby.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.databinding.ActivitySettingsBinding
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
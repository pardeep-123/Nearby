package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityPersonalInfoBinding
import com.creation.nearby.databinding.ActivitySettingsBinding

class PersonalInfoActivity : AppCompatActivity() {
    lateinit var binding: ActivityPersonalInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonalInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickHandler()

    }

    private fun clickHandler() {

        binding.gobackBtn.setOnClickListener{
            onBackPressed()
        }

    }
}
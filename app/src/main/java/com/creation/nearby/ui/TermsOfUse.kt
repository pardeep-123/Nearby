package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityTermsOfUseBinding

class TermsOfUse : AppCompatActivity() {
    private lateinit var binding: ActivityTermsOfUseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermsOfUseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goBack.setOnClickListener{
            onBackPressed()
        }
    }
}
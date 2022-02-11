package com.creation.nearby.ui

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityCallingBinding
import com.creation.nearby.databinding.ActivityCallingVideoBinding

class CallingVideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCallingVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallingVideoBinding.inflate(layoutInflater)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(binding.root)

        clickHandler()
    }

    private fun clickHandler() {
        binding.cancelCallBtn.setOnClickListener{
            onBackPressed()
        }
    }
}
package com.creation.nearby.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityCallingBinding

class CallingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCallingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallingBinding.inflate(layoutInflater)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(binding.root)

        clickHandler()

    }

    private fun clickHandler() {
        binding.cancelCallBtn.setOnClickListener{
            onBackPressed()
        }
        binding.videoBtn.setOnClickListener{
            startActivity(Intent(this,CallingVideoActivity::class.java))
        }
    }


}
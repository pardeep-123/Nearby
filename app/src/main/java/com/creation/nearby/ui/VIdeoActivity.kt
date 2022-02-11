package com.creation.nearby.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityVIdeoBinding

class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVIdeoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVIdeoBinding.inflate(layoutInflater)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(binding.root)

        clickHandler()
    }

    private fun clickHandler() {
        binding.cancelCallBtn.setOnClickListener{
            onBackPressed()
        }
        binding.videoCallBtn.setOnClickListener{
            startActivity(Intent(this,CallingVideoActivity::class.java))
        }
    }


}
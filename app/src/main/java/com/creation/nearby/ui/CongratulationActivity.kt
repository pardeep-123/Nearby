package com.creation.nearby.ui


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.creation.nearby.databinding.ActivityCongratulationBinding

class CongratulationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCongratulationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongratulationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clickHandler()
    }

    private fun clickHandler() {
        binding.continueToSwipe.setOnClickListener{
            onBackPressed()
        }
        binding.chatWithFriendBtn.setOnClickListener{

            startActivity(Intent(this,OngoingChatActivity::class.java))

        }
    }
}
package com.creation.nearby.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.creation.nearby.databinding.ActivityCongratulationBinding

class CongratulationActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityCongratulationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongratulationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onClick(v: View?) {
        when(v){
            binding.continueToSwipe->{
                onBackPressed()
            }
        }

    }
}
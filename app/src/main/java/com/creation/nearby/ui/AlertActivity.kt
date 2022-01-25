package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.creation.nearby.databinding.ActivityAlertBinding

class AlertActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityAlertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.messageLayout.setOnClickListener(this)
        binding.newFriendLayout.setOnClickListener(this)
        binding.requestsLayout.setOnClickListener(this)
        binding.inAppNotificationLayout.setOnClickListener(this)
        binding.tipsLayout.setOnClickListener(this)
        binding.locationLayout.setOnClickListener(this)
        binding.cameraLayout.setOnClickListener(this)
        binding.voiceLayout.setOnClickListener(this)


    }

    override fun onClick(v: View?) {

        when(v){
            binding.messageLayout->{
                binding.messageCheckBox.isChecked = !binding.messageCheckBox.isChecked
            }
            binding.newFriendLayout->{
                binding.newFriendCheckBox.isChecked = !binding.newFriendCheckBox.isChecked
            }
            binding.requestsLayout->{
                binding.requestCheckBox.isChecked = !binding.requestCheckBox.isChecked
            }
            binding.inAppNotificationLayout->{
                binding.inAppNotificationCheckBox.isChecked = !binding.inAppNotificationCheckBox.isChecked
            }
            binding.tipsLayout->{
                binding.tipsCheckBox.isChecked = !binding.tipsCheckBox.isChecked
            }
            binding.locationLayout->{
                binding.locationCheckBox.isChecked = !binding.locationCheckBox.isChecked
            }
            binding.cameraLayout->{
                binding.cameraCheckBox.isChecked = !binding.cameraCheckBox.isChecked
            }
            binding.voiceLayout->{
                binding.voiceCheckBox.isChecked = !binding.voiceCheckBox.isChecked
            }
        }

    }

}
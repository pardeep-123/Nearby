package com.creation.nearby.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.creation.nearby.databinding.ActivitySideBarMenuBinding

class SideBarMenuActivity : AppCompatActivity(),View.OnClickListener {

   private lateinit var binding: ActivitySideBarMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySideBarMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.notificationLayout.setOnClickListener(this)
        binding.settingsLayout.setOnClickListener(this)
        binding.myProfileLayout.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when(v){

           binding.notificationLayout->{
               startActivity(Intent(this,NotificationsActivity::class.java))
           }

            binding.settingsLayout->{
                startActivity(Intent(this,SettingsActivity::class.java))
            }
            binding.myProfileLayout->{
                startActivity(Intent(this,MyProfileActivity::class.java))
            }

        }

    }
}
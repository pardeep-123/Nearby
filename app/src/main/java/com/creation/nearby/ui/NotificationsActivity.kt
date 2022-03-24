package com.creation.nearby.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.NotificationAdapter
import com.creation.nearby.databinding.ActivityNotificationsBinding
import com.creation.nearby.fragments.HomeFragment
import com.creation.nearby.model.NotificationModel
import com.creation.nearby.viewmodel.NotificationVM

class NotificationsActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotificationsBinding

    lateinit var notificationVm : NotificationVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notificationVm = ViewModelProviders.of(this).get(NotificationVM::class.java)
        //notification recycler view

        notificationVm.notificationListing()
        onClickEvent()

    }

    private fun onClickEvent() {
        binding.settings.setOnClickListener{
            startActivity(Intent(this,AlertActivity::class.java))
        }
        binding.backBtn1.setOnClickListener{
            onBackPressed()
        }
    }

}
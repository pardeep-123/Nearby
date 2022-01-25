package com.creation.nearby.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.NotificationAdapter
import com.creation.nearby.databinding.ActivityNotificationsBinding
import com.creation.nearby.fragments.HomeFragment
import com.creation.nearby.model.NotificationModel

class NotificationsActivity : AppCompatActivity(),View.OnClickListener {

    lateinit var binding: ActivityNotificationsBinding

    companion object{
        lateinit var notificationAdapter: NotificationAdapter
    }
    private var notificationList = ArrayList<NotificationModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.settings.setOnClickListener(this)

        //notification recycler view
        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,false)

        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Brooklyn Simmons","You are friends! \uD83C\uDF89","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))

        HomeFragment.notificationAdapter = NotificationAdapter(notificationList)
        binding.notificationRecyclerView.adapter = HomeFragment.notificationAdapter
        HomeFragment.notificationAdapter.notifyDataSetChanged()

        //notification recycler view


    }

    override fun onClick(v: View?) {
       when(v){
           binding.settings->{
               startActivity(Intent(this,AlertActivity::class.java))
           }
       }
    }
}
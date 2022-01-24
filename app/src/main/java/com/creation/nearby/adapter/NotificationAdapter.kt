package com.creation.nearby.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.databinding.DiscoverItemBinding
import com.creation.nearby.databinding.ItemNotificationBinding
import com.creation.nearby.fragments.HomeFragment
import com.creation.nearby.model.DiscoverModel
import com.creation.nearby.model.NotificationModel
import com.creation.nearby.ui.NotificationsActivity
import com.zerobranch.layout.SwipeLayout
import com.zerobranch.layout.SwipeLayout.SwipeActionsListener

class NotificationAdapter(private val mList: ArrayList<NotificationModel>) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){

            with(mList[position]){

                binding.userPic.setImageResource(userPic)
                binding.tvName.text = name
                binding.tvMessage.text = message
                binding.messageDay.text = day


                binding.swipeLayout.setOnActionsListener(object : SwipeActionsListener {
                    override fun onOpen(direction: Int, isContinuous: Boolean) {
                        if (direction == SwipeLayout.RIGHT) {
                            binding.messageDay.visibility = VISIBLE
                        } else if (direction == SwipeLayout.LEFT) {
                            binding.messageDay.visibility = GONE
                        }

                    }

                    override fun onClose() {
                        binding.messageDay.visibility = VISIBLE
                    }
                })

                binding.removeView.setOnClickListener{

                    if(isHomeFragment){
                        mList.removeAt(position)
                        HomeFragment.notificationAdapter.notifyDataSetChanged()

                    }else{

                        mList.removeAt(position)
                        NotificationsActivity.notificationAdapter.notifyDataSetChanged()

                    }

                }

            }


        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : ItemNotificationBinding): RecyclerView.ViewHolder(binding.root)

}
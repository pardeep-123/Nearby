package com.creation.nearby.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.databinding.ItemChatBinding
import com.creation.nearby.databinding.ItemEventsBinding
import com.creation.nearby.model.ChatModel
import com.creation.nearby.model.EventsModel
import com.creation.nearby.model.NotificationModel
import com.creation.nearby.ui.OngoingChatActivity

class ChatsAdapter(private val mList: ArrayList<ChatModel>) : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){

            with(mList[position]){

                binding.userPic.setImageResource(userPic)
                binding.tvName.text = name
                binding.tvMessage.text = message
                binding.messageDay.text = day

                if (isOnline){

                    binding.circleImageView.visibility = View.VISIBLE

                }else{
                    binding.circleImageView.visibility = View.GONE

                }

                itemView.setOnClickListener{
                    itemView.context.startActivity(Intent(itemView.context,OngoingChatActivity::class.java))
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : ItemChatBinding): RecyclerView.ViewHolder(binding.root)

}
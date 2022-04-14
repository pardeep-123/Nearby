package com.creation.nearby.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.ItemChatBinding
import com.creation.nearby.model.ChatListModel

import com.creation.nearby.ui.OngoingChatActivity
import com.creation.nearby.utils.Constants

class ChatsAdapter(private val mList: List<ChatListModel.ChatListModelItem>) : RecyclerView.Adapter<ChatsAdapter.ViewHolder>() {

   lateinit var ctx : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        ctx = parent.context
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){

            with(mList[position]){

                Glide.with(ctx).load(Constants.IMAGE_BASE_URL+userImage).into(binding.userPic)
                binding.tvName.text = FirstName+LastName
                binding.tvMessage.text = lastMessage
              //  binding.messageDay.text = day

//                if (isOnline){
//
//                    binding.circleImageView.visibility = View.VISIBLE
//
//                }else{
//                    binding.circleImageView.visibility = View.GONE
//
//                }

                itemView.setOnClickListener{
                    val intent = Intent(itemView.context,OngoingChatActivity::class.java)
                    intent.putExtra("name",mList[position].FirstName+mList[position].LastName)
                    intent.putExtra("image",mList[position].userImage)
                         if (PreferenceFile.retrieveUserId() == mList[position].user2id.toString())
                    intent.putExtra("user2Id",mList[position].userid.toString())
                    else
                             intent.putExtra("user2Id",mList[position].user2id.toString())

                    itemView.context.startActivity(intent)
//                    itemView.context.startActivity(Intent(itemView.context,OngoingChatActivity::class.java))
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : ItemChatBinding): RecyclerView.ViewHolder(binding.root)

}
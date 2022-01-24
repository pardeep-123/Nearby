package com.creation.nearby.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.databinding.ItemOnlineChatUserBinding
import com.creation.nearby.model.OnlineUserChatModel

class OnlineUserChatAdapter(private val mList: ArrayList<OnlineUserChatModel>) : RecyclerView.Adapter<OnlineUserChatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemOnlineChatUserBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){

            with(mList[position]){

                binding.friendsImage.setImageResource(friendsPic)

            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : ItemOnlineChatUserBinding): RecyclerView.ViewHolder(binding.root)

}
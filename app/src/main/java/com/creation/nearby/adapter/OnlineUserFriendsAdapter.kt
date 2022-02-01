package com.creation.nearby.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.databinding.ItemFriendsBinding
import com.creation.nearby.databinding.ItemOnlineChatUserBinding
import com.creation.nearby.model.FriendsModel
import com.creation.nearby.model.OnlineUserChatModel
import com.creation.nearby.ui.DetailedProfileActivity

class OnlineUserFriendsAdapter(private val mList: ArrayList<FriendsModel>) : RecyclerView.Adapter<OnlineUserFriendsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemFriendsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){

            with(mList[position]){

                binding.friendsImage.setImageResource(friendsPic)
                binding.friendsName.text = friendsName

                if (isActive){

                    binding.friendsActive.visibility = View.VISIBLE

                }else{
                    binding.friendsActive.visibility = View.GONE

                }

                itemView.setOnClickListener{
                    itemView.context.startActivity(Intent(itemView.context,DetailedProfileActivity::class.java))
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : ItemFriendsBinding): RecyclerView.ViewHolder(binding.root)

}
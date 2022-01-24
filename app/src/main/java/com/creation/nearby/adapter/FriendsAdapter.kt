package com.creation.nearby.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.databinding.ItemEventsBinding
import com.creation.nearby.databinding.ItemFriendsBinding
import com.creation.nearby.model.EventsModel
import com.creation.nearby.model.FriendsModel

class FriendsAdapter(private val mList: ArrayList<FriendsModel>) : RecyclerView.Adapter<FriendsAdapter.ViewHolder>() {

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

            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : ItemFriendsBinding): RecyclerView.ViewHolder(binding.root)

}
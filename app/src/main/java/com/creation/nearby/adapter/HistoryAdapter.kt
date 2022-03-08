package com.creation.nearby.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.databinding.ItemEventsBinding
import com.creation.nearby.databinding.ItemHistoryMessageBinding
import com.creation.nearby.databinding.ItemPostsBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.EventsModel
import com.creation.nearby.model.HistoryModel
import com.creation.nearby.model.PostModel

class HistoryAdapter(private val mList: ArrayList<HistoryModel>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemHistoryMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){

            with(mList[position]){

                binding.userHistoryPic.setImageResource(userImage)
                binding.userTime.text = userTime
                binding.userMessage.text = userMessage
                if (replyMessage.isNotEmpty()){

                    binding.replyPic.setImageResource(replyPic)
                    binding.replyTime.text = replyTime
                    binding.replyMessage.text = replyMessage
                }else{
                    binding.replyTime.visibility = View.INVISIBLE
                    binding.replyMessage.text = "-No Reply-"
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : ItemHistoryMessageBinding): RecyclerView.ViewHolder(binding.root)

}
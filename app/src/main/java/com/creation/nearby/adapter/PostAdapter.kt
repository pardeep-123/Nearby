package com.creation.nearby.adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.databinding.ItemEventsBinding
import com.creation.nearby.databinding.ItemPostsBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.EventsModel
import com.creation.nearby.model.PostModel
import com.creation.nearby.ui.OngoingChatActivity

class PostAdapter(private val mList: ArrayList<PostModel>,var onActionListener: OnActionListener<PostModel>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemPostsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var model:PostModel = mList[position]

        with(holder){

            with(mList[position]){

                binding.postImageView.setImageResource(postProfilePic)
                binding.postUserName.text = postName
                binding.postTimeTv.text = postTime
                binding.postMessageTv.text = postMessage
                if(postPicture != null && !postPicture.equals("")){

                    binding.postImage.setImageResource(postPicture)

                }else{
                    binding.postImage.visibility = View.GONE
                }
                binding.postReportIv.setOnClickListener{
                    onActionListener.notify(model,position,holder.itemView)
                }
                binding.postMessageBtn.setOnClickListener{
                    holder.itemView.context.startActivity(Intent(itemView.context,OngoingChatActivity::class.java))
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : ItemPostsBinding): RecyclerView.ViewHolder(binding.root)

}
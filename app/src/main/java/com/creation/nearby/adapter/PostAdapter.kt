package com.creation.nearby.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.databinding.ItemEventsBinding
import com.creation.nearby.databinding.ItemPostsBinding
import com.creation.nearby.model.EventsModel
import com.creation.nearby.model.PostModel

class PostAdapter(private val mList: ArrayList<PostModel>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemPostsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){

            with(mList[position]){

                binding.postImageView.setImageResource(postPic)
                binding.postUserName.text = postName
                binding.postTimeTv.text = postTime
                binding.postMessageTv.text = postMessage
                binding.postMainLayout.setBackgroundColor(itemView.resources.getColor(backgroundColor))

            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : ItemPostsBinding): RecyclerView.ViewHolder(binding.root)

}
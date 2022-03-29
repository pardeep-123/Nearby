package com.creation.nearby.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.databinding.ItemGallaryBinding
import com.creation.nearby.model.FileUploadModel
import com.creation.nearby.model.ImageModel
import com.creation.nearby.ui.EditProfileActivity
import com.creation.nearby.utils.Constants.IMAGE_BASE_URL


class ImageAdapter(
    var context: Context,
    var imagesList: MutableList<FileUploadModel.Body>,
    var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onRemoveImage(position:Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGallaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load("${IMAGE_BASE_URL}${imagesList[position].image}")
            .placeholder(R.drawable.placeholder)
            .into(holder.binding.layoutImage)

        holder.binding.closeImage.setOnClickListener {
            onItemClickListener.onRemoveImage(position)
        }
    }

    override fun getItemCount(): Int {
        return imagesList.size
    }

    class ViewHolder(var binding: ItemGallaryBinding) : RecyclerView.ViewHolder(binding.root)
}
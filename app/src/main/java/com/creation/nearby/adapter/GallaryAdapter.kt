package com.creation.nearby.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.databinding.ItemGallaryBinding
import com.creation.nearby.databinding.ItemMyGallaryBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.GallaryModel
import com.creation.nearby.model.ImageModel
import com.creation.nearby.openImagePopUp
import com.creation.nearby.ui.FullPictureActivity
import com.creation.nearby.utils.Constants


class GallaryAdapter(var context: Context, var items: ArrayList<String>, var onActionListener: OnActionListener<GallaryModel>) : RecyclerView.Adapter<GallaryAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMyGallaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        with(holder.binding){

                Glide.with(context).load(Constants.IMAGE_BASE_URL+items[position]).placeholder(R.drawable.placeholder).error(R.drawable.placeholder)
                    .into(layoutImage)

                layoutImage.setOnClickListener{

                    openImagePopUp(Constants.IMAGE_BASE_URL+items[position],context)
//                    onActionListener.notify(item,position,holder.itemView)

                }



        }

    }

    override fun getItemCount(): Int {
       return items.size
    }

    class ViewHolder(var binding: ItemMyGallaryBinding) :RecyclerView.ViewHolder(binding.root)
}
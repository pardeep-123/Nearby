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
import com.creation.nearby.R
import com.creation.nearby.databinding.ItemGallaryBinding
import com.creation.nearby.databinding.ItemMyGallaryBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.GallaryModel
import com.creation.nearby.model.ImageModel
import com.creation.nearby.ui.FullPictureActivity


class GallaryAdapter(var context: Context, var items: ArrayList<GallaryModel>, var onActionListener: OnActionListener<GallaryModel>) : RecyclerView.Adapter<GallaryAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMyGallaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item: GallaryModel = items[position]
        with(holder.binding){

            with(items[position]){

                layoutImage.setImageResource(imageUrl)

                layoutImage.setOnClickListener{

                    onActionListener.notify(item,position,holder.itemView)

                }

            }

        }

    }

    override fun getItemCount(): Int {
       return items.size
    }

    class ViewHolder(var binding: ItemMyGallaryBinding) :RecyclerView.ViewHolder(binding.root)
}
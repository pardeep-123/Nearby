package com.creation.nearby.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.databinding.ItemGallaryBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.ImageModel


class ImageAdapter(var context: Context,var items: ArrayList<ImageModel>, var onActionListener: OnActionListener<ImageModel>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGallaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model = if (items.size == position)
            ImageModel()
        else
            items[position]

        with(model)
        {
            with(holder.binding) {
                if (imageUri == null) {
                    layoutAdd.visibility = View.VISIBLE
                    plusImage.visibility = View.VISIBLE
                    layoutCard.visibility = View.GONE

                    layoutAdd.setOnClickListener {
                        onActionListener.notify(model, position,it)
                    }

                } else {
                    layoutAdd.visibility = View.GONE
                    plusImage.visibility = View.GONE
                    layoutCard.visibility = View.VISIBLE
                    layoutImage.setImageURI(imageUri)

                    closeImage.setOnClickListener {

                        onActionListener.notify(model, position,it)
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
       return items.size + 1
    }

    class ViewHolder(var binding: ItemGallaryBinding) :RecyclerView.ViewHolder(binding.root)
}
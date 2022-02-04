package com.creation.nearby.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creation.nearby.databinding.ItemGallaryBinding
import com.creation.nearby.model.ImageModel
import com.creation.nearby.ui.EditProfileActivity


class ImageAdapter(
    var context: Context,
    var items: ArrayList<ImageModel>,
    var onActionListener: EditProfileActivity
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGallaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var model: ImageModel = items[position]

        /* val model = if (items.size == position)
            ImageModel()
        else
            items[position]
*/
        with(items[position])
        {
            with(holder.binding) {


                holder.binding.closeImage.setOnClickListener {
                    items[position].imagePath = ""
                    items[position].isDeleteL = true
                    notifyDataSetChanged()
                }

                holder.binding.layoutAdd.setOnClickListener {
                    onActionListener.getPosition(position)
                }
                if (items[position].imagePath.isEmpty()) {

                    layoutCard.visibility = View.GONE
                    layoutAdd.visibility = View.VISIBLE


                } else {
                    layoutCard.visibility = View.VISIBLE
                    layoutAdd.visibility = View.GONE

                    Glide.with(context).load(items[position].imagePath).into(layoutImage)


                }


            }

        }


    }

    override fun getItemCount(): Int {
        return 6
    }

    class ViewHolder(var binding: ItemGallaryBinding) : RecyclerView.ViewHolder(binding.root)
}
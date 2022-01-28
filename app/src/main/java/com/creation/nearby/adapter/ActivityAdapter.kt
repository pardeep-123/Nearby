package com.creation.nearby.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.databinding.ItemActivitiesBinding
import com.creation.nearby.fragments.*
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.ActivitiesModel

class ActivityAdapter(val mList: ArrayList<ActivitiesModel>,var onActionListener: OnActionListener<ActivitiesModel>) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemActivitiesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var model: ActivitiesModel = mList[position]

        with(holder){

            with(mList[position]){

                binding.activityPic.setImageResource(activityPic)
                binding.activityName.text = activityName

                if (activityName == "Home"){
                        binding.activityPic.imageTintList = itemView.resources.getColorStateList(R.color.blue)
                        binding.activityName.setTextColor(itemView.resources.getColorStateList(R.color.blue))
                }else{
                    binding.activityPic.imageTintList = itemView.resources.getColorStateList(R.color.black_dark_transparent)
                    binding.activityName.setTextColor(itemView.resources.getColorStateList(R.color.black_dark_transparent))
                }
                itemView.setOnClickListener {

                    onActionListener.notify(model,position,holder.itemView)


                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding: ItemActivitiesBinding): RecyclerView.ViewHolder(binding.root)


}
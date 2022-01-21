package com.creation.nearby.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.databinding.ItemActivitiesBinding
import com.creation.nearby.model.ActivitiesModel

class ActivityAdapter(val mList: ArrayList<ActivitiesModel>) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemActivitiesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        with(holder){

            with(mList[position]){

                binding.activityPic.setImageResource(activityPic)
                binding.activityName.text = activityName


                if (activityName == "Home"){

                        binding.activityPic.imageTintList = itemView.resources.getColorStateList(R.color.blue)
                        binding.activityName.setTextColor(itemView.resources.getColorStateList(R.color.blue))

                }
            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding: ItemActivitiesBinding): RecyclerView.ViewHolder(binding.root)


}
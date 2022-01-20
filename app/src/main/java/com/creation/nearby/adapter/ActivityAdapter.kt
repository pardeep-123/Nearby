package com.creation.nearby.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.model.ActivitiesModel
import kotlinx.android.synthetic.main.item_activities.view.*

class ActivityAdapter(val mList: ArrayList<ActivitiesModel>) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_activities,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.itemView.activity_pic.setImageResource(item.activityPic)
        holder.itemView.activity_name.text = item.activityName

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {


    }

}
package com.creation.nearby.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.model.ActivitiesModel
import com.creation.nearby.model.DiscoverModel
import kotlinx.android.synthetic.main.discover_item.view.*
import kotlinx.android.synthetic.main.item_activities.view.*

class DiscoverAdapter(val mList: ArrayList<DiscoverModel>) : RecyclerView.Adapter<DiscoverAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.discover_item,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.itemView.distance.text = item.distance
        holder.itemView.titleName.text = item.name
        holder.itemView.topicName.text = item.topicName

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {


    }

}
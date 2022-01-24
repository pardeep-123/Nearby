package com.creation.nearby.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.databinding.ItemEventsBinding
import com.creation.nearby.model.EventsModel

class EventsAdapter(private val mList: ArrayList<EventsModel>) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemEventsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){

            with(mList[position]){

                binding.eventsPic.setImageResource(eventImage)
                binding.eventsName.text = eventName
                binding.eventsDistance.text = eventDistance

            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : ItemEventsBinding): RecyclerView.ViewHolder(binding.root)

}
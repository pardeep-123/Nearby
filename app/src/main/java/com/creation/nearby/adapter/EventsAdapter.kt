package com.creation.nearby.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creation.nearby.databinding.ItemEventsBinding
import com.creation.nearby.model.EventsModel
import com.creation.nearby.model.GetEventModel
import com.creation.nearby.ui.EventDetailsActivity

class EventsAdapter(val mList: ArrayList<GetEventModel.Body>) : RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemEventsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        context = parent.context
        return ViewHolder(binding)

    }

//    fun addItems(items: List<T>) {
//        this.items.clear()
//        this.items.addAll(items)
//        notifyDataSetChanged()
//    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder){

            with(mList[position]){

                Glide.with(context).load(image).into(binding.eventsPic)
                binding.eventsName.text = title
              //  binding.eventsDistance.text = body[position].

                binding.eventsPic.setOnClickListener{
                    holder.itemView.context.startActivity(Intent(holder.itemView.context,EventDetailsActivity::class.java))
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return mList.size
    }
    class ViewHolder(val binding : ItemEventsBinding): RecyclerView.ViewHolder(binding.root)

}
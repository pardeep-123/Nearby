package com.creation.nearby.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.databinding.DiscoverItemBinding
import com.creation.nearby.fragments.EventsFragment
import com.creation.nearby.fragments.FeedFragment
import com.creation.nearby.model.DiscoverModel
import com.creation.nearby.ui.MainActivity

class FeedAdapter(private val mList: ArrayList<DiscoverModel>) : RecyclerView.Adapter<FeedAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = DiscoverItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        val item = mList[position]

        with(holder){

            binding.distance.text = item.distance
            binding.titleName.text = item.name
            binding.topicName.text = item.topicName

            holder.itemView.setOnClickListener{
              /*  var  fragmentTransaction = (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.selection_frame_layout, FeedFragment())
                fragmentTransaction.commit()*/

                MainActivity.binding.sectionRecyclerView.postDelayed( Runnable {

                    MainActivity.binding.sectionRecyclerView.scrollToPosition(4)

                    MainActivity.binding.sectionRecyclerView.postDelayed( Runnable {
                        MainActivity.binding.sectionRecyclerView.findViewHolderForAdapterPosition(4)?.itemView?.performClick()


                    },10)

                },50)
            }



        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : DiscoverItemBinding): RecyclerView.ViewHolder(binding.root)

}
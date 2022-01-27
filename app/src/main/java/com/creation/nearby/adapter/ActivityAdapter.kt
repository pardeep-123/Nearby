package com.creation.nearby.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.databinding.ItemActivitiesBinding
import com.creation.nearby.fragments.*
import com.creation.nearby.model.ActivitiesModel

class ActivityAdapter(val mList: ArrayList<ActivitiesModel>) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemActivitiesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


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

                    if (activityName == "Home"){

                        binding.activityPic.imageTintList = itemView.resources.getColorStateList(R.color.blue)
                        binding.activityName.setTextColor(itemView.resources.getColorStateList(R.color.blue))
                        val fragment = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                        fragment.replace(R.id.selection_frame_layout,HomeFragment())
                        fragment.commit()
                    }


                    if (activityName == "Map"){

                        binding.activityPic.imageTintList = itemView.resources.getColorStateList(R.color.green1)
                        binding.activityName.setTextColor(itemView.resources.getColorStateList(R.color.green1))
                        val fragment = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                        fragment.replace(R.id.complete_frame_layout,MapFragment())
                        fragment.commit()
                    }
                    if (activityName == "Events"){

                        binding.activityPic.imageTintList = itemView.resources.getColorStateList(R.color.sky_blue)
                        binding.activityName.setTextColor(itemView.resources.getColorStateList(R.color.sky_blue))
                        val fragment = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                        fragment.replace(R.id.selection_frame_layout,EventsFragment())
                        fragment.commit()
                    }

                    if (activityName == "Friends"){

                        binding.activityPic.imageTintList = itemView.resources.getColorStateList(R.color.red)
                        binding.activityName.setTextColor(itemView.resources.getColorStateList(R.color.red))
                        val fragment = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                        fragment.replace(R.id.selection_frame_layout,FriendsFragment())
                        fragment.commit()
                    }

                    if (activityName == "Feed"){

                        binding.activityPic.imageTintList = itemView.resources.getColorStateList(R.color.blue)
                        binding.activityName.setTextColor(itemView.resources.getColorStateList(R.color.blue))
                        val fragment = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                        fragment.replace(R.id.selection_frame_layout,FeedFragment())
                        fragment.commit()
                    }

                    if (activityName == "Swipe"){
                        binding.activityPic.imageTintList = itemView.resources.getColorStateList(R.color.blue)
                        binding.activityName.setTextColor(itemView.resources.getColorStateList(R.color.blue))
                        val fragment = (itemView.context as FragmentActivity).supportFragmentManager.beginTransaction()
                        fragment.replace(R.id.selection_frame_layout,SwipeCardFragment())
                        fragment.commit()
                    }
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding: ItemActivitiesBinding): RecyclerView.ViewHolder(binding.root)


}
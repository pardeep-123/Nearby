package com.creation.nearby.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.databinding.SwipeCardItemBinding
import com.creation.nearby.model.SwipeCardModel
import com.creation.nearby.ui.DetailedProfileActivity

class SwipeCardAdapter(val context: Context,var swipeList: ArrayList<SwipeCardModel> ):RecyclerView.Adapter<SwipeCardAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeCardAdapter.ViewHolder {

        val binding = SwipeCardItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SwipeCardAdapter.ViewHolder, position: Int) {
        with(holder.binding){

            with(swipeList[position]){

                swipePic.setImageResource(pic)
                swipeUserName.text = name
                swipeUserAgeCity.text =age.plus(" - ").plus(city)


                userDetailLayout.setOnClickListener{
                    context.startActivity(Intent(context,DetailedProfileActivity::class.java))
                }
            }

        }
    }

    override fun getItemCount(): Int {
      return swipeList.size
    }

     class ViewHolder(val binding: SwipeCardItemBinding):RecyclerView.ViewHolder(binding.root)
}
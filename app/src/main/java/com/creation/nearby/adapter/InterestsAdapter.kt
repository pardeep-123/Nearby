package com.creation.nearby.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.databinding.InterestsItemBinding
import com.creation.nearby.model.GenericModel
import com.creation.nearby.model.InterestedModel
import com.creation.nearby.model.SuggestionsModel

class InterestsAdapter(private val mList: ArrayList<InterestedModel>) : RecyclerView.Adapter<InterestsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = InterestsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder.binding){

            with(mList[position]){

                interest.text = interestName

                if (isProfile){

                    interest.background = holder.itemView.resources.getDrawable(R.drawable.round_background_with_transparent)
                    interest.setTextColor(holder.itemView.resources.getColorStateList(R.color.white))
                }else{


                    if (isSelected){

                        interest.backgroundTintList = holder.itemView.resources.getColorStateList(R.color.sky_blue)
                        interest.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.white))

                    }else{
                        interest.backgroundTintList = holder.itemView.resources.getColorStateList(R.color.edittext_grey)
                        interest.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.black))

                    }

                    holder.itemView.setOnClickListener{

                        if (isSelected){

                            isSelected = false
                            interest.backgroundTintList = holder.itemView.resources.getColorStateList(R.color.edittext_grey)
                            interest.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.black))

                        }else{

                            isSelected = true
                            interest.backgroundTintList = holder.itemView.resources.getColorStateList(R.color.sky_blue)
                            interest.setTextColor(ContextCompat.getColor(holder.itemView.context,R.color.white))


                        }
                    }

                }

            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : InterestsItemBinding): RecyclerView.ViewHolder(binding.root)

}
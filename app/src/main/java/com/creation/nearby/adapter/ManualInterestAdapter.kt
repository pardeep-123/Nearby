package com.creation.nearby.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.databinding.InterestsItemBinding
import com.creation.nearby.model.ManualInterestModel

class ManualInterestAdapter(
    private val mContext: Context,
    private val interestList: MutableList<ManualInterestModel>
) : RecyclerView.Adapter<ManualInterestAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = InterestsItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemBinding.interest.text = interestList[position].itemTitle

        if (interestList[position].isSelected==1){
            holder.itemBinding.interest.backgroundTintList = ContextCompat.getColorStateList(mContext,
                R.color.sky_blue)
            holder.itemBinding.interest.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))

        }else{
            holder.itemBinding.interest.backgroundTintList = ContextCompat.getColorStateList(mContext,
                R.color.edittext_grey)
            holder.itemBinding.interest.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        }

        holder.itemView.setOnClickListener{
            if (interestList[position].isSelected==1){
                interestList[position].isSelected = 0
                holder.itemBinding.interest.backgroundTintList = ContextCompat.getColorStateList(mContext,
                    R.color.edittext_grey)
                holder.itemBinding.interest.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))

            }else{
                interestList[position].isSelected = 1
                holder.itemBinding.interest.backgroundTintList = ContextCompat.getColorStateList(mContext,
                    R.color.sky_blue)
                holder.itemBinding.interest.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            }
        }

    }

    override fun getItemCount(): Int {
        return interestList.size
    }

    class MyViewHolder(itemView: InterestsItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        val itemBinding: InterestsItemBinding = itemView
    }

}

package com.creation.nearby.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.databinding.InterestsItemBinding
import com.creation.nearby.model.InterestedModel
import com.creation.nearby.model.SuggestionsModel

class OtherProfileInterestAdapter(private val mList: ArrayList<InterestedModel>) : RecyclerView.Adapter<OtherProfileInterestAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = InterestsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(holder.binding){

            with(mList[position]) {

                interest.text = interestName

            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : InterestsItemBinding): RecyclerView.ViewHolder(binding.root)

}
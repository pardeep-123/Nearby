package com.creation.nearby.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.databinding.ItemEventsBinding
import com.creation.nearby.databinding.ItemPostsBinding
import com.creation.nearby.databinding.SuggestionsItemBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.EventsModel
import com.creation.nearby.model.PostModel
import com.creation.nearby.model.SuggestionsModel

class SuggestionAdapter(private val mList: ArrayList<SuggestionsModel>,val onActionListener: OnActionListener<SuggestionsModel>) : RecyclerView.Adapter<SuggestionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = SuggestionsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var model: SuggestionsModel = mList[position]

        with(holder){

            with(mList[position]){

                binding.suggestion.text = suggestion

                binding.suggestion.setOnClickListener{

                    onActionListener.notify(model,position,itemView)
                }


            }
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding : SuggestionsItemBinding): RecyclerView.ViewHolder(binding.root)

}
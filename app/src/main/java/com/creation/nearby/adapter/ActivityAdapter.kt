package com.creation.nearby.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.databinding.ItemActivitiesBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.ActivitiesModel

class ActivityAdapter(
    val mList: ArrayList<ActivitiesModel>,
    var onActionListener: OnActionListener<ActivitiesModel>
) : RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {

    private var selectPosition = 0
    private var color = R.color.blue

   // var onClickListener: ((pos: Int,id: ActivitiesModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            ItemActivitiesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val model: ActivitiesModel = mList[position]

        with(holder) {

            with(mList[position]) {

                binding.activityPic.setImageResource(activityPic)
                binding.activityName.text = activityName

                if (selectPosition == position) {

                    binding.activityName.setTextColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            color
                        )
                    )
                    binding.activityPic.imageTintList =
                        ActivityCompat.getColorStateList(itemView.context, color)

                } else {
                    binding.activityName.setTextColor(
                        ContextCompat.getColor(
                            holder.itemView.context,
                            R.color.black_dark_transparent
                        )
                    )
                    binding.activityPic.imageTintList = ActivityCompat.getColorStateList(
                        itemView.context,
                        R.color.black_dark_transparent
                    )
                }

                itemView.setOnClickListener {

                 //   onClickListener?.invoke(position,mList[position])

                    onActionListener.notify(model, position, holder.itemView)

                    selectPosition = position
                        if (position == 0) {

                            color = R.color.blue
                        }

                        if (position == 1) {
                            color = R.color.teal
                        }

                        if (position == 2) {
                            color = R.color.teal
                        }

                        if (position == 3) {

                            color = R.color.sky_blue
                        }

                        if (position == 4) {

                            color = R.color.blue
                        }

                        if (position == 5) {

                            color = R.color.red
                        }

                    notifyDataSetChanged()

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding: ItemActivitiesBinding) : RecyclerView.ViewHolder(binding.root) {

    }


}
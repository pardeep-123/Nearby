package com.creation.nearby.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.ItemChatBinding
import com.creation.nearby.databinding.RightUserChatLayoutBinding
import com.creation.nearby.model.OneToOneChatListModel
import com.creation.nearby.utils.Constants
import de.hdodenhof.circleimageview.CircleImageView

class OnlineUserChatAdapter(private var mList: ArrayList<OneToOneChatListModel.OneToOneChatListModelItem>) : RecyclerView.Adapter<OnlineUserChatAdapter.ViewHolder>() {

    val TYPE_USER = 0
    val TYPE_FRIEND = 2
    lateinit var ctx : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
          ctx = parent.context
//        if (viewType == TYPE_USER){
//
//            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.right_user_chat_layout,parent,false))
//        }else if(viewType == TYPE_FRIEND){
//            return OtherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.left_chat_user_layout,parent,false))
//
//        }
        val binding = RightUserChatLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        ctx = parent.context
        return ViewHolder(binding)
//         return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.right_user_chat_layout,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        if (holder.itemViewType == TYPE_FRIEND) {
//
//            val friendViewHolder = holder as OtherViewHolder
//            friendViewHolder.txtUserView.text = mList[position].message
//            Glide.with(ctx).load(Constants.IMAGE_BASE_URL+mList[position].senderImage).into(friendViewHolder.txtImageView)
//
//        } else if (holder.itemViewType == TYPE_USER) {
//            val userViewHolder = holder as ViewHolder
//            userViewHolder.txtUserView.text = mList[position].message

       // }
        if(mList[position].senderID.toString()== PreferenceFile.retrieveUserId()){
            holder.userLayout.visibility = View.GONE
            holder.senderLayout.visibility = View.VISIBLE
            holder.tvMessageSender.text = mList[position].message
        }else{
            holder.userLayout.visibility = View.VISIBLE
            holder.senderLayout.visibility = View.GONE
            holder.tvMessageUser.text = mList[position].message
            Glide.with(ctx).load(Constants.IMAGE_BASE_URL+mList[position].senderImage).into(holder.imageViewReceiver)
        }

    }

    override fun getItemViewType(position: Int): Int {
//        val data= mList[position]
//
//        if(data.senderID.toString()== PreferenceFile.retrieveUserId()){
//            return TYPE_USER
//        } else {
//            return TYPE_FRIEND
//        }
        return position
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(binding: RightUserChatLayoutBinding): RecyclerView.ViewHolder(binding.root){

        val tvMessageSender: TextView = itemView.findViewById(R.id.tvMessage)
        val tvMessageUser: TextView = itemView.findViewById(R.id.tvMessageUser)
        val senderLayout: LinearLayout = itemView.findViewById(R.id.senderLayout)
        val userLayout: ConstraintLayout = itemView.findViewById(R.id.userLayout)
        val imageViewReceiver: CircleImageView = itemView.findViewById(R.id.profile_imageR)

    }


}
package com.creation.nearby.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.OneToOneChatListModel
import de.hdodenhof.circleimageview.CircleImageView

class OnlineUserChatAdapter(private val mList: ArrayList<OneToOneChatListModel.OneToOneChatListModelItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_USER = 0
    val TYPE_FRIEND = 2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == TYPE_USER){

            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.right_user_chat_layout,parent,false))
        }else if(viewType == TYPE_FRIEND){
            return OtherViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.left_chat_user_layout,parent,false))

        }

         return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.left_chat_user_layout,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder.itemViewType == TYPE_FRIEND) {

            val friendViewHolder = holder as OtherViewHolder
            friendViewHolder.txtUserView.text = mList[position].message
          //  Glide.with(context).load(arrayList[position].recieverImage).into(friendViewHolder.profileimage)

        } else if (holder.itemViewType == TYPE_USER) {
            val userViewHolder = holder as ViewHolder
            userViewHolder.txtUserView.text = mList[position].message

        }


    }
    override fun getItemViewType(position: Int): Int {
        val data= mList[position]

        if(data.senderID.toString()== PreferenceFile.retrieveUserId()){
            return TYPE_USER
        } else {
            return TYPE_FRIEND
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        val txtUserView: TextView = itemView.findViewById(R.id.tvMessage)

    }
    class OtherViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val txtUserView: TextView = itemView.findViewById(R.id.tvMessage)
        val txtImageView: CircleImageView = itemView.findViewById(R.id.profile_imageR)

    }

}
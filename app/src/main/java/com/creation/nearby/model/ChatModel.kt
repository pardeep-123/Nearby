package com.creation.nearby.model

import com.creation.nearby.adapter.AbstractModel
import com.google.gson.annotations.SerializedName


//data class ChatModel(val userPic: Int, val name: String, val message: String, val day: String,val isOnline: Boolean)
class OneToOneChatListModel : ArrayList<OneToOneChatListModel.OneToOneChatListModelItem>() {

    data class OneToOneChatListModelItem(
        @SerializedName("created")
        val created: Int, // 1649676367
        @SerializedName("id")
        val id: Int, // 51
        @SerializedName("message")
        val message: String, // hiii
        @SerializedName("msg_type")
        val msgType: Int, // 0
        @SerializedName("ReceiverFirstName")
        val receiverFirstName: String, // tim smith
        @SerializedName("ReceiverId")
        val receiverId: Int, // 238
        @SerializedName("ReceiverImage")
        val receiverImage: String, // /public/uploads/user/1648789111673-file.jpeg
        @SerializedName("ReceiverLastName")
        val receiverLastName: String,
        @SerializedName("ReceiverRole")
        val receiverRole: Int, // 1
        @SerializedName("SenderFirstName")
        val senderFirstName: String, // pardeep sharma
        @SerializedName("SenderID")
        val senderID: Int, // 236
        @SerializedName("SenderImage")
        val senderImage: String, // /public/uploads/user/1648721207743-file.jpeg
        @SerializedName("SenderLastName")
        val senderLastName: String,
        @SerializedName("SenderRole")
        val senderRole: Int // 1
    ) : AbstractModel()

}
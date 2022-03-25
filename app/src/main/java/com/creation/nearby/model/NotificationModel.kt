package com.creation.nearby.model
import com.creation.nearby.adapter.AbstractModel
import com.google.gson.annotations.SerializedName


data class NotificationModel(
    @SerializedName("body")
    val body: ArrayList<Body>,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Notification list.
    @SerializedName("success")
    val success: Boolean // true
) : AbstractModel(){
    data class Body(
        @SerializedName("created")
        val created: Int, // 1647508611
        @SerializedName("createdAt")
        val createdAt: String, // 2022-03-17T09:16:50.000Z
        @SerializedName("data")
        val `data`: Data,
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("isRead")
        val isRead: Int, // 1
        @SerializedName("message")
        val message: String, // Swipe user successfully
        @SerializedName("notificationType")
        val notificationType: Int, // 1
        @SerializedName("receiver")
        val `receiver`: Receiver,
        @SerializedName("sender")
        val sender: Sender,
        @SerializedName("updated")
        val updated: Int, // 1647508611
        @SerializedName("updatedAt")
        val updatedAt: String, // 2022-03-24T12:22:21.000Z
        @SerializedName("user2Id")
        val user2Id: Int, // 180
        @SerializedName("userId")
        val userId: Int,
        var isNewRequest : Boolean = false // 182
    ) : AbstractModel(){
        class Data()

        data class Receiver(
            @SerializedName("email")
            val email: String, // user3@gmail.com
            @SerializedName("firstname")
            val firstname: String, // new
            @SerializedName("id")
            val id: Int, // 180
            @SerializedName("image")
            val image: String, // /public/uploads/user/1646399240816-file.png
            @SerializedName("lastname")
            val lastname: String // test
        ): AbstractModel()

        data class Sender(
            @SerializedName("email")
            val email: String, // newuser2@yopmail.com
            @SerializedName("firstname")
            val firstname: String, // new1
            @SerializedName("id")
            val id: Int, // 182
            @SerializedName("image")
            val image: String, // /public/uploads/user/1646399240816-file.png
            @SerializedName("lastname")
            val lastname: String
        ): AbstractModel()
    }
}
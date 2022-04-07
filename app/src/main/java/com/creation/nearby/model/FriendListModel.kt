package com.creation.nearby.model
import com.creation.nearby.adapter.AbstractModel
import com.google.gson.annotations.SerializedName


data class FriendListModel(
    @SerializedName("body")
    val body: Body,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Friend list.
    @SerializedName("success")
    val success: Boolean // true
): AbstractModel() {
    data class Body(
        @SerializedName("friend_list")
        val friendList: List<Friend>,
        @SerializedName("online_list")
        val onlineList: List<Online>
    ): AbstractModel() {
        data class Friend(
            @SerializedName("created")
            val created: Int, // 1649248839
            @SerializedName("createdAt")
            val createdAt: String, // 2022-04-06T12:40:38.000Z
            @SerializedName("first_name")
            val firstName: String, // pardeep sharma
            @SerializedName("id")
            val id: Int, // 209
            @SerializedName("image")
            val image: String, // /public/uploads/user/1648721207743-file.jpeg
            @SerializedName("last_name")
            val lastName: String,
            @SerializedName("status")
            val status: Int, // 2
            @SerializedName("swipe_by")
            val swipeBy: Int, // 238
            @SerializedName("swipe_to")
            val swipeTo: Int, // 236
            @SerializedName("updated")
            val updated: Int, // 1649248839
            @SerializedName("updatedAt")
            val updatedAt: String // 2022-04-06T12:58:51.000Z
        ) : AbstractModel()

        data class Online(
            @SerializedName("created")
            val created: Int, // 1649248839
            @SerializedName("createdAt")
            val createdAt: String, // 2022-04-06T12:40:38.000Z
            @SerializedName("first_name")
            val firstName: String, // pardeep sharma
            @SerializedName("id")
            val id: Int, // 209
            @SerializedName("image")
            val image: String, // /public/uploads/user/1648721207743-file.jpeg
            @SerializedName("last_name")
            val lastName: String,
            @SerializedName("status")
            val status: Int, // 2
            @SerializedName("swipe_by")
            val swipeBy: Int, // 238
            @SerializedName("swipe_to")
            val swipeTo: Int, // 236
            @SerializedName("updated")
            val updated: Int, // 1649248839
            @SerializedName("updatedAt")
            val updatedAt: String // 2022-04-06T12:58:51.000Z
        ): AbstractModel()
    }
}
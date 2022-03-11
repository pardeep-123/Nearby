package com.creation.nearby.model


import com.creation.nearby.adapter.AbstractModel
import com.google.gson.annotations.SerializedName

data class HomeListingModel(
    @SerializedName("body")
    val body: Body,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Home Listing
    @SerializedName("success")
    val success: Boolean // true
) : AbstractModel() {
    data class Body(
        @SerializedName("eventList")
        val eventList: ArrayList<Event>,
        @SerializedName("feedList")
        val feedList: ArrayList<Feed>,
        @SerializedName("notificationList")
        val notificationList: ArrayList<Notification>,
        @SerializedName("userList")
        val userList: ArrayList<User>
    ) : AbstractModel() {
        data class Event(
            @SerializedName("attend")
            val attend: String,
            @SerializedName("createdAt")
            val createdAt: String, // 2022-03-01T05:04:44.000Z
            @SerializedName("details")
            val details: String, // test
            @SerializedName("discount")
            val discount: String, // 12
            @SerializedName("id")
            val id: Int, // 46
            @SerializedName("image")
            val image: String, // 9eb97253-1d04-4954-9da3-4881f0c608ec.jpeg
            @SerializedName("latitude")
            val latitude: String, // 32.24318720
            @SerializedName("location")
            val location: String, // Manali, Himachal Pradesh, India
            @SerializedName("longitude")
            val longitude: String, // 77.18917610
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("time")
            val time: String, // 05:04
            @SerializedName("title")
            val title: String, // new event
            @SerializedName("updatedAt")
            val updatedAt: String, // 2022-03-01T05:04:44.000Z
            @SerializedName("userId")
            val userId: Int // 147
        ): AbstractModel()

        data class Feed(
            @SerializedName("createdAt")
            val createdAt: String, // 2021-11-26T11:31:47.000Z
            @SerializedName("description")
            val description: String, // seems to be adorable
            @SerializedName("id")
            val id: Int, // 4
            @SerializedName("latitude")
            val latitude: Double, // 0
            @SerializedName("location")
            val location: String, // Chandigarh
            @SerializedName("longitude")
            val longitude: Double, // 0
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("updatedAt")
            val updatedAt: String, // 2021-11-26T11:49:02.000Z
            @SerializedName("userId")
            val userId: Int,// 123
            @SerializedName("user")
            val user: FeedUser,

        ): AbstractModel()
        {
            data class FeedUser(
                @SerializedName("id")
                val id: Int, // 123
                @SerializedName("image")
                val image: String, // funsukh2.jpg
                @SerializedName("latitude")
                val latitude: String, // 0.000000
                @SerializedName("location")
                val location: String, // chandigarh
                @SerializedName("longitude")
                val longitude: String, // 0.000000
                @SerializedName("name")
                val name: String // Mark
            )
        }

        data class Notification(
            @SerializedName("created")
            val created: Int, // 1636634568
            @SerializedName("createdAt")
            val createdAt: String, // 2021-11-11T12:42:47.000Z
            @SerializedName("data")
            val `data`: String,
            @SerializedName("id")
            val id: Int, // 542
            @SerializedName("isRead")
            val isRead: Int, // 1
            @SerializedName("message")
            val message: String, // fy7uohui
            @SerializedName("notificationType")
            val notificationType: Int, // 4
            @SerializedName("updated")
            val updated: Int, // 1636634568
            @SerializedName("updatedAt")
            val updatedAt: String, // 2022-03-01T05:04:10.000Z
            @SerializedName("user2Id")
            val user2Id: Int, // 147
            @SerializedName("userId")
            val userId: Int // 0
        ): AbstractModel()

        data class User(
            @SerializedName("distance")
            val distance: Double, // 0
            @SerializedName("id")
            val id: Int, // 166
            @SerializedName("image")
            val image: String,
            @SerializedName("latitude")
            val latitude: String, // 0.000000
            @SerializedName("longitude")
            val longitude: String, // 0.000000
            @SerializedName("name")
            val name: String // Ashu Kumar
        ): AbstractModel()
    }
}
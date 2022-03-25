package com.creation.nearby.model


import com.creation.nearby.adapter.AbstractModel
import com.google.gson.annotations.SerializedName

//data class HomeListingModel(
//    @SerializedName("body")
//    val body: Body,
//    @SerializedName("code")
//    val code: Int, // 200
//    @SerializedName("message")
//    val message: String, // Home Listing
//    @SerializedName("success")
//    val success: Boolean // true
//) : AbstractModel() {
//    data class Body(
//        @SerializedName("eventList")
//        val eventList: ArrayList<Event>,
//        @SerializedName("feedList")
//        val feedList: ArrayList<Feed>,
//        @SerializedName("notificationList")
//        val notificationList: ArrayList<Notification>,
//        @SerializedName("userList")
//        val userList: ArrayList<User>
//    ) : AbstractModel() {
//        data class Event(
//            @SerializedName("attend")
//            val attend: String,
//            @SerializedName("createdAt")
//            val createdAt: String, // 2022-03-01T05:04:44.000Z
//            @SerializedName("details")
//            val details: String, // test
//            @SerializedName("discount")
//            val discount: String, // 12
//            @SerializedName("id")
//            val id: Int, // 46
//            @SerializedName("image")
//            val image: String, // 9eb97253-1d04-4954-9da3-4881f0c608ec.jpeg
//            @SerializedName("latitude")
//            val latitude: String, // 32.24318720
//            @SerializedName("location")
//            val location: String, // Manali, Himachal Pradesh, India
//            @SerializedName("longitude")
//            val longitude: String, // 77.18917610
//            @SerializedName("status")
//            val status: Int, // 1
//            @SerializedName("time")
//            val time: String, // 05:04
//            @SerializedName("title")
//            val title: String, // new event
//            @SerializedName("updatedAt")
//            val updatedAt: String, // 2022-03-01T05:04:44.000Z
//            @SerializedName("userId")
//            val userId: Int // 147
//        ): AbstractModel()
//
//        data class Feed(
//            @SerializedName("createdAt")
//            val createdAt: String, // 2021-11-26T11:31:47.000Z
//            @SerializedName("description")
//            val description: String, // seems to be adorable
//            @SerializedName("id")
//            val id: Int, // 4
//            @SerializedName("latitude")
//            val latitude: Double, // 0
//            @SerializedName("location")
//            val location: String, // Chandigarh
//            @SerializedName("longitude")
//            val longitude: Double, // 0
//            @SerializedName("status")
//            val status: Int, // 1
//            @SerializedName("updatedAt")
//            val updatedAt: String, // 2021-11-26T11:49:02.000Z
//            @SerializedName("userId")
//            val userId: Int,// 123
//            @SerializedName("user")
//            val user: FeedUser,
//
//        ): AbstractModel()
//        {
//            data class FeedUser(
//                @SerializedName("id")
//                val id: Int, // 123
//                @SerializedName("image")
//                val image: String, // funsukh2.jpg
//                @SerializedName("latitude")
//                val latitude: String, // 0.000000
//                @SerializedName("location")
//                val location: String, // chandigarh
//                @SerializedName("longitude")
//                val longitude: String, // 0.000000
//                @SerializedName("name")
//                val name: String,
//                @SerializedName("firstname")
//                val firstname: String,
//                @SerializedName("lastname")
//                val lastname: String,
//                // Mark
//            )
//        }
//
//        data class Notification(
//            @SerializedName("created")
//            val created: Int, // 1636634568
//            @SerializedName("createdAt")
//            val createdAt: String, // 2021-11-11T12:42:47.000Z
////            @SerializedName("data")
////            val `data`: String,
//            @SerializedName("id")
//            val id: Int, // 542
//            @SerializedName("isRead")
//            val isRead: Int, // 1
//            @SerializedName("message")
//            val message: String, // fy7uohui
//            @SerializedName("notificationType")
//            val notificationType: Int, // 4
//            @SerializedName("updated")
//            val updated: Int, // 1636634568
//            @SerializedName("updatedAt")
//            val updatedAt: String, // 2022-03-01T05:04:10.000Z
//            @SerializedName("user2Id")
//            val user2Id: Int, // 147
//            @SerializedName("userId")
//            val userId: Int // 0
//        ): AbstractModel()
//
//        data class User(
//            @SerializedName("distance")
//            val distance: Double, // 0
//            @SerializedName("id")
//            val id: Int, // 166
//            @SerializedName("image")
//            val image: String,
//            @SerializedName("latitude")
//            val latitude: String, // 0.000000
//            @SerializedName("longitude")
//            val longitude: String, // 0.000000
//            @SerializedName("firstname")
//            val firstName: String, // Ashu Kumar
//            @SerializedName("lastname")
//            val lastName: String
//        ): AbstractModel()
//    }
//}
data class HomeListingModel(
    @SerializedName("body")
    val body: Body,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Home Listing
    @SerializedName("success")
    val success: Boolean // true
): AbstractModel() {
    data class Body(
        @SerializedName("eventList")
        val eventList: ArrayList<Event>,
        @SerializedName("feedList")
        val feedList: ArrayList<Feed>,
        @SerializedName("notificationList")
        val notificationList: ArrayList<Notification>,
        @SerializedName("userList")
        val userList: ArrayList<User>
    ): AbstractModel() {
        data class Event(
            @SerializedName("attend")
            val attend: String,
            @SerializedName("createdAt")
            val createdAt: String, // 2022-03-24T04:04:17.000Z
            @SerializedName("details")
            val details: String, // check this event out…
            @SerializedName("discount")
            val discount: String, // 10
            @SerializedName("distance")
            val distance: Int, // 0
            @SerializedName("id")
            val id: Int, // 84
            @SerializedName("image")
            val image: String, // 573bb3d0-20ab-4798-b908-346daaf5331e.jpeg
            @SerializedName("latitude")
            val latitude: String, // 30.72931110
            @SerializedName("location")
            val location: String, // Balongi, Sahibzada Ajit Singh Nagar, Punjab, India
            @SerializedName("longitude")
            val longitude: String, // 76.69468720
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("time")
            val time: String, // 04:04
            @SerializedName("title")
            val title: String, // Get Together
            @SerializedName("updatedAt")
            val updatedAt: String, // 2022-03-24T04:04:17.000Z
            @SerializedName("userId")
            val userId: Int // 211
        ): AbstractModel()

        data class Feed(
            @SerializedName("createdAt")
            val createdAt: String, // 2022-03-24T04:53:26.000Z
            @SerializedName("description")
            val description: String, // test1
            @SerializedName("id")
            val id: Int, // 32
            @SerializedName("image")
            val image: String, // /public/uploads/feeds/1646828000543-file.jpeg
            @SerializedName("latitude")
            val latitude: String, // 0.00000000
            @SerializedName("location")
            val location: String,
            @SerializedName("longitude")
            val longitude: String, // 0.00000000
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("updatedAt")
            val updatedAt: String, // 2022-03-24T04:53:26.000Z
            @SerializedName("user")
            val user: User,
            @SerializedName("userId")
            val userId: Int // 212
        ) : AbstractModel(){
            data class User(
                @SerializedName("firstname")
                val firstname: String, // new2
                @SerializedName("id")
                val id: Int, // 212
                @SerializedName("image")
                val image: String,
                @SerializedName("lastname")
                val lastname: String, // user
                @SerializedName("latitude")
                val latitude: String, // 30.73330000
                @SerializedName("location")
                val location: String, // chandigarh
                @SerializedName("longitude")
                val longitude: String // 76.77940000
            ): AbstractModel()
        }

        data class Notification(
            @SerializedName("created")
            val created: Int, // 1648034747
            @SerializedName("createdAt")
            val createdAt: String, // 2022-03-23T11:25:46.000Z
            @SerializedName("data")
            val `data`: Data,
            @SerializedName("id")
            val id: Int, // 90
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
            val updated: Int, // 1648034747
            @SerializedName("updatedAt")
            val updatedAt: String, // 2022-03-25T05:40:34.000Z
            @SerializedName("user2Id")
            val user2Id: Int, // 207
            @SerializedName("userId")
            val userId: Int, // 208
            var isNewRequest : Boolean = false
        ): AbstractModel() {
            class Data(
            )

            data class Receiver(
                @SerializedName("email")
                val email: String, // test1@yopmail.com
                @SerializedName("firstname")
                val firstname: String, // test1
                @SerializedName("id")
                val id: Int, // 207
                @SerializedName("image")
                val image: String,
                @SerializedName("lastname")
                val lastname: String // abc
            ): AbstractModel()

            data class Sender(
                @SerializedName("email")
                val email: String, // user@gmail.com
                @SerializedName("firstname")
                val firstname: String, // Adam
                @SerializedName("id")
                val id: Int, // 208
                @SerializedName("image")
                val image: String, // /public/uploads/user/1648110420222-file.jpeg
                @SerializedName("lastname")
                val lastname: String // Reign
            ): AbstractModel()
        }

        data class User(
            @SerializedName("age")
            val age: Int, // 34
            @SerializedName("distance")
            val distance: Int, // 0
            @SerializedName("dob")
            val dob: String, // 1988-02-28
            @SerializedName("email")
            val email: String, // rishu@gmail.com
            @SerializedName("firstname")
            val firstname: String, // Rishu
            @SerializedName("gender")
            val gender: Int, // 1
            @SerializedName("id")
            val id: Int, // 216
            @SerializedName("is_liked")
            val isLiked: Int, // 0
            @SerializedName("is_online")
            val isOnline: Int, // 0
            @SerializedName("is_show_location")
            val isShowLocation: Int, // 0
            @SerializedName("is_subscribed")
            val isSubscribed: Int, // 0
            @SerializedName("join_date")
            val joinDate: String, // 2022-03-25
            @SerializedName("lastname")
            val lastname: String, // Sharma
            @SerializedName("latitude")
            val latitude: String, // 30.71194705
            @SerializedName("location")
            val location: String, // Mohali
            @SerializedName("longitude")
            val longitude: String, // 76.69269900
            @SerializedName("role")
            val role: Int, // 1
            @SerializedName("status")
            val status: Int, // 1
            @SerializedName("total_age")
            val totalAge: String, // 34
            @SerializedName("verified")
            val verified: Int // 0
        ): AbstractModel()
    }
}
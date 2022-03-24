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
                val name: String,
                @SerializedName("firstname")
                val firstname: String,
                @SerializedName("lastname")
                val lastname: String,
                // Mark
            )
        }

        data class Notification(
            @SerializedName("created")
            val created: Int, // 1636634568
            @SerializedName("createdAt")
            val createdAt: String, // 2021-11-11T12:42:47.000Z
//            @SerializedName("data")
//            val `data`: String,
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
            @SerializedName("firstname")
            val firstName: String, // Ashu Kumar
            @SerializedName("lastname")
            val lastName: String
        ): AbstractModel()
    }
}
/*
{
  "success": true,
  "code": 200,
  "message": "Home Listing",
  "body": {
    "eventList": [
      {
        "id": 74,
        "userId": 184,
        "time": "06:39",
        "title": "New Event",
        "details": "this is new event",
        "discount": "20",
        "location": "Delhi, India",
        "attend": "",
        "image": "d0509c56-ebd6-4860-8f5e-36a91b4dc30e.jpeg",
        "status": 1,
        "latitude": "28.68627380",
        "longitude": "77.22178310",
        "createdAt": "2022-03-17T06:39:16.000Z",
        "updatedAt": "2022-03-17T06:39:16.000Z",
        "distance": 0
      },
      {
        "id": 73,
        "userId": 181,
        "time": "06:15 PM",
        "title": "hiii",
        "details": "ggg",
        "discount": "ey",
        "location": "Sahibzada Ajit Singh Nagar, Punjab, India",
        "attend": "",
        "image": "60561b3e-6adf-4844-807f-f420ecf28abb.jpeg",
        "status": 1,
        "latitude": "30.70464860",
        "longitude": "76.71787260",
        "createdAt": "2022-03-16T09:45:55.000Z",
        "updatedAt": "2022-03-16T09:45:55.000Z",
        "distance": 0
      },
      {
        "id": 72,
        "userId": 181,
        "time": "02:59 PM",
        "title": "hey",
        "details": "hey alll",
        "discount": "10",
        "location": "Sahibzada Ajit Singh Nagar, Punjab, India",
        "attend": "",
        "image": "3e6ee832-b26b-46bb-9a6b-bd49d4d71eba.jpeg",
        "status": 1,
        "latitude": "30.70464860",
        "longitude": "76.71787260",
        "createdAt": "2022-03-16T09:30:07.000Z",
        "updatedAt": "2022-03-16T09:30:07.000Z",
        "distance": 0
      }
    ],
    "feedList": [
      {
        "id": 11,
        "userId": 184,
        "description": "What’s up today?",
        "image": "/public/uploads/feeds/1647499275551-file.jpeg",
        "location": "Mohali",
        "status": 1,
        "latitude": "30.71695500",
        "longitude": "76.71837600",
        "createdAt": "2022-03-17T06:41:23.000Z",
        "updatedAt": "2022-03-17T06:41:23.000Z",
        "user": {
          "id": 184,
          "name": "user 2",
          "latitude": "30.71695500",
          "longitude": "76.71837600",
          "location": "Sector 59, Mohali, India",
          "image": ""
        }
      },
      {
        "id": 10,
        "userId": 180,
        "description": "Good Morning",
        "image": "",
        "location": "Mohali",
        "status": 1,
        "latitude": "30.71695500",
        "longitude": "76.71837600",
        "createdAt": "2022-03-17T06:40:48.000Z",
        "updatedAt": "2022-03-17T06:40:48.000Z",
        "user": {
          "id": 180,
          "name": "new",
          "latitude": "30.70460000",
          "longitude": "76.71790000",
          "location": "mohali",
          "image": "/public/uploads/user/1647349476683-file.png"
        }
      },
      {
        "id": 9,
        "userId": 184,
        "description": "Coffee??",
        "image": "",
        "location": "Mohali",
        "status": 1,
        "latitude": "30.71695500",
        "longitude": "76.71837600",
        "createdAt": "2022-03-17T06:40:17.000Z",
        "updatedAt": "2022-03-17T06:40:17.000Z",
        "user": {
          "id": 184,
          "name": "user 2",
          "latitude": "30.71695500",
          "longitude": "76.71837600",
          "location": "Sector 59, Mohali, India",
          "image": ""
        }
      },
      {
        "id": 8,
        "userId": 181,
        "description": "heyyy",
        "image": "/public/uploads/feeds/1647425146027-file.jpeg",
        "location": "",
        "status": 1,
        "latitude": "0.00000000",
        "longitude": "0.00000000",
        "createdAt": "2022-03-16T10:05:46.000Z",
        "updatedAt": "2022-03-16T10:05:46.000Z",
        "user": {
          "id": 181,
          "name": "Gagan DeepSingh",
          "latitude": "30.70943950",
          "longitude": "76.69522840",
          "location": "",
          "image": "/public/uploads/user/1647349476683-file.png"
        }
      }
    ],
    "userList": [
      {
        "id": 185,
        "role": 1,
        "verified": 0,
        "status": 1,
        "username": "",
        "name": "dfgf fghghj",
        "email": "user1@gmail.com",
        "gender": 0,
        "location": "mohali",
        "dob": "",
        "age": 0,
        "is_online": 0,
        "latitude": "30.70460000",
        "longitude": "76.71790000",
        "is_subscribed": 0,
        "is_show_location": 0,
        "is_liked": 0,
        "distance": 0,
        "join_date": "2022-03-17",
        "total_age": null
      },
      {
        "id": 184,
        "role": 1,
        "verified": 0,
        "status": 1,
        "username": "",
        "name": "user 2",
        "email": "user2@gmail.com",
        "gender": 0,
        "location": "Sector 59, Mohali, India",
        "dob": "",
        "age": 0,
        "is_online": 0,
        "latitude": "30.71695500",
        "longitude": "76.71837600",
        "is_subscribed": 0,
        "is_show_location": 0,
        "is_liked": 0,
        "distance": 0,
        "join_date": "2022-03-17",
        "total_age": null
      },
      {
        "id": 183,
        "role": 1,
        "verified": 0,
        "status": 1,
        "username": "",
        "name": "dfgf fghghj",
        "email": "test12@gmail.com",
        "gender": 0,
        "location": "mohali",
        "dob": "2015-03-15",
        "age": 7,
        "is_online": 0,
        "latitude": "30.70460000",
        "longitude": "76.71790000",
        "is_subscribed": 0,
        "is_show_location": 0,
        "is_liked": 0,
        "distance": 0,
        "join_date": "2022-03-15",
        "total_age": "7"
      },
      {
        "id": 181,
        "role": 1,
        "verified": 0,
        "status": 1,
        "username": "",
        "name": "Gagan DeepSingh",
        "email": "gagand1993@gmail.com",
        "gender": 0,
        "location": "",
        "dob": "2015-03-15",
        "age": 7,
        "is_online": 0,
        "latitude": "30.70943950",
        "longitude": "76.69522840",
        "is_subscribed": 0,
        "is_show_location": 0,
        "is_liked": 0,
        "distance": 0,
        "join_date": "2022-03-14",
        "total_age": "7"
      },
      {
        "id": 180,
        "role": 1,
        "verified": 0,
        "status": 1,
        "username": "",
        "name": "new",
        "email": "user3@gmail.com",
        "gender": 1,
        "location": "mohali",
        "dob": "2015-03-15",
        "age": 7,
        "is_online": 0,
        "latitude": "30.70460000",
        "longitude": "76.71790000",
        "is_subscribed": 0,
        "is_show_location": 0,
        "is_liked": 0,
        "distance": 0,
        "join_date": "2022-03-14",
        "total_age": "7"
      }
    ],
    "notificationList": [
      {
        "data": {},
        "id": 10,
        "userId": 181,
        "user2Id": 182,
        "notificationType": 2,
        "message": "Swipe user successfully",
        "isRead": 0,
        "created": 1647499105,
        "updated": 1647499105,
        "createdAt": "2022-03-17T06:38:24.000Z",
        "updatedAt": "2022-03-17T06:38:24.000Z"
      },
      {
        "data": {},
        "id": 9,
        "userId": 181,
        "user2Id": 182,
        "notificationType": 2,
        "message": "Swipe user successfully",
        "isRead": 0,
        "created": 1647499105,
        "updated": 1647499105,
        "createdAt": "2022-03-17T06:38:24.000Z",
        "updatedAt": "2022-03-17T06:38:24.000Z"
      }
    ]
  }
}
 */
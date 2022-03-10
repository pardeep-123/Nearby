package com.creation.nearby.model


import android.os.Parcelable
import com.creation.nearby.adapter.AbstractModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//data class GetEventModel(
//    @SerializedName("body")
//    val body: ArrayList<Body>,
//    @SerializedName("code")
//    val code: Int, // 200
//    @SerializedName("message")
//    val message: String, // Event listing fetched successfully.
//    @SerializedName("success")
//    val success: Boolean // true
//): AbstractModel() {
//    @Parcelize
//    data class Body(
//        @SerializedName("attend")
//        val attend: String,
//        @SerializedName("createdAt")
//        val createdAt: String, // 2022-02-21T13:19:01.000Z
//        @SerializedName("details")
//        val details: String, // xxzc
//        @SerializedName("discount")
//        val discount: String, // cxc
//        @SerializedName("id")
//        val id: Int, // 19
//        @SerializedName("image")
//        val image: String, // http://202.164.42.227:9022/images/59a32c09-30bc-4041-b2a6-d14d26bdb56e.jpeg
//        @SerializedName("latitude")
//        val latitude: String, // 32.27333520
//        @SerializedName("location")
//        val location: String, // Pathankot, Punjab 145001, India
//        @SerializedName("longitude")
//        val longitude: String, // 75.65220660
//        @SerializedName("status")
//        val status: Int, // 1
//        @SerializedName("time")
//        val time: String, // 06:48 PM
//        @SerializedName("title")
//        val title: String, // heyy
//        @SerializedName("updatedAt")
//        val updatedAt: String, // 2022-02-21T13:19:01.000Z
//        @SerializedName("userId")
//        val userId: Int ,// 0
//
//    var visible : Boolean = false
//    ):AbstractModel(),Parcelable
//}

data class GetEventModel(
    @SerializedName("body")
    val body: ArrayList<Body>,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Event listing fetched successfully.
    @SerializedName("success")
    val success: Boolean // true
) :AbstractModel(){
    @Parcelize
    data class Body(
        @SerializedName("attend")
        val attend: String,
        @SerializedName("createdAt")
        val createdAt: String, // 2022-03-07T15:42:49.000Z
        @SerializedName("details")
        val details: String, // This event is created by user3
        @SerializedName("discount")
        val discount: String, // 50
        @SerializedName("id")
        val id: Int, // 70
        @SerializedName("image")
        val image: String, // http://202.164.42.227:9022/images/1d6c3b05-1417-4f5b-b5bc-4ba390daaca2.jpeg
        @SerializedName("is_accepted")
        val isAccepted: Int, // 2
        @SerializedName("latitude")
        val latitude: String, // 0.00000000
        @SerializedName("location")
        val location: String, // Dehradun, Uttarakhand, India
        @SerializedName("longitude")
        val longitude: String, // 0.00000000
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("time")
        val time: String, // 15:42
        @SerializedName("title")
        val title: String, // EventUser3
        @SerializedName("updatedAt")
        val updatedAt: String, // 2022-03-07T15:42:49.000Z
        @SerializedName("user")
        val user: User,
        @SerializedName("userId")
        val userId: Int, // 176

        var visible : Boolean = false
    ):AbstractModel(),Parcelable {
        @Parcelize
        data class User(
            @SerializedName("distance")
            val distance: Double, // 0
            @SerializedName("id")
            val id: Int, // 176
            @SerializedName("image")
            val image: String,
            @SerializedName("latitude")
            val latitude: String, // 0.000000
            @SerializedName("longitude")
            val longitude: String, // 0.000000
            @SerializedName("name")
            val name: String // new User
        ): AbstractModel(),Parcelable
    }
}

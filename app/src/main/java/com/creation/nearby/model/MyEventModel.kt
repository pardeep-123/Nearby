package com.creation.nearby.model
import android.os.Parcelable
import com.creation.nearby.adapter.AbstractModel
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class MyEventModel(
    @SerializedName("body")
    val body: ArrayList<Body>,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Event detail fetched successfully.
    @SerializedName("success")
    val success: Boolean // true
):AbstractModel() {
    @Parcelize
    data class Body(
        @SerializedName("attend")
        val attend: String,
        @SerializedName("createdAt")
        val createdAt: String, // 2022-03-07T12:32:13.000Z
        @SerializedName("details")
        val details: String, // all goods
        @SerializedName("discount")
        val discount: String, // 12
        @SerializedName("id")
        val id: Int, // 69
        @SerializedName("image")
        val image: String, // http://202.164.42.227:9022/images/8497d591-78cb-428a-8019-aecb76f22e0f.jpg
        @SerializedName("is_accepted")
        val isAccepted: Int, // 2
        @SerializedName("latitude")
        val latitude: String, // 30.70464860
        @SerializedName("location")
        val location: String, // Sahibzada Ajit Singh Nagar, Punjab, India
        @SerializedName("longitude")
        val longitude: String, // 76.71787260
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("time")
        val time: String, // 06:01 PM
        @SerializedName("title")
        val title: String, // my event
        @SerializedName("updatedAt")
        val updatedAt: String, // 2022-03-07T12:32:13.000Z
        @SerializedName("user")
        val user: User,
        @SerializedName("userId")
        val userId: Int, // 161
       var visible : Boolean = false
    ) :AbstractModel(),Parcelable {
        @Parcelize
        data class User(
            @SerializedName("distance")
            val distance: Double, // 5431.551199875289
            @SerializedName("id")
            val id: Int, // 161
            @SerializedName("image")
            val image: String,
            @SerializedName("latitude")
            val latitude: String, // 0.000000
            @SerializedName("longitude")
            val longitude: String, // 0.000000
            @SerializedName("name")
            val name: String // prdp shrma
        ):AbstractModel(),Parcelable
    }
}
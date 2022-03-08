package com.creation.nearby.model


import com.google.gson.annotations.SerializedName

data class AddEventModel(
    @SerializedName("body")
    val body: Body,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Event added successfully.
    @SerializedName("success")
    val success: Boolean // true
) {
    data class Body(
        @SerializedName("attend")
        val attend: String,
        @SerializedName("createdAt")
        val createdAt: String, // 2022-02-21T09:26:17.000Z
        @SerializedName("details")
        val details: String, // Tech event details
        @SerializedName("id")
        val id: Int, // 16
        @SerializedName("image")
        val image: String, // http://202.164.42.227:9022/images/998759fb-e674-4f7a-b2c8-93c1bc798b52.png
        @SerializedName("latitude")
        val latitude: String, // 30.92384283
        @SerializedName("location")
        val location: String, // Chandigarh
        @SerializedName("longitude")
        val longitude: String, // 76.92347234
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("time")
        val time: String, // 16:10
        @SerializedName("title")
        val title: String, // Tech Event
        @SerializedName("updatedAt")
        val updatedAt: String, // 2022-02-21T09:26:17.000Z
        @SerializedName("userId")
        val userId: Int // 0
    )
}
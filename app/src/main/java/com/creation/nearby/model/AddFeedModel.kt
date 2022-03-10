package com.creation.nearby.model
import com.google.gson.annotations.SerializedName


data class AddFeedModel(
    @SerializedName("body")
    val body: Body,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Feeds add successfully.
    @SerializedName("success")
    val success: Boolean // true
) {
    data class Body(
        @SerializedName("createdAt")
        val createdAt: String, // 2022-03-10T10:00:29.000Z
        @SerializedName("description")
        val description: String, // test1
        @SerializedName("id")
        val id: Int, // 14
        @SerializedName("image")
        val image: String, // /public/uploads/feeds/1646828000543-file.jpeg
        @SerializedName("latitude")
        val latitude: Int, // 0
        @SerializedName("location")
        val location: String,
        @SerializedName("longitude")
        val longitude: Int, // 0
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("updatedAt")
        val updatedAt: String, // 2022-03-10T10:00:29.000Z
        @SerializedName("user")
        val user: User,
        @SerializedName("userId")
        val userId: Int // 147
    ) {
        data class User(
            @SerializedName("id")
            val id: Int, // 147
            @SerializedName("image")
            val image: String, // /public/uploads/user/1646399240816-file.png
            @SerializedName("latitude")
            val latitude: String, // 30.704600
            @SerializedName("location")
            val location: String, // mohali
            @SerializedName("longitude")
            val longitude: String, // 76.717900
            @SerializedName("name")
            val name: String // test
        )
    }
}
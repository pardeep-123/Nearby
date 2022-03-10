package com.creation.nearby.model


import com.creation.nearby.adapter.AbstractModel
import com.google.gson.annotations.SerializedName

data class GetFeedModel(
    @SerializedName("body")
    val body: ArrayList<Body>,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Feed list.
    @SerializedName("success")
    val success: Boolean // true
) : AbstractModel(){
    data class Body(
        @SerializedName("createdAt")
        val createdAt: String, // 2021-11-26T11:31:47.000Z
        @SerializedName("description")
        val description: String, // seems to be adorable
        @SerializedName("id")
        val id: Int, // 4
        @SerializedName("image")
        val image: String,
        @SerializedName("latitude")
        val latitude: Int, // 0
        @SerializedName("location")
        val location: String, // Chandigarh
        @SerializedName("longitude")
        val longitude: Int, // 0
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("updatedAt")
        val updatedAt: String, // 2021-11-26T11:49:02.000Z
        @SerializedName("user")
        val user: User,
        @SerializedName("userId")
        val userId: Int // 123
    ) : AbstractModel(){
        data class User(
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
        ): AbstractModel()
    }
}
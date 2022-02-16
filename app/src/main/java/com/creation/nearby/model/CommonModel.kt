package com.creation.nearby.model


import com.google.gson.annotations.SerializedName

data class CommonModel(
    @SerializedName("body")
    val body: Body,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // User Logged Out Successfully !
    @SerializedName("success")
    val success: Boolean // true
) {
    class Body(
    )
}
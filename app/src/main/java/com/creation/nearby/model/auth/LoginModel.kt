package com.creation.nearby.model.auth

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  LoginModel(
    @SerializedName("body")
    val body: Body,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Signup successfully.
    @SerializedName("success")
    val success: Boolean // true
): Parcelable {
    @Parcelize
    data class Body(
        @SerializedName("age")
        val age: Int, // 0
        @SerializedName("biography")
        val biography: String,
        @SerializedName("countryCode")
        val countryCode: String,
        @SerializedName("countryId")
        val countryId: Int, // 91
        @SerializedName("counts")
        val counts: Int, // 0
        @SerializedName("created")
        val created: Int, // 1644919020
        @SerializedName("createdAt")
        val createdAt: String, // 2022-02-15T09:56:59.000Z
        @SerializedName("dob")
        val dob: String,
        @SerializedName("email")
        val email: String, // jack@gmail.com
        @SerializedName("end_date")
        val endDate: String,
        @SerializedName("facebookId")
        val facebookId: String,
        @SerializedName("forgotPasswordHash")
        val forgotPasswordHash: String,
        @SerializedName("gender")
        val gender: Int, // 0
        @SerializedName("googleId")
        val googleId: String,
        @SerializedName("id")
        val id: Int, // 149
        @SerializedName("image")
        val image: String,
        @SerializedName("interests")
        val interests: String,
        @SerializedName("intrested_in")
        val intrestedIn: String,
        @SerializedName("isAt")
        val isAt: Int, // 0
        @SerializedName("is_subscribed")
        val isSubscribed: Int, // 0
        @SerializedName("isVerified")
        val isVerified: Int, // 0
        @SerializedName("latitude")
        val latitude: String, // 0.000000
        @SerializedName("location")
        val location: String,
        @SerializedName("login_time")
        val loginTime: String, // 1644919019
        @SerializedName("longitude")
        val longitude: String, // 0.000000
        @SerializedName("name")
        val name: String, // jack smith
        @SerializedName("firstname")
        val firstname: String,
        @SerializedName("lastname")
        val lastname: String,
        @SerializedName("otp")
        val otp: Int, // 1111
        @SerializedName("password")
        val password: String, // $2y$10$dLLHaDfdbh0l7qwSjZn3kuOBoAFIZKXOj1ZMZfEMu17OGHk2QBVRC
        @SerializedName("phone")
        val phone: String,
        @SerializedName("role")
        val role: Int, // 1
        @SerializedName("size")
        val size: String, // 0
        @SerializedName("social_id")
        val socialId: String,
        @SerializedName("socialType")
        val socialType: Int, // 0
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("subscriptionId")
        val subscriptionId: Int, // 0
        @SerializedName("subscription_type")
        val subscriptionType: String,
        @SerializedName("token")
        val token: String, // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImlkIjoxNDksImxvZ2luX3RpbWUiOjE2NDQ5MTkwMTl9LCJpYXQiOjE2NDQ5MTkwMTl9.A_AYrRQkUkikLpLWDQFOv-YlqbNwxrsA4BdcRS-S2no
        @SerializedName("total_bids")
        val totalBids: Int, // 0
        @SerializedName("updated")
        val updated: Int, // 1644919020
        @SerializedName("updatedAt")
        val updatedAt: String, // 2022-02-15T09:56:59.000Z
        @SerializedName("userDetail")
        val userDetail: UserDetail,
        @SerializedName("username")
        val username: String,
        @SerializedName("first_login")
        val firstLogin: String,
        @SerializedName("verified")
        val verified: Int, // 0
        @SerializedName("zodiac")
        val zodiac: String
    ): Parcelable {
        @Parcelize
        data class UserDetail(
            @SerializedName("created")
            val created: Int, // 1644919020
            @SerializedName("createdAt")
            val createdAt: String?, // 2022-02-15T09:56:59.000Z
            @SerializedName("deviceToken")
            val deviceToken: String?, // 23
            @SerializedName("deviceType")
            val deviceType: Int, // 23
            @SerializedName("id")
            val id: Int, // 20
            @SerializedName("image")
            val image: String?,
            @SerializedName("isNotification")
            val isNotification: Int, // 0
            @SerializedName("name")
            val name: String?,
            @SerializedName("updated")
            val updated: Int, // 1644919020
            @SerializedName("updatedAt")
            val updatedAt: String?, // 2022-02-15T09:56:59.000Z
            @SerializedName("userId")
            val userId: Int // 149
        ) : Parcelable
    }
}
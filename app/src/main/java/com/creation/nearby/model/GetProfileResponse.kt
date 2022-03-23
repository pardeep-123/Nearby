package com.creation.nearby.model
import com.google.gson.annotations.SerializedName


data class GetProfileResponse(
    @SerializedName("body")
    val body: Body?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
){
    data class Body(
        @SerializedName("age")
        val age: Int?,
        @SerializedName("biography")
        val biography: String?,
        @SerializedName("countryCode")
        val countryCode: String?,
        @SerializedName("countryId")
        val countryId: Int?,
        @SerializedName("counts")
        val counts: Int?,
        @SerializedName("created")
        val created: Int?,
        @SerializedName("createdAt")
        val createdAt: String?,
        @SerializedName("dob")
        val dob: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("end_date")
        val endDate: String?,
        @SerializedName("facebookId")
        val facebookId: String?,
        @SerializedName("first_login")
        val firstLogin: Int?,
        @SerializedName("firstname")
        val firstname: String?,
        @SerializedName("forgotPasswordHash")
        val forgotPasswordHash: String?,
        @SerializedName("gender")
        val gender: Int?,
        @SerializedName("googleId")
        val googleId: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("interests")
        val interests: String?,
        @SerializedName("intrested_in")
        val intrestedIn: String?,
        @SerializedName("isAt")
        val isAt: Int?,
        @SerializedName("is_online")
        val isOnline: Int?,
        @SerializedName("is_show_location")
        val isShowLocation: Int?,
        @SerializedName("is_subscribed")
        val isSubscribed: Int?,
        @SerializedName("isVerified")
        val isVerified: Int?,
        @SerializedName("lastname")
        val lastname: String?,
        @SerializedName("latitude")
        val latitude: String?,
        @SerializedName("location")
        val location: String?,
        @SerializedName("login_time")
        val loginTime: String?,
        @SerializedName("longitude")
        val longitude: String?,
        @SerializedName("manual_interest")
        val manualInterest: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("otp")
        val otp: Int?,
        @SerializedName("password")
        val password: String?,
        @SerializedName("phone")
        val phone: String?,
        @SerializedName("role")
        val role: Int?,
        @SerializedName("size")
        val size: String?,
        @SerializedName("social_id")
        val socialId: String?,
        @SerializedName("socialType")
        val socialType: Int?,
        @SerializedName("status")
        val status: Int?,
        @SerializedName("subscriptionId")
        val subscriptionId: Int?,
        @SerializedName("subscription_type")
        val subscriptionType: String?,
        @SerializedName("total_bids")
        val totalBids: Int?,
        @SerializedName("updated")
        val updated: Int?,
        @SerializedName("updatedAt")
        val updatedAt: String?,
        @SerializedName("userDetail")
        val userDetail: UserDetail?,
        @SerializedName("user_images")
        val userImages: List<UserImage>?,
        @SerializedName("username")
        val username: String?,
        @SerializedName("verified")
        val verified: Int?,
        @SerializedName("zodiac")
        val zodiac: String?
    ){
        data class UserDetail(
            @SerializedName("deviceToken")
            val deviceToken: String?,
            @SerializedName("deviceType")
            val deviceType: Int?,
            @SerializedName("isNotification")
            val isNotification: Int?
        )
        data class UserImage(
            @SerializedName("created")
            val created: Int?,
            @SerializedName("created_at")
            val createdAt: String?,
            @SerializedName("id")
            val id: Int?,
            @SerializedName("image")
            val image: String?,
            @SerializedName("thumbnail")
            val thumbnail: String?,
            @SerializedName("updated")
            val updated: Int?,
            @SerializedName("updated_at")
            val updatedAt: String?,
            @SerializedName("user_id")
            val userId: Int?
        )
    }
}
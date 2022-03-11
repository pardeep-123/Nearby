package com.creation.nearby.model
import com.creation.nearby.adapter.AbstractModel
import com.google.gson.annotations.SerializedName


data class UserListModel(
    @SerializedName("body")
    val body: ArrayList<Body>,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // User list.
    @SerializedName("success")
    val success: Boolean // true
): AbstractModel() {
    data class Body(
        @SerializedName("age")
        val age: Int, // 30
        @SerializedName("biography")
        val biography: String, // to be continued..
        @SerializedName("countryCode")
        val countryCode: String,
        @SerializedName("countryId")
        val countryId: Int, // 1
        @SerializedName("counts")
        val counts: Int, // 60
        @SerializedName("created")
        val created: Int, // 1615277180
        @SerializedName("createdAt")
        val createdAt: String, // 2020-06-03T20:42:30.000Z
        @SerializedName("dob")
        val dob: String, // 13-02-1997
        @SerializedName("email")
        val email: String, // ravneet@gmail.com
        @SerializedName("end_date")
        val endDate: String, // 2022-06-20
        @SerializedName("facebookId")
        val facebookId: String,
        @SerializedName("forgotPasswordHash")
        val forgotPasswordHash: String,
        @SerializedName("gender")
        val gender: Int, // 1
        @SerializedName("googleId")
        val googleId: String,
        @SerializedName("id")
        val id: Int, // 1
        @SerializedName("image")
        val image: String, // funsukh2.jpg
        @SerializedName("interests")
        val interests: String, // travel
        @SerializedName("intrested_in")
        val intrestedIn: String, // woman
        @SerializedName("isAt")
        val isAt: Int, // 0
        @SerializedName("is_online")
        val isOnline: Int, // 1
        @SerializedName("is_show_location")
        val isShowLocation: Int, // 0
        @SerializedName("is_subscribed")
        val isSubscribed: Int, // 1
        @SerializedName("isVerified")
        val isVerified: Int, // 0
        @SerializedName("latitude")
        val latitude: String, // 0.00000000
        @SerializedName("location")
        val location: String,
        @SerializedName("login_time")
        val loginTime: String, // 1645602904
        @SerializedName("longitude")
        val longitude: String, // 0.00000000
        @SerializedName("manual_interest")
        val manualInterest: String,
        @SerializedName("name")
        val name: String, // ravneet
        @SerializedName("otp")
        val otp: Int, // 0
        @SerializedName("password")
        val password: String, // $2y$10$46sKqMu7ORnUvLrdQo5KCO16jIiOtw24hIXsNf2/nmh.J/BxPKyHu
        @SerializedName("phone")
        val phone: String, // 8888569612
        @SerializedName("role")
        val role: Int, // 1
        @SerializedName("size")
        val size: String,
        @SerializedName("social_id")
        val socialId: String,
        @SerializedName("socialType")
        val socialType: Int, // 0
        @SerializedName("status")
        val status: Int, // 1
        @SerializedName("subscriptionId")
        val subscriptionId: Int, // 5
        @SerializedName("subscription_type")
        val subscriptionType: String,
        @SerializedName("total_bids")
        val totalBids: Int, // 0
        @SerializedName("updated")
        val updated: Int, // 1615277180
        @SerializedName("updatedAt")
        val updatedAt: String, // 2022-02-23T07:56:08.000Z
        @SerializedName("username")
        val username: String, // Ravneet
        @SerializedName("verified")
        val verified: Int, // 1
        @SerializedName("zodiac")
        val zodiac: String // libra
    ):AbstractModel()
}
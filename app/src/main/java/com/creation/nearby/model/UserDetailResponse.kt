package com.creation.nearby.model

data class UserDetailResponse(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
) {
    data class Body(
        val age: Int,
        val biography: String,
        val countryCode: String,
        val countryId: Int,
        val counts: Int,
        val created: Int,
        val createdAt: String,
        val dob: String,
        val email: String,
        val end_date: String,
        val facebookId: String,
        val firstname: String,
        val forgotPasswordHash: String,
        val gender: Int,
        val googleId: String,
        val id: Int,
        val image: String,
        val interests: String,
        val intrested_in: String,
        val isAt: Int,
        val isVerified: Int,
        val is_online: Int,
        val is_show_location: Int,
        val is_subscribed: Int,
        val latitude: String,
        val location: String,
        val login_time: String,
        val longitude: String,
        val manual_interest: String,
        val name: String,
        val otp: Int,
        val password: String,
        val phone: String,
        val role: Int,
        val size: String,
        val socialType: Int,
        val social_id: String,
        val status: Int,
        val subscriptionId: Int,
        val subscription_type: String,
        val total_bids: Int,
        val updated: Int,
        val updatedAt: String,
        val userDetail: UserDetail,
        val user_images: List<UserImage>,
        val username: String,
        val verified: Int,
        val zodiac: String
    ) {
        data class UserDetail(
            val deviceToken: String,
            val deviceType: Int,
            val isNotification: Int
        )

        data class UserImage(
            val created: Int,
            val created_at: String,
            val id: Int,
            val image: String,
            val thumbnail: String,
            val updated: Int,
            val updated_at: String,
            val user_id: Int
        )
    }
}
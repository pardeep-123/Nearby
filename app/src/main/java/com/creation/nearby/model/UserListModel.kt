package com.creation.nearby.model

import com.creation.nearby.adapter.AbstractModel

data class UserListModel(
    val body: Body,
    val code: Int,
    val message: String,
    val success: Boolean
): AbstractModel() {
    data class Body(
        val count: Int,
        val user_list: List<User>
    ):AbstractModel() {
        data class User(
            val age: Int,
            val distance: Double,
            val image: String,
            val dob: String,
            val email: String,
            val gender: Int,
            val id: Int,
            val is_liked: Int,
            val is_online: Int,
            val is_show_location: Int,
            val is_subscribed: Int,
            val join_date: String,
            val latitude: String,
            val location: String,
            val longitude: String,
            val name: String,
            val role: Int,
            val status: Int,
            val total_age: String,
            val userDetail: UserDetail,
            val username: String,
            val verified: Int
        ):AbstractModel() {
            data class UserDetail(
                val deviceToken: String,
                val deviceType: Int,
                val isNotification: Int
            ):AbstractModel()
        }
    }
}
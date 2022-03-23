package com.creation.nearby.model
import com.google.gson.annotations.SerializedName


data class InterestListResponse(
    @SerializedName("body")
    val body: List<Body>?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("success")
    val success: Boolean?
){
    data class Body(
        @SerializedName("created")
        val created: Int?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("status")
        val status: Int?,
        @SerializedName("updated")
        val updated: Int?,
        @SerializedName("updated_at")
        val updatedAt: String?
    )
}
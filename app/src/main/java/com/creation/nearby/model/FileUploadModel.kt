package com.creation.nearby.model


import com.google.gson.annotations.SerializedName

data class FileUploadModel(
    @SerializedName("body")
    val body: ArrayList<Body>,
    @SerializedName("code")
    val code: Int, // 200
    @SerializedName("message")
    val message: String, // Successufully
    @SerializedName("status")
    val status: Boolean // true
) {
    data class Body(
        @SerializedName("fileName")
        val fileName: String, // Counseling.jpeg
        @SerializedName("file_type")
        val fileType: String, // image
        @SerializedName("folder")
        val folder: String, // feeds
        @SerializedName("image")
        val image: String, // /public/uploads/feeds/1646891269318-file.jpeg
        @SerializedName("thumbnail")
        val thumbnail: String // /public/uploads/feeds/1646891269318-thumbnail-file.jpeg
    )
}
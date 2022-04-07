package com.creation.nearby.interfaces


interface FilterInterface {
    fun sendData(
        location: String,
        latitude: String,
        longitude: String,
        distance: String,
        gender: String,
        filterBy: String,
        minAge: String,
        maxAge: String
    )
}
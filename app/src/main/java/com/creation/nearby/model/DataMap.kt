package com.creation.nearby.model

import com.google.android.gms.maps.model.LatLng

data class DataMap(
    val latlng : LatLng,
    val id : Int ,
    val firstName : String,
    val lastName : String
//    val image : String,
//    val distance : Int,
//    "name": "gaurav sharma",
//"latitude": "0.000000",
//"longitude": "0.000000",
//"image": "",
//"distance": 0
)

package com.creation.nearby.model

data class EditProfileRequest(
    var firstname:String?=null,
    var lastname:String?=null,
    var countryCode:String?=null,
    var phone:String?=null,
    var dob:String?=null,
    var location:String?=null,
    var latitude:String?=null,
    var longitude:String?=null,
    var gender:String?=null,
    var biography:String?=null,
    var gallary_images:String?=null,
    var zodiac:String?=null,
    var intrested_in:String?=null,
    var interests:String?=null,
    var manual_interest:String?=null,
    var image:String?=null,
    var email:String?=""
)
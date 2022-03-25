package com.creation.nearby.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Constants {

    val BASEURL = "http://202.164.42.227:9022/api/"
    val IMAGE_BASE_URL = "http://202.164.42.227:9022"
  //  val BASEURL = "http://192.168.1.96:9022/api/"

    const val SIGNUP = "signup"

    const val LOGIN = "login"
    const val socialLogin = "social_login"
    const val logout = "logout"

    const val PROFILE = "profile"
    const val changePassword = "change_password"
    const val forgotPassword = "forgot_password"
    const val getContent = "get_content"
    const val editProfile = "edit_profile"
    const val addEvent = "addEvent"
    const val addFeed = "add_feed"
    const val getFeed = "feeds"
    const val eventListing = "eventListing"
    const val myEventListing = "my_event"
    const val homeListing = "home"
    const val fileUpload = "file_upload"
    const val swipeList = "user_listing"
    const val user_detail = "user_detail"
    const val swipeUser = "swipe_user"
    const val acceptRejectEvent = "accept_reject_event"
    const val acceptRejectFriend = "accept_reject_friend"
    const val checkFirstTimeLoginStatus = "check_first_time_login_status"
    const val profile = "profile"
    const val interests = "interests"
    const val notification_listing = "notification_listing"
    const val contactUs = "contact_us"
    const val feedback = "feedback"


    //preferences
    const val DEVICE_ID = "deviceID"
    const val FIREBASE_FCM_TOKEN = "firebase_fcm_token"
    const val preferenceName = "Nearby"
    const val foreverPreferenceName = "Nearby"
    const val loginData = "loginData"
    const val AUTH_KEY = "AUTH_KEY"
    const val USER_IMAGE = "USER_IMAGE"
    const val IS_FIRST_LOGIN = "IS_FIRST_LOGIN"
    const val notifiactionBroadcase = "notificatiionCallChange"




    //convert a data class to a map
    fun <T> T.serializeToMap(): Map<String, String> {
        return convert()
    }

    //convert an object of type I to type O
    inline fun <I, reified O> I.convert(): O {
        val json = Gson().toJson(this)
        return Gson().fromJson(json, object : TypeToken<O>() {}.type)
    }
}
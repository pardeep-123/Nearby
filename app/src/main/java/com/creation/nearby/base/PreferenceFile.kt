package com.creation.nearby.base

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.creation.nearby.model.auth.LoginModel
import com.creation.nearby.utils.Constants
import com.google.gson.Gson

object PreferenceFile {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var foreverSharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    fun storeKey(context: Context, key: String, value: String) {
        sharedPreferences =
            context.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
        editor.commit()
    }

    fun retrieveKey(context: Context, key: String): String? {
        sharedPreferences =
            context.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }


    fun setConnected(value: Boolean) {
        sharedPreferences = AppController.mInstance.getSharedPreferences(
            Constants.preferenceName,
            Context.MODE_PRIVATE
        )
        editor = sharedPreferences.edit()
        editor.putBoolean("connect", value)
        editor.apply()
        editor.commit()
    }

    fun getConnected(): Boolean? {
        sharedPreferences = AppController.mInstance.getSharedPreferences(
            Constants.preferenceName,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean("connect", false)
    }



    fun storeUserRole(context: Context, status: String) {
        sharedPreferences =
            context.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString("role", status)
        editor.apply()
    }

    fun retrieveUserRole(context: Context): String {
        sharedPreferences =
            context.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE)
        // 1 for user
        return sharedPreferences.getString("role", "1")!!
    }

    fun storeUserId(context: Context, user_id: String) {
        sharedPreferences =
            context.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString("user_id", user_id)
        editor.apply()
    }

    fun retrieveUserId(): String {
        sharedPreferences = AppController.mInstance.getSharedPreferences(
            Constants.preferenceName,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString("user_id", "")!!
    }

    fun storeLocationData(context: Context, lat: Double, lng: Double, location: String) {
        sharedPreferences =
            context.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        /* var hashMap = HashMap<String, Any>()

         hashMap["lat"] = lat
         hashMap["lng"] = lng
         hashMap["location"] = location*/


        editor.putString("lat", lat.toString())
        editor.putString("lng", lng.toString())
        editor.putString("location", location)
        editor.apply()
    }

    fun retrieveLocationData(): HashMap<String, Any> {
        sharedPreferences = AppController.mInstance.getSharedPreferences(
            Constants.preferenceName,
            Context.MODE_PRIVATE
        )
        var hashMap = HashMap<String, Any>()

        hashMap["lat"] = sharedPreferences.getString("lat", "")!!
        hashMap["lng"] = sharedPreferences.getString("lng", "")!!
        hashMap["location"] = sharedPreferences.getString("location", "")!!

        return hashMap
    }

    fun storeNotificationStatus(context: Context, status: String) {
        sharedPreferences =
            context.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString("notify", status)
        editor.apply()
    }

    fun retrieveNotificationStatus(context: Context): String {
        sharedPreferences =
            context.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getString("notify", "0").toString()
    }

    fun storeLoginData(context: Context, loginResponse: LoginModel) {
        sharedPreferences =
            context.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE)
        val prefsEditor: SharedPreferences.Editor = sharedPreferences.edit()
        Log.e("storeLoginData", "===${Gson().toJson(loginResponse)}")
        prefsEditor.putString(Constants.loginData, Gson().toJson(loginResponse))
        prefsEditor.apply()
        prefsEditor.commit()
    }


      fun retrieveLoginData(context: Context): LoginModel? {
          sharedPreferences =
                  context.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE)
          Log.e("retrieveLoginData", "===${Gson().fromJson(
                  sharedPreferences.getString(Constants.loginData, "")!!,
                  LoginModel::class.java
          )}")

          return Gson().fromJson(
                  sharedPreferences.getString(Constants.loginData, "")!!,
                  LoginModel::class.java
          )
      }


    fun clearPreference(context: Context) {
        sharedPreferences =
            context.getSharedPreferences(Constants.preferenceName, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun storeForeverKey(context: Context, key: String, value: String) {
        foreverSharedPreferences =
            context.getSharedPreferences(Constants.foreverPreferenceName, Context.MODE_PRIVATE)
        editor = foreverSharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun retrieveForeverKey(context: Context, key: String): String? {
        foreverSharedPreferences =
            context.getSharedPreferences(Constants.foreverPreferenceName, Context.MODE_PRIVATE)
        return foreverSharedPreferences.getString(key, null)
    }

    /*To Store Fcm Device ID*/
    fun storeFcmDeviceId(context: Context, `val`: String) {
        sharedPreferences = context.getSharedPreferences("FCM", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString("DEVICE", `val`)
        editor.apply()
    }

    fun retrieveFcmDeviceId(context: Context): String {
        sharedPreferences = context.getSharedPreferences("FCM", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        return sharedPreferences.getString("DEVICE", null).toString()
    }
}

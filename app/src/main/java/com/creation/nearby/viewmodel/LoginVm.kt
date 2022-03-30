package com.creation.nearby.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.creation.nearby.animateFade
import com.creation.nearby.animateSlide
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.auth.LoginModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.MainActivity
import com.creation.nearby.ui.authentication.ForgotPasswordActivity
import com.creation.nearby.ui.authentication.OtpActivity
import com.creation.nearby.ui.authentication.SignUpActivity
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.Constants.IS_FIRST_LOGIN
import com.creation.nearby.utils.Constants.USER_IMAGE
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.utils.Validator
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import retrofit2.Response

class LoginVm : ViewModel() {

    var email: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")

    // create function for onclicks
    fun onClick(v: View, s: String) {
        when (s) {
            "login" -> {
                validatePhoneLogin(v.context)
            }
            "forget" -> {
                v.context.startActivity(Intent(v.context, ForgotPasswordActivity::class.java))
                animateFade(v.context as Activity)
            }
            "onback" -> {
                (v.context as Activity).finish()
            }
            "signup" -> {
                v.context.startActivity(Intent(v.context, SignUpActivity::class.java))
                animateFade(v.context as Activity)
            }

        }
    }

    // function for validation check
    private fun validateLogin(context: Context) {
        if (Validator().validateLogin(
                email.get()!!,
                password.get()!!)) {

            loginApi(context)

        } else {
            ToastUtils.showToast((context as Activity), Validator.ErrorMessage)
        }
    }
    // function for validation check
    private fun validatePhoneLogin(context: Context) {
        if (Validator().validateLogin(
                email.get()!!)) {

            loginApi(context)

        } else {
            ToastUtils.showToast((context as Activity), Validator.ErrorMessage)
        }
    }

    private fun loginApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<LoginModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<LoginModel> {

                        return retrofitApi.loginApiNew(
                            mapValues(context)
                        )
                    }

                    override fun onResponse(res: Response<LoginModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                            val intent = Intent(context,OtpActivity::class.java)
                             intent.putExtra("data",response)
                            context.startActivity(intent)
//                            context.startActivity(Intent(context, OtpActivity::class.java).putExtra("data"),response.body)
                            animateSlide(context as Activity)

                            context.finishAffinity()
                        }
                    }

                    override fun onException(message: String?) {
                        Log.e("userException", "====$message")
                    }
                })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // api for social login
     fun socialLoginApi(context: Context,
                               name:String,email:String,socialId:String,socialType:String) {
        try {

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<LoginModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<LoginModel> {

                        return retrofitApi.socialLoginApi(
                            socialMapValues(context,name,email,socialId,socialType)
                        )
                    }

                    override fun onResponse(res: Response<LoginModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                            context.startActivity(
                                Intent(context, MainActivity::class.java))
                            animateSlide(context as Activity)
                            PreferenceFile.storeLoginData(context, response)
                            PreferenceFile.storeKey(context,"username",response.body.name)

                            PreferenceFile.storeKey(context,Constants.AUTH_KEY,"Bearer "+response.body.token)
                            PreferenceFile.storeUserId(context,response.body.id.toString())

                            context.finishAffinity()
                        }
                    }

                    override fun onException(message: String?) {
                        Log.e("userException", "====$message")
                    }
                })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    // function for hashmap
    fun mapValues(context: Context): HashMap<String, String> {

        val hashMap = HashMap<String, String>()

        hashMap["phone"] = email.get()!!
        hashMap["countryCode"] = password.get()!!
        hashMap["deviceToken"] = PreferenceFile.retrieveKey(context,Constants.FIREBASE_FCM_TOKEN).toString()
        // hashMap["deviceToken"] = PreferenceFile.retrieveFcmDeviceId(context)
        hashMap["deviceType"] = "1"

        return hashMap
    }

    // FUNCTION for social login hasmap
    fun socialMapValues(context: Context,name:String,email:String,socialId:String,socialType:String): HashMap<String, String> {

        val hashMap = HashMap<String, String>()

        hashMap["email"] = email
        hashMap["name"] = name
        hashMap["social_id"] = socialId
        hashMap["socialType"] = socialType
        hashMap["deviceToken"] = PreferenceFile.retrieveKey(context,Constants.FIREBASE_FCM_TOKEN).toString()
        // hashMap["deviceToken"] = PreferenceFile.retrieveFcmDeviceId(context)
        hashMap["deviceType"] = "1"

        return hashMap
    }

}
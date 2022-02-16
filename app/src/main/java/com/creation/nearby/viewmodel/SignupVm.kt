package com.creation.nearby.viewmodel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.auth.LoginModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.authentication.LoginActivity
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.utils.Validator
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class SignupVm : ViewModel() {

    // decalre variables
    var firstname: ObservableField<String> = ObservableField("")
    var lastname: ObservableField<String> = ObservableField("")
    var email: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    var confirmPassword: ObservableField<String> = ObservableField("")

    // create function for onclicks
    fun onClick(v: View, s:String){
        if (s == "signup"){

            validateSignup(v.context)
        }
    }

    // function for validation check
    fun validateSignup(context: Context){
        if (Validator().validateRegister(
                firstname.get()!!,
                lastname.get()!!,
                email.get()!!,
                password.get()!!,
                confirmPassword.get()!!
            )
        ) {


            signUpApi(context)

        } else {
            ToastUtils.showToast((context as Activity),Validator.ErrorMessage)
        }
    }
    private fun signUpApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<LoginModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<LoginModel> {

                        return retrofitApi.signup(
                            mapValues()
                        )

                    }

                    override fun onResponse(res: Response<LoginModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")

                            ToastUtils.showToast((context as Activity),response.message)
                            context.finish()
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
    fun mapValues() : HashMap<String,String>{

     val hashMap = HashMap<String,String>()

        hashMap["email"] = email.get()!!
        hashMap["firstname"] = firstname.get()!!
        hashMap["lastname"] = lastname.get()!!
        hashMap["password"] = password.get()!!
        hashMap["deviceToken"] = "23"
       // hashMap["deviceToken"] = PreferenceFile.retrieveFcmDeviceId(context)
        hashMap["deviceType"] = "23"

        return hashMap
    }
}
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
            ToastUtils.showToast(Validator.ErrorMessage)
        }
    }
    private fun signUpApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,
                "",
                object : RequestProcessor<Response<LoginModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<LoginModel> {


                        return retrofitApi.signup(mapValues(context))

                    }

                    override fun onResponse(res: Response<LoginModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                         ToastUtils.showToast(response.message)
                            (context as Activity).finishAffinity()
                            context.startActivity(
                                Intent(
                                    context,
                                    LoginActivity::class.java
                                )
                            )
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
    fun mapValues(context: Context) : HashMap<String,RequestBody>{

     val hashMap = HashMap<String,RequestBody>()

        hashMap["email"] = email.get()!!.toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["firstname"] = firstname.get()!!.toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["lastname"] = lastname.get()!!.toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["password"] = password.get()!!.toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["deviceToken"] = "12345".toRequestBody("text/plain".toMediaTypeOrNull())
       // hashMap["deviceToken"] = PreferenceFile.retrieveFcmDeviceId(context)
        hashMap["deviceType"] = "0".toRequestBody("text/plain".toMediaTypeOrNull())

        return hashMap
    }
}
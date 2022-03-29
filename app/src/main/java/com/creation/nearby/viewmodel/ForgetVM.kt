package com.creation.nearby.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.creation.nearby.confirmationDialog
import com.creation.nearby.model.CommonModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.utils.Validator
import retrofit2.Response

class ForgetVM : ViewModel() {

    val email : ObservableField<String> = ObservableField("")

    fun onClick(v: View,s:String){
        if (s=="forgetPassword"){
            validateForget(v.context)
        }
    }

    // function for validation check
    private fun validateForget(context: Context) {
        if (Validator().validateEmail(
                email.get()!!)) {
            forgetApi(context)

        } else {
            ToastUtils.showToast((context as Activity), Validator.ErrorMessage)
        }
    }

    private fun forgetApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<CommonModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<CommonModel> {

                        return retrofitApi.forgetPasswordApi(
                            mapValues())
                    }

                    override fun onResponse(res: Response<CommonModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                            confirmationDialog(context)
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
    fun mapValues(): HashMap<String, String> {

        val hashMap = HashMap<String, String>()

        hashMap["email"] = email.get()!!

        return hashMap
    }
}
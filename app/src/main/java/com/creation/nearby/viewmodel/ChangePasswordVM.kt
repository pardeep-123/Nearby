package com.creation.nearby.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.creation.nearby.model.CommonModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.utils.Validator
import retrofit2.Response

class ChangePasswordVM : ViewModel() {

    val oldPassword: ObservableField<String> = ObservableField("")
    val newPassword: ObservableField<String> = ObservableField("")
    val confirmPassword: ObservableField<String> = ObservableField("")


    // on click
    fun onClick(v: View, s: String) {
        if (s == "submit") {
            validateChangePassword(v.context)
        }
    }

    // function for validation check
    private fun validateChangePassword(context: Context) {
        if (Validator().validateChangePassword(
                oldPassword.get(),
                newPassword.get(),confirmPassword.get()
            )
        ) {

            changePasswordApi(context)

        } else {
            ToastUtils.showToast((context as Activity), Validator.ErrorMessage)
        }
    }

    // function for logout api
    private fun changePasswordApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,
                object : RequestProcessor<Response<CommonModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<CommonModel> {

                        return retrofitApi.changePasswordApi(mapValues())
                    }

                    override fun onResponse(res: Response<CommonModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                            ToastUtils.showToast(context,res.body()?.message.toString())
                            (context as Activity).finish()
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
        hashMap["old_password"] = oldPassword.get()!!
        hashMap["new_password"] = newPassword.get()!!
        hashMap["confirm_password"] = confirmPassword.get()!!

        return hashMap
    }
}
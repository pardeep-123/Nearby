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
import com.creation.nearby.ui.MainActivity
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.utils.Validator
import retrofit2.Response

class ContactUsVM : ViewModel() {

    val message: ObservableField<String> = ObservableField("")


    fun onClick(v: View){
        validateContactUs(v.context)
    }
    // function for validation check
    private fun validateContactUs(context: Context) {
        if (Validator().validateContact(
                message.get()!!
            )
        ) {

            contactUsApi(context)

        } else {
            ToastUtils.showToast((context as Activity), Validator.ErrorMessage)
        }
    }

    // api for Contact Us
    private fun contactUsApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,
                object : RequestProcessor<Response<CommonModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<CommonModel> {

                        return retrofitApi.contactUs(message.get()!!)
                    }

                    override fun onResponse(res: Response<CommonModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                            message.set("")
                            ToastUtils.showToast(context, res.body()?.message.toString())

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
}
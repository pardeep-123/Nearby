package com.creation.nearby.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.creation.nearby.confirmationDialog
import com.creation.nearby.model.AddEventModel
import com.creation.nearby.model.CommonModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.utils.Validator
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class AddEventVm : ViewModel() {

    val time : ObservableField<String> = ObservableField("")
    val title : ObservableField<String> = ObservableField("")
    val details : ObservableField<String> = ObservableField("")
    val discount : ObservableField<String> = ObservableField("")
    val location : ObservableField<String> = ObservableField("")
    val latitude : ObservableField<String> = ObservableField("")
    val longitude : ObservableField<String> = ObservableField("")
    val image : ObservableField<String> = ObservableField("")
    var imagefile : MultipartBody.Part?=null

    fun onClick(v: View, s:String){
        if (s=="addEvent"){
            validateAddEvent(v.context)
        }
    }

    // function for validation check
    private fun validateAddEvent(context: Context) {
        if (Validator().validateAddEvent(
                title.get()!!,
                details.get()!!,
                time.get()!!,
                location.get()!!,
                image.get()!!
            )) {
            addEventApi(context)

        } else {
            ToastUtils.showToast((context as Activity), Validator.ErrorMessage)
        }
    }

    private fun addEventApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<AddEventModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<AddEventModel> {
                        val requestFile =
                            File(image.get()!!).asRequestBody("image/*".toMediaTypeOrNull())
                        imagefile = MultipartBody.Part.createFormData("image",File(image.get()!!).name, requestFile)
                        return retrofitApi.addEventApi(
                            mapValues(),imagefile!!)
                    }

                    override fun onResponse(res: Response<AddEventModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                            ToastUtils.showToast(context,response.message)
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
    fun mapValues(): HashMap<String, RequestBody> {

        val hashMap = HashMap<String, RequestBody>()

        hashMap["time"] = time.get()!!.toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["title"] = title.get()!!.toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["details"] = details.get()!!.toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["discount"] = discount.get()!!.toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["location"] = location.get()!!.toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["latitude"] = latitude.get()!!.toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["longitude"] = longitude.get()!!.toRequestBody("text/plain".toMediaTypeOrNull())

        return hashMap
    }

}
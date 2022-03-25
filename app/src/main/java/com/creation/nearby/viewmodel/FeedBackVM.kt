package com.creation.nearby.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.creation.nearby.model.CommonModel
import com.creation.nearby.model.FileUploadModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.FeedBackActivity
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.utils.Validator
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class FeedBackVM : ViewModel() {

    val image: ObservableField<String> = ObservableField("")
    var imagefile: MultipartBody.Part? = null
    var imagePath = ""
    var message : ObservableField<String> = ObservableField("")

    fun onClick(v: View){
        validateFeedback(v.context)
    }

    // function for validation check
    private fun validateFeedback(context: Context) {
        if (Validator().validateFeedBack(
                message.get()!!,image.get()!!
            )
        ) {

            uploadFileApi(context)

        } else {
            ToastUtils.showToast((context as Activity), Validator.ErrorMessage)
        }
    }


    // hit upload image api
    private fun uploadFileApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<FileUploadModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<FileUploadModel> {
                        val requestFile =
                            File(image.get()!!).asRequestBody("image/*".toMediaTypeOrNull())
                        imagefile = MultipartBody.Part.createFormData(
                            "image",
                            File(image.get()!!).name,
                            requestFile
                        )
                        return retrofitApi.fileUpload(
                            mapValues(), imagefile!!
                        )
                    }

                    override fun onResponse(res: Response<FileUploadModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            imagePath = response.body[0].image
                            feedbackApi(context)

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
        hashMap["type"] = "images".toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["folder"] = "feeds".toRequestBody("text/plain".toMediaTypeOrNull())

        return hashMap
    }

    // api for Contact Us
    private fun feedbackApi(context: Context) {
        try {
         val hashMap : HashMap<String,String> = HashMap()
            hashMap["comment"] = message.get()!!
            hashMap["image"] = imagePath
            CallApi().callService(
                context,
                true,
                object : RequestProcessor<Response<CommonModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<CommonModel> {

                        return retrofitApi.feedback(hashMap)
                    }

                    override fun onResponse(res: Response<CommonModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                            message.set("")
                            ToastUtils.showToast(context, res.body()?.message.toString())
                            (context as FeedBackActivity).finish()
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
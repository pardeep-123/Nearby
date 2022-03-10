package com.creation.nearby.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.creation.nearby.model.AddEventModel
import com.creation.nearby.model.CommonModel
import com.creation.nearby.model.FileUploadModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.MainActivity
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.utils.Validator
import com.zxy.tiny.Tiny
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class AddFeedVM : ViewModel() {
    val feedText : ObservableField<String> = ObservableField("")
    val longitude : ObservableField<String> = ObservableField("")
    val image : ObservableField<String> = ObservableField("")
    var imagefile : MultipartBody.Part?=null
    var imagePath = ""
    fun onClick(v: View, s: String) {
        when (s) {
            "feedSubmit" -> {
                validateFeed(v.context)
            }
//            "cancelImage" ->{
//
//            }
        }
    }
    // function for validation check
    private fun validateFeed(context: Context) {
        if (image.get()?.isNotEmpty()!!) {
            if (feedText.get()?.isNotEmpty()!!) {

                uploadFileApi(context)

            } else {
                ToastUtils.showToast((context as Activity), "Write Something")
            }
        }else{
            if (feedText.get()?.isNotEmpty()!!) {

                addFeedApi(context)

            } else {
                ToastUtils.showToast((context as Activity), "Write Something")
            }
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
                        imagefile = MultipartBody.Part.createFormData("image",File(image.get()!!).name, requestFile)
                        return retrofitApi.fileUpload(
                            mapValues(),imagefile!!)
                    }

                    override fun onResponse(res: Response<FileUploadModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            imagePath = response.body[0].image
                            addFeedApi(context)

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

    // api for add feed

    private fun addFeedApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,
                object : RequestProcessor<Response<CommonModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<CommonModel> {

                        return retrofitApi.addFeed(addMapValues())
                    }

                    override fun onResponse(res: Response<CommonModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                            ToastUtils.showToast(context,res.body()?.message.toString())
                            // open home fragment
                            MainActivity.binding.sectionRecyclerView.postDelayed({

                                MainActivity.binding.sectionRecyclerView.scrollToPosition(0)

                                MainActivity.binding.sectionRecyclerView.postDelayed({
                                    MainActivity.binding.sectionRecyclerView.findViewHolderForAdapterPosition(
                                        0
                                    )?.itemView?.performClick()


                                }, 10)

                            }, 50)
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
    fun addMapValues(): HashMap<String, String> {

        val hashMap = HashMap<String, String>()
        hashMap["description"] = feedText.get()!!
        hashMap["image"] = imagePath


        return hashMap
    }

}
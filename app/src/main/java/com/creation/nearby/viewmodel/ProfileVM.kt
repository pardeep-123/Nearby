package com.creation.nearby.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creation.nearby.model.FileUploadModel
import com.creation.nearby.model.GetProfileResponse
import com.creation.nearby.model.InterestListResponse
import com.creation.nearby.model.UserListModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File

class ProfileVM: ViewModel() {

    val profileData: MutableLiveData<GetProfileResponse> = MutableLiveData()
    val getInterestListResponse: MutableLiveData<ArrayList<InterestListResponse.Body>> = MutableLiveData()
    val imageUploadResponse: MutableLiveData<FileUploadModel> = MutableLiveData()
    val image : ObservableField<String> = ObservableField("")
    var imagefile : MultipartBody.Part?=null
    var imagePath = ""

    fun getProfileApi(context: Context) {
        try {
            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<GetProfileResponse>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<GetProfileResponse> {

                        return retrofitApi.getProfile()
                    }

                    override fun onResponse(res: Response<GetProfileResponse>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            profileData.value = response
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

    fun getInterests(context: Context) {
        try {
            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<InterestListResponse>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<InterestListResponse> {

                        return retrofitApi.getInterests()
                    }

                    override fun onResponse(res: Response<InterestListResponse>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            getInterestListResponse.value = response.body as ArrayList<InterestListResponse.Body>?
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

    fun uploadGalleryImageApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<FileUploadModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<FileUploadModel> {
                        val requestFile =
                            File(image.get()!!).asRequestBody("image/*".toMediaTypeOrNull())
                        imagefile = MultipartBody.Part.createFormData("image",
                            File(image.get()!!).name, requestFile)
                        return retrofitApi.fileUpload(
                            mapImageUploadValues(),imagefile!!)
                    }

                    override fun onResponse(res: Response<FileUploadModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            imageUploadResponse.value = response
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

    fun mapImageUploadValues(): HashMap<String, RequestBody> {

        val hashMap = HashMap<String, RequestBody>()
        hashMap["type"] = "image".toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["folder"] = "gallary".toRequestBody("text/plain".toMediaTypeOrNull())

        return hashMap
    }
}
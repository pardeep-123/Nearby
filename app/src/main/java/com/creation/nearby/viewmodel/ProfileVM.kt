package com.creation.nearby.viewmodel

import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creation.nearby.model.*
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.utils.Constants.serializeToMap
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
    val galleryImageUploadResponse: MutableLiveData<FileUploadModel> = MutableLiveData()
    val profileImageUploadResponse: MutableLiveData<FileUploadModel> = MutableLiveData()
    val editProfileResponse: MutableLiveData<EditProfileResponse> = MutableLiveData()
    val image : ObservableField<String> = ObservableField("")
    val profileImage : ObservableField<String> = ObservableField("")
    var imagefile : MultipartBody.Part?=null
    var profileImageFile : MultipartBody.Part?=null
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
                            mapGallaryImageUploadValues(),imagefile!!)
                    }

                    override fun onResponse(res: Response<FileUploadModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            galleryImageUploadResponse.value = response
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

    fun mapGallaryImageUploadValues(): HashMap<String, RequestBody> {

        val hashMap = HashMap<String, RequestBody>()
        hashMap["type"] = "image".toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["folder"] = "gallary".toRequestBody("text/plain".toMediaTypeOrNull())

        return hashMap
    }

    fun uploadProfileImageApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<FileUploadModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<FileUploadModel> {
                        val requestFile =
                            File(profileImage.get()!!).asRequestBody("image/*".toMediaTypeOrNull())
                        profileImageFile = MultipartBody.Part.createFormData("image",
                            File(profileImage.get()!!).name, requestFile)
                        return retrofitApi.fileUpload(
                            mapProfileImageUploadValues(),profileImageFile!!)
                    }

                    override fun onResponse(res: Response<FileUploadModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            profileImageUploadResponse.value = response
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

    fun mapProfileImageUploadValues(): HashMap<String, RequestBody> {

        val hashMap = HashMap<String, RequestBody>()
        hashMap["type"] = "image".toRequestBody("text/plain".toMediaTypeOrNull())
        hashMap["folder"] = "user".toRequestBody("text/plain".toMediaTypeOrNull())

        return hashMap
    }

    fun editProfileApi(
        context: Context,
        editProfileRequest: EditProfileRequest,
        interestList: ArrayList<GenericModel>,
        manualInterestList: java.util.ArrayList<ManualInterestModel>
    ){

        val interestTitleList=ArrayList<String>()

        if(interestList.isNotEmpty())
        {
            val filteredList = interestList.filter { it.isSelected == 1 }
            for(i in filteredList.indices){
                interestTitleList.add(interestList[i].itemTitle.toString())
            }
        }

        val interestsStr = TextUtils.join(",",interestTitleList)
        editProfileRequest.interests = interestsStr

        val manualInterestTitleList=ArrayList<String>()
        for(i in manualInterestList.indices){
            manualInterestTitleList.add(manualInterestList[i].itemTitle.toString())
        }

        if(manualInterestTitleList.size==1){
            editProfileRequest.manual_interest = manualInterestTitleList[0]
        }else{
            val manualInterestsStr = TextUtils.join(",",manualInterestTitleList)
            editProfileRequest.manual_interest = manualInterestsStr
        }

        val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
        val map = editProfileRequest.serializeToMap()
        for (part in map) {
            builder.addFormDataPart(part.key, part.value)
        }
        val body = builder.build()

        try {
            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<EditProfileResponse>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<EditProfileResponse> {

                        return retrofitApi.editProfile(body)
                    }

                    override fun onResponse(res: Response<EditProfileResponse>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            editProfileResponse.value = response
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
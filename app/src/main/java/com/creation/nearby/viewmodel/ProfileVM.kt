package com.creation.nearby.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creation.nearby.model.GetProfileResponse
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import retrofit2.Response

class ProfileVM: ViewModel() {
    val profileData: MutableLiveData<GetProfileResponse> = MutableLiveData()
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
}
package com.creation.nearby.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creation.nearby.model.UserDetailResponse
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import retrofit2.Response

class OtherUserVM : ViewModel() {

  val userId : ObservableField<String> = ObservableField("")
  val userData : MutableLiveData<UserDetailResponse> = MutableLiveData()


     fun userDetailApi(context: Context) {

        try {
            CallApi().callService(
                context,
                true,
                object : RequestProcessor<Response<UserDetailResponse>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<UserDetailResponse> {
                        return retrofitApi.userDetail(userId.get()!!)
                    }

                    override fun onResponse(res: Response<UserDetailResponse>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                           userData.value = response
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
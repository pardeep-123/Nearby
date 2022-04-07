package com.creation.nearby.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creation.nearby.model.CommonModel
import com.creation.nearby.model.UserDetailResponse
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.OtherUserProfileActivity
import com.creation.nearby.utils.ToastUtils
import retrofit2.Response

class OtherUserVM : ViewModel() {

    val userId: ObservableField<String> = ObservableField("")
    val userData: MutableLiveData<UserDetailResponse> = MutableLiveData()


    fun onClick(v: View, s: String) {
        when (s) {
            "cancel" -> {
                swipeUserApi(v.context, userId.get()!!, "0")
            }
            "accept" -> {
                swipeUserApi(v.context, userId.get()!!, "1")
            }
        }
    }

    // to swipe the user

    private fun swipeUserApi(context: Context, userId: String, status: String) {
        try {
            val hashMap = HashMap<String, String>()
            hashMap["swipe_to"] = userId
            hashMap["status"] = status

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<CommonModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<CommonModel> {

                        return retrofitApi.swipeUser(hashMap)
                    }

                    override fun onResponse(res: Response<CommonModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            ToastUtils.showToast(context, response.message)
                            (context as OtherUserProfileActivity).finish()
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
package com.creation.nearby.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.creation.nearby.adapter.EventsAdapter
import com.creation.nearby.model.GetEventModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import retrofit2.Response

class GetEventVM : ViewModel() {

 //  val adapter by lazy { EventsAdapter }
    // get
     fun eventListApi(context: Context) {
        try {
            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<GetEventModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<GetEventModel> {

                        return retrofitApi.eventListingApi()
                    }

                    override fun onResponse(res: Response<GetEventModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!

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
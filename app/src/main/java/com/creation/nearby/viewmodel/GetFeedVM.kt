package com.creation.nearby.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.GetEventModel
import com.creation.nearby.model.GetFeedModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import retrofit2.Response

class GetFeedVM : ViewModel() {

    val feedList by lazy { ArrayList<GetFeedModel.Body>() }
    val feedAdapter by lazy { RecyclerAdapter<GetFeedModel.Body>(R.layout.items_feeds) }

    // get event list
    fun feedListApi(context: Context) {
        try {
            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<GetFeedModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<GetFeedModel> {

                        return retrofitApi.feedListing()
                    }

                    override fun onResponse(res: Response<GetFeedModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            feedList.addAll(response.body)

                            feedAdapter.addItems(feedList)
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

    fun setVisibility(): Boolean {
        return feedList.size <= 0
    }

}
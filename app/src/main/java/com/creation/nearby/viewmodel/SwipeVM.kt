package com.creation.nearby.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.model.UserListModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import retrofit2.Response

class SwipeVM : ViewModel() {

  val getSwipeList by lazy { ArrayList<UserListModel.Body>() }

  val adapter by lazy { RecyclerAdapter<UserListModel.Body>(R.layout.item_events) }

  // get event list
  fun swipeListApi(context: Context) {
    try {
      CallApi().callService(
        context,
        true,

        object : RequestProcessor<Response<UserListModel>> {
          override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<UserListModel> {

            return retrofitApi.swipeUserList()
          }

          override fun onResponse(res: Response<UserListModel>) {
            if (res.isSuccessful) {
              val response = res.body()!!
              getSwipeList.addAll(response.body)

              adapter.addItems(getSwipeList)
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
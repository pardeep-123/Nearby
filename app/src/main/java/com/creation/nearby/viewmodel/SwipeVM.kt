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

  val getSwipeList by lazy { ArrayList<UserListModel.Body.User>() }

  val adapter by lazy { RecyclerAdapter<UserListModel.Body.User>(R.layout.swipe_card_item) }

  // get event list
  fun swipeListApi(context: Context, currentLat: Double, currentLng: Double) {
    try {
      var haQueryMap= HashMap<String,String>()
      haQueryMap["page"]="1"
      haQueryMap["is_online"]="1"
      haQueryMap["gender"]="3"
      haQueryMap["latitude"]=currentLat.toString()
      haQueryMap["longitude"]=currentLng.toString()
      haQueryMap["radius"]="100"
      haQueryMap["min_age"]="1"
      haQueryMap["max_age"]="50"
      CallApi().callService(
        context,
        true,

        object : RequestProcessor<Response<UserListModel>> {
          override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<UserListModel> {

            return retrofitApi.swipeUserList(haQueryMap)
          }

          override fun onResponse(res: Response<UserListModel>) {
            if (res.isSuccessful) {
              val response = res.body()!!
              getSwipeList.addAll(response.body.user_list)

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
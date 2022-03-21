package com.creation.nearby.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.model.CommonModel
import com.creation.nearby.model.UserListModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.utils.ToastUtils
import retrofit2.Response

class SwipeVM : ViewModel() {

  val getSwipeList by lazy { ArrayList<UserListModel.Body.User>() }

  val adapter by lazy { RecyclerAdapter<UserListModel.Body.User>(R.layout.swipe_card_item) }

  // get event list
  fun swipeListApi(context: Context, currentLat: Double, currentLng: Double) {
    try {
      var haQueryMap= HashMap<String,String>()
      haQueryMap["page"]=""
      haQueryMap["is_online"]=""
      haQueryMap["gender"]=""
      haQueryMap["latitude"]=""
      haQueryMap["longitude"]=""
      haQueryMap["radius"]=""
      haQueryMap["min_age"]=""
      haQueryMap["max_age"]=""
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
              if (response.body.user_list.isNotEmpty()) {
                getSwipeList.addAll(response.body.user_list)

                adapter.addItems(getSwipeList)
                setVisibility()
              }
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

  // to swipe the user

  fun swipeUserApi(context: Context,userId: String,status: String) {
    try {
      val hashMap= HashMap<String,String>()
      hashMap["swipe_to"]=userId
      hashMap["status"]=status

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
               ToastUtils.showToast(context,response.message)
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

  fun setVisibility() : Boolean{
    return getSwipeList.size==0
  }

}
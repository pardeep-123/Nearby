package com.creation.nearby.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.FriendListModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.utils.SocketManager
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

class MessageListVM : ViewModel() {

    val onlineAdapter by lazy { RecyclerAdapter<FriendListModel.Body.Online>(R.layout.item_friends) }

    val onlineList by lazy { ArrayList<FriendListModel.Body.Online>() }
   val onlineValue : ObservableField<Boolean> = ObservableField(false)

    fun getFriendApi(context: Context) {
        try {
            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<FriendListModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<FriendListModel> {

                        return retrofitApi.friendList()
                    }

                    override fun onResponse(res: Response<FriendListModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            onlineList.clear()

                            onlineList.addAll(response.body.onlineList)
                            onlineAdapter.addItems(onlineList)

                            if (onlineList.size==0) onlineValue.set(true)
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
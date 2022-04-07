package com.creation.nearby.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.model.FriendListModel
import com.creation.nearby.model.HomeListingModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import retrofit2.Response

class FriendListVM : ViewModel(){

    val onlineAdapter by lazy { RecyclerAdapter<FriendListModel.Body.Online>(R.layout.item_friends) }
    val allFriendListAdapter by lazy { RecyclerAdapter<FriendListModel.Body.Friend>(R.layout.item_friends) }

    val onlineList by lazy { ArrayList<FriendListModel.Body.Online>() }
    val allFriendList by lazy { ArrayList<FriendListModel.Body.Friend>() }


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
                            allFriendList.clear()
                            onlineList.addAll(res.body()?.body?.onlineList!!)
                            allFriendList.addAll(res.body()?.body?.friendList!!)
                            onlineAdapter.addItems(onlineList)
                            allFriendListAdapter.addItems(allFriendList)

                        }
                    }

                    override fun onException(message: String?) {
                        Log.e("userException", "====$message")
                    }
                })

        } catch (e: Exception) {e.printStackTrace()}
    }
}
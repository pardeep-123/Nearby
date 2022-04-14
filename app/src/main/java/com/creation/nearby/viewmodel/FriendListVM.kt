package com.creation.nearby.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.FriendListModel
import com.creation.nearby.model.HomeListingModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.OngoingChatActivity
import com.creation.nearby.ui.OtherUserProfileActivity
import retrofit2.Response

class FriendListVM : ViewModel() {

    val onlineAdapter by lazy { RecyclerAdapter<FriendListModel.Body.Online>(R.layout.item_friends) }
    val allFriendListAdapter by lazy { RecyclerAdapter<FriendListModel.Body.Friend>(R.layout.item_friends) }

    val onlineList by lazy { ArrayList<FriendListModel.Body.Online>() }
    val allFriendList by lazy { ArrayList<FriendListModel.Body.Friend>() }

    init {
        onlineAdapter.setOnItemClick(object : RecyclerAdapter.OnItemClick {
            override fun onClick(view: View, position: Int, type: String) {
                val intent = Intent(view.context, OtherUserProfileActivity::class.java)
                if (PreferenceFile.retrieveUserId() != onlineList[position].swipeTo.toString())
                    intent.putExtra("userId", onlineList[position].swipeTo.toString())
                else
                    intent.putExtra("userId", onlineList[position].swipeBy.toString())
                    intent.putExtra("name", onlineList[position].firstName)
                    intent.putExtra("image", onlineList[position].image)
                intent.putExtra("from", "friendList")


                view.context.startActivity(intent)
            }

        })

        /**
         * Set intent on all friendliSt
         */
        allFriendListAdapter.setOnItemClick(object : RecyclerAdapter.OnItemClick {
            override fun onClick(view: View, position: Int, type: String) {
                val intent = Intent(view.context, OtherUserProfileActivity::class.java)
                if (PreferenceFile.retrieveUserId() != allFriendList[position].swipeTo.toString())
                    intent.putExtra("userId", allFriendList[position].swipeTo.toString())
                else
                    intent.putExtra("userId", allFriendList[position].swipeBy.toString())
                intent.putExtra("name", allFriendList[position].firstName)
                intent.putExtra("image", allFriendList[position].image)
                intent.putExtra("from", "friendList")
                view.context.startActivity(intent)

            }

        })
    }

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

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
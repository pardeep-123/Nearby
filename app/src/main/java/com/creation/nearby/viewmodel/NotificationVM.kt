package com.creation.nearby.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.model.CommonModel
import com.creation.nearby.model.NotificationModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.utils.ToastUtils
import retrofit2.Response

class NotificationVM : ViewModel() {

    val notificationList by lazy { ArrayList<NotificationModel.Body>() }

    val notificationAdapter by lazy { RecyclerAdapter<NotificationModel.Body>(R.layout.items_full_notifications) }
    var isNewRequest: ObservableField<Boolean> = ObservableField(true)

    init {
        // set click on notification list
        notificationAdapter.setOnItemClick(object : RecyclerAdapter.OnItemClick {
            override fun onClick(view: View, position: Int, type: String) {
                if (type == "accept") {
                    acceptRejectEventApi(view.context,notificationList[position].sender.id.toString(),
                        "2",notificationList[position].id.toString())
                } else if (type == "reject"){
                    acceptRejectEventApi(view.context,notificationList[position].sender.id.toString(),
                        "3",notificationList[position].id.toString())

                }
            }

        })
    }

    fun notificationListing(context: Context){
        try {
            CallApi().callService(context,true,object :RequestProcessor<Response<NotificationModel>>{
                override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<NotificationModel> {
                    return retrofitApi.notificationListing()
                }

                override fun onResponse(res: Response<NotificationModel>) {
                    if (res.isSuccessful) {
                        val response = res.body()
                        notificationList.clear()
                        notificationList.addAll(response?.body!!)
                        notificationList.forEach {
                            it.isNewRequest = it.notificationType==1
                        }
                        notificationAdapter.addItems(notificationList)

                    }
                }

                override fun onException(message: String?) {

                }

            })
        }catch (e:Exception){}
    }

    // Accept Reject Friend Requests
    fun acceptRejectEventApi(context: Context,userId:String,status:String,notificationId:String) {
        try {
            val hashMap= HashMap<String,String>()
            hashMap["swipe_by"]=userId
            hashMap["status"]=status
            hashMap["notification_id"]=notificationId
            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<CommonModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<CommonModel> {

                        return retrofitApi.acceptRejectFriend(hashMap)
                    }

                    override fun onResponse(res: Response<CommonModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            ToastUtils.showToast(context,response.message)
                            notificationListing(context)
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
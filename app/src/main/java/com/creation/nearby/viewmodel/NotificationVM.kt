package com.creation.nearby.viewmodel

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.base.AppController
import com.creation.nearby.model.HomeListingModel
import com.creation.nearby.model.NotificationModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.utils.AppUtils
import retrofit2.Response
import java.lang.Exception

class NotificationVM(val application: AppController) : AndroidViewModel(application) {

    val notificationList by lazy { ArrayList<NotificationModel.Body>() }

    val notificationAdapter by lazy { RecyclerAdapter<NotificationModel.Body>(R.layout.items_full_notifications) }

    init {
        // set click on notification list
        notificationAdapter.setOnItemClick(object : RecyclerAdapter.OnItemClick {
            override fun onClick(view: View, position: Int, type: String) {
                if (type == "accept") {

                } else if (type == "reject"){

                }
            }

        })
    }

    fun notificationListing(){
        try {
            CallApi().callService(application.applicationContext,true,object :RequestProcessor<Response<NotificationModel>>{
                override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<NotificationModel> {
                    return retrofitApi.notificationListing()
                }

                override fun onResponse(res: Response<NotificationModel>) {
                    val response = res.body()
                    notificationList.addAll(response?.body!!)
                    notificationAdapter.addItems(notificationList)
                }

                override fun onException(message: String?) {

                }

            })
        }catch (e:Exception){}
    }
}
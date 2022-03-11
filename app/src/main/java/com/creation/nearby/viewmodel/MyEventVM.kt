package com.creation.nearby.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.GetEventModel
import com.creation.nearby.model.MyEventModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.EventDetailsActivity
import retrofit2.Response

class MyEventVM : ViewModel() {

    val myEventList by lazy { ArrayList<MyEventModel.Body>() }

    val adapter by lazy { RecyclerAdapter<MyEventModel.Body>(R.layout.item_my_events) }

    init {

        adapter.setOnItemClick(object : RecyclerAdapter.OnItemClick{
            override fun onClick(view: View, position: Int, type: String) {
                if (type=="eventClick"){
                    view.context.startActivity(
                        Intent(view.context, EventDetailsActivity::class.java).putExtra("eventData",
                        myEventList[position]))

                }
            }

        })
    }
    // get event list
    fun eventListApi(context: Context) {
        try {
            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<MyEventModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<MyEventModel> {

                        return retrofitApi.myEventListingApi()
                    }

                    override fun onResponse(res: Response<MyEventModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            myEventList.addAll(response.body)
                   myEventList.forEach {
                       it.visible = false
                   }
                            adapter.addItems(myEventList)
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
        return myEventList.size <= 0
    }

}
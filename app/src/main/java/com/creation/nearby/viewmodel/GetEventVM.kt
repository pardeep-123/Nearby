package com.creation.nearby.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.EventsAdapter
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.GetEventModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.EventDetailsActivity
import retrofit2.Response

class GetEventVM : ViewModel() {
    val getEventList by lazy { ArrayList<GetEventModel.Body>() }

    val adapter by lazy { RecyclerAdapter<GetEventModel.Body>(R.layout.item_events) }

    init {

        adapter.setOnItemClick(object : RecyclerAdapter.OnItemClick{
            override fun onClick(view: View, position: Int, type: String) {
                 if (type=="eventClick"){
                     view.context.startActivity(Intent(view.context,EventDetailsActivity::class.java).putExtra("eventData",
                             getEventList[position]))

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

                object : RequestProcessor<Response<GetEventModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<GetEventModel> {

                        return retrofitApi.eventListingApi()
                    }

                    override fun onResponse(res: Response<GetEventModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            getEventList.addAll(response.body)
                            for (i in 0 until  response.body.size){
                                response.body[i].visible = response.body[i].userId.toString()!=PreferenceFile.retrieveUserId()
                            }
                            adapter.addItems(getEventList)
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
        return getEventList.size <= 0
    }

    // function to check either tick button needs to be shown or not.
//    fun showTick() : Boolean{
//
//    }
}
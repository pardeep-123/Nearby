package com.creation.nearby.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.CommonModel
import com.creation.nearby.model.GetEventModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.EventDetailsActivity
import com.creation.nearby.utils.ToastUtils
import retrofit2.Response

class GetEventVM : ViewModel() {
    val getEventList by lazy { ArrayList<GetEventModel.Body>() }

    val adapter by lazy { RecyclerAdapter<GetEventModel.Body>(R.layout.item_events) }

    init {

        adapter.setOnItemClick(object : RecyclerAdapter.OnItemClick{
            override fun onClick(view: View, position: Int, type: String) {
                when (type) {
                    "eventClick" -> {
                        view.context.startActivity(Intent(view.context,EventDetailsActivity::class.java).putExtra("eventData",
                            getEventList[position]))

                    }
                    "acceptEvent" -> {
                        acceptRejectEventApi(view.context,getEventList[position].id.toString(),"1")
                    }
                    "rejectEvent" -> {
                        acceptRejectEventApi(view.context,getEventList[position].id.toString(),"0")
                    }
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
                            getEventList.clear()
                            getEventList.addAll(response.body)
                            setVisibility()
                            for (i in 0 until  response.body.size){
                                response.body[i].visible = response.body[i].isAccepted.toString()=="2"
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

    // Accept Reject events
    fun acceptRejectEventApi(context: Context,userId:String,status:String) {
        try {
            val hashMap= HashMap<String,String>()
            hashMap["event_id"]=userId
            hashMap["status"]=status
            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<CommonModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<CommonModel> {

                        return retrofitApi.acceptRejectEvent(hashMap)
                    }

                    override fun onResponse(res: Response<CommonModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                           ToastUtils.showToast(context,response.message)
                            eventListApi(context)
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
        return getEventList.size == 0
    }

}
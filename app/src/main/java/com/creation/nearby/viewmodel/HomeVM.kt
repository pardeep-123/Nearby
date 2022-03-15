package com.creation.nearby.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.model.DataMap
import com.creation.nearby.model.HomeListingModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.EventDetailsActivity
import com.creation.nearby.ui.MainActivity
import com.creation.nearby.ui.SplashActivity
import com.google.android.gms.maps.model.LatLng
import retrofit2.Response

class HomeVM : ViewModel() {

    val eventList by lazy { ArrayList<HomeListingModel.Body.Event>() }
    val feedList by lazy { ArrayList<HomeListingModel.Body.Feed>() }
    val notificationList by lazy { ArrayList<HomeListingModel.Body.Notification>() }
    val mapList by lazy { ArrayList<HomeListingModel.Body.User>() }

    val mapListData: MutableLiveData<ArrayList<DataMap>> = MutableLiveData()

    // list of lat long
    val mList = ArrayList<DataMap>()

    // initialze adapters
    val eventAdapter by lazy { RecyclerAdapter<HomeListingModel.Body.Event>(R.layout.discover_item) }
    val feedAdapter by lazy { RecyclerAdapter<HomeListingModel.Body.Feed>(R.layout.items_feeds) }
    val notificationAdapter by lazy { RecyclerAdapter<HomeListingModel.Body.Notification>(R.layout.item_notification) }

    // set api and create set adapter method
    init {

        eventAdapter.setOnItemClick(object : RecyclerAdapter.OnItemClick {
            override fun onClick(view: View, position: Int, type: String) {
                if (type == "eventClick") {
                    MainActivity.binding.sectionRecyclerView.postDelayed({

                        MainActivity.binding.sectionRecyclerView.scrollToPosition(3)

                        MainActivity.binding.sectionRecyclerView.postDelayed({
                            MainActivity.binding.sectionRecyclerView.findViewHolderForAdapterPosition(
                                3
                            )?.itemView?.performClick()


                        }, 10)

                    }, 50)

                }

            }
        })
        // set click on feed adapter
        feedAdapter.setOnItemClick(object : RecyclerAdapter.OnItemClick {
            override fun onClick(view: View, position: Int, type: String) {
                if (type == "feedClick") {
                    MainActivity.binding.sectionRecyclerView.postDelayed({

                        MainActivity.binding.sectionRecyclerView.scrollToPosition(4)

                        MainActivity.binding.sectionRecyclerView.postDelayed({
                            MainActivity.binding.sectionRecyclerView.findViewHolderForAdapterPosition(
                                4
                            )?.itemView?.performClick()


                        }, 10)

                    }, 50)
                }
            }

        })

        // set click on notification list
        notificationAdapter.setOnItemClick(object : RecyclerAdapter.OnItemClick {
            override fun onClick(view: View, position: Int, type: String) {
                if (type == "accept") {

                 } else if (type == "reject"){

                }
            }

        })
    }

    // get event list
    fun homeListingApi(context: Context, currentLat: Double, currentLng: Double) {
        try {
            var haQueryMap=HashMap<String,String>()

            haQueryMap["is_online"]="1"
            haQueryMap["gender"]="0"
            haQueryMap["latitude"]=currentLat.toString()
            haQueryMap["longitude"]=currentLng.toString()
            haQueryMap["radius"]="100"
            haQueryMap["min_age"]="1"
            haQueryMap["max_age"]="50"

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<HomeListingModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<HomeListingModel> {

                        return retrofitApi.homeListing(haQueryMap)
                    }

                    override fun onResponse(res: Response<HomeListingModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!

                            // show max 5 events on the page
                            response.body.eventList.forEachIndexed { index, it ->
                                if (index<5){
                                    eventList.add(it)
                                }
                            }
                              response.body.feedList.forEachIndexed { index, feed ->
                                  if (index<5)
                                  feedList.add(feed)
                              }

                            notificationList.addAll(response.body.notificationList)
                            // add data to map list
                            for (i in 0 until response.body.userList.size) {
                                response.body.userList.run {
                                    mList.add(
                                        DataMap(
                                            LatLng(
                                                this[i].latitude.toDouble(),
                                                this[i].longitude.toDouble()
                                            ), this[i].id, this[i].name
                                        )
                                    )

                                }
                            }
                            mapListData.value= mList
                            eventAdapter.addItems(eventList)
                            feedAdapter.addItems(feedList)
                            notificationAdapter.addItems(notificationList)

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
package com.creation.nearby.viewmodel

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.CommonModel
import com.creation.nearby.model.DataMap
import com.creation.nearby.model.HomeListingModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.DetailedProfileActivity
import com.creation.nearby.ui.MainActivity
import com.creation.nearby.ui.OtherUserProfileActivity
import com.creation.nearby.ui.authentication.OtpActivity
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.ToastUtils
import com.google.android.gms.maps.model.LatLng
import retrofit2.Response

class HomeVM : ViewModel() {

    val userList by lazy { ArrayList<HomeListingModel.Body.User>() }
    val eventList by lazy { ArrayList<HomeListingModel.Body.Event>() }
    val notificationList by lazy { ArrayList<HomeListingModel.Body.Notification>() }
    val latitude: ObservableField<String> = ObservableField("")
    val longitude: ObservableField<String> = ObservableField("")
    val gender: ObservableField<String> = ObservableField("")
    val filterBy: ObservableField<String> = ObservableField("")
    val distance: ObservableField<String> = ObservableField("")
    val minAge: ObservableField<String> = ObservableField("")
    val maxAge: ObservableField<String> = ObservableField("")
    val mapListData: MutableLiveData<ArrayList<DataMap>> = MutableLiveData()

    // list of lat long
    val mList = ArrayList<DataMap>()

    // initialze adapters
    val eventAdapter by lazy { RecyclerAdapter<HomeListingModel.Body.User>(R.layout.new_discover_item) }
    val feedAdapter by lazy { RecyclerAdapter<HomeListingModel.Body.Event>(R.layout.items_new_events) }
    val notificationAdapter by lazy { RecyclerAdapter<HomeListingModel.Body.Notification>(R.layout.item_notification) }

    // set api and create set adapter method
    init {

        eventAdapter.setOnItemClick(object : RecyclerAdapter.OnItemClick {
            override fun onClick(view: View, position: Int, type: String) {
                if (type == "userClick") {
                    val intent = Intent(view.context, OtherUserProfileActivity::class.java)
                    intent.putExtra("userId", userList[position].id.toString())
                    view.context.startActivity(intent)

                }

            }
        })
        // set click on feed adapter
        feedAdapter.setOnItemClick(object : RecyclerAdapter.OnItemClick {
            override fun onClick(view: View, position: Int, type: String) {
                if (type == "eventClick") {
                    MainActivity.binding.sectionRecyclerView.postDelayed({

                        MainActivity.binding.sectionRecyclerView.scrollToPosition(3)

                        MainActivity.binding.sectionRecyclerView.postDelayed({
                            MainActivity.binding.sectionRecyclerView.findViewHolderForAdapterPosition(
                                3)?.itemView?.performClick()

                        }, 10)

                    }, 50)
                }
            }

        })

        // set click on notification list
        notificationAdapter.setOnItemClick(object : RecyclerAdapter.OnItemClick {
            override fun onClick(view: View, position: Int, type: String) {
                if (type == "accept") {
                    acceptRejectEventApi(
                        view.context, notificationList[position].sender.id.toString(),
                        "2", notificationList[position].id.toString()
                    )
                } else if (type == "reject") {
                    acceptRejectEventApi(
                        view.context, notificationList[position].sender.id.toString(),
                        "3", notificationList[position].id.toString()
                    )

                }
            }

        })
    }

    // get event list
    fun homeListingApi(
        context: Context, currentLat: String, currentLng: String,
        filterBy: String, gender: String, distance: String, minAge: String, maxAge: String
    ) {
        try {
            val haQueryMap = HashMap<String, String>()

            haQueryMap["is_online"] = filterBy
            haQueryMap["gender"] = gender
            haQueryMap["latitude"] = currentLat
            haQueryMap["longitude"] = currentLng
            haQueryMap["radius"] = distance
            haQueryMap["min_age"] = minAge
            haQueryMap["max_age"] = maxAge

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
                            userList.clear()
                            eventList.clear()
                            // show max 5 events on the page
                            response.body.userList.forEachIndexed { index, it ->
                                //  if (index<5){

                                userList.add(it)
                                // }
                            }
                            response.body.eventList.forEachIndexed { index, feed ->
                                //   if (index<5)
                                eventList.add(feed)
                            }

                            // add data to map list
//                            if(response.body.userList.isNotEmpty()) {
//                                for (i in 0 until response.body.userList.size) {
//                                    response.body.userList.run {
//                                        mList.add(
//                                            DataMap(
//                                                LatLng(
//                                                    this[i].latitude.toDouble(),
//                                                    this[i].longitude.toDouble()
//                                                ), this[i].id, this[i].firstname,this[i].lastname
//                                            )
//                                        )
//                                    }
//                                }
//                            }
                            //  mapListData.value= mList
                            eventAdapter.addItems(userList)
                            feedAdapter.addItems(eventList)
                            notificationList.clear()
                            notificationList.addAll(response.body.notificationList)
                            notificationList.forEach {
                                it.isNewRequest = it.notificationType == 1
                            }
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

    fun checkFirstTimeLoginApi(context: Context) {
        try {
            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<CommonModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<CommonModel> {

                        return retrofitApi.checkFirstTimeLoginStatus("1")
                    }

                    override fun onResponse(res: Response<CommonModel>) {
                        if (res.isSuccessful) {
                            PreferenceFile.storeKey(context, Constants.IS_FIRST_LOGIN, "1")
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

    fun acceptRejectEventApi(
        context: Context,
        userId: String,
        status: String,
        notificationId: String
    ) {
        try {
            val hashMap = HashMap<String, String>()
            hashMap["swipe_by"] = userId
            hashMap["status"] = status
            hashMap["notification_id"] = notificationId
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
                            ToastUtils.showToast(context, response.message)
                            homeListingApi(
                                context,
                                latitude.get()!!,
                                longitude.get()!!,
                                filterBy.get()!!,
                                gender.get()!!,
                                distance.get()!!,
                                minAge.get()!!,
                                maxAge.get()!!
                            )
                            notificationAdapter.notifyDataSetChanged()
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
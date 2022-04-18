package com.creation.nearby.base

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.creation.nearby.R
import com.creation.nearby.utils.SocketManager
import com.google.android.libraries.places.api.Places
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.util.*


class AppController : Application(), AppLifecycleHandler.AppLifecycleDelegates {


    private var lifecycleHandler: AppLifecycleHandler? = null


    override fun onCreate() {
        super.onCreate()
        mInstance = this
        getSocketManager()
        initializeSocket()
        if (PreferenceFile.retrieveLoginData(this) != null) {
          //  getSocketManager()
        }


        Album.initialize(
            AlbumConfig.newBuilder(this)
                .setAlbumLoader(MediaLoader())
                .setLocale(Locale.getDefault())
                .build()
        )

        Places.initialize(this, getString(R.string.map_key))
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        lifecycleHandler = AppLifecycleHandler(this)
        registerLifeCycleHandler(lifecycleHandler)


    }


    companion object {
        fun getSocketManager(): SocketManager? {
            return mSocket
        }


        private fun initializeSocket() {
            mSocket = SocketManager()
            mSocket!!.init()
        }

        lateinit var mInstance: AppController
        var mSocket : SocketManager?=null
      //  var mSocketManager: SocketManager? = null
        fun getInstance(): AppController {
            return mInstance
        }

    }

    override fun onAppBackgrounded() {
        Log.e("DisconnectSocket", "Disconnect")
        mSocket!!.disconnectAll()
    }

    override fun onAppForegrounded() {
        Log.e("ConnectSocket", "Connect")
        if (!mSocket!!.isConnected() || mSocket!!.getmSocket() == null) {
            mSocket!!.init()
        }
    }


    private fun registerLifeCycleHandler(lifeCycleHandler: AppLifecycleHandler?) {
        registerActivityLifecycleCallbacks(lifeCycleHandler)
        registerComponentCallbacks(lifeCycleHandler)
    }

    fun hasNetwork(): Boolean {
        return mInstance.checkIfHasNetwork()
    }

    private fun checkIfHasNetwork(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


}

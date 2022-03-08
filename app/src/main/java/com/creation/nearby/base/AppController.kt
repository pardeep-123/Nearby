package com.creation.nearby.base

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.creation.nearby.R
import com.google.android.libraries.places.api.Places
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import java.util.*


class AppController : Application(), AppLifecycleHandler.AppLifecycleDelegates {


    private var lifecycleHandler: AppLifecycleHandler? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
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


        lateinit var mInstance: AppController

        var mSocketManager: SocketManager? = null
        fun getInstance(): AppController {
            return mInstance
        }

    }


    private fun getSocketManager(): SocketManager {
        if (mSocketManager == null) {
          //  mSocketManager = socket
        }
        return mSocketManager!!
    }

    override fun onAppForegrounded() {
        if (mSocketManager != null && !mSocketManager!!.isConnected()) {
            mSocketManager!!.onConnect()
        }
    }

    override fun onAppBackgrounded() {
        if (mSocketManager != null && mSocketManager!!.isConnected()) {
            mSocketManager!!.onDisconnect()
        }
    }


    private fun registerLifeCycleHandler(lifeCycleHandler: AppLifecycleHandler?) {
        registerActivityLifecycleCallbacks(lifeCycleHandler)
        registerComponentCallbacks(lifeCycleHandler)
    }


}

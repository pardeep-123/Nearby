package com.creation.nearby.utils

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.creation.nearby.R
import com.creation.nearby.ui.MainActivity
import com.creation.nearby.ui.OngoingChatActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.*
import kotlin.coroutines.CoroutineContext


class MyFirebaseMessagingService : FirebaseMessagingService(), CoroutineScope {

    var title: String? = ""
    var message: String? = ""
    var notificationManager: NotificationManager? = null
    var notifyID = 1


    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job


    override fun onNewToken(refreshedToken: String) {
        super.onNewToken(refreshedToken)
        Log.d("fcm_token", "-----$refreshedToken")

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.e("new_message", "=======${remoteMessage.data}")
        if (remoteMessage.data.isNotEmpty()) {
            try {
                val json = JSONObject(remoteMessage.data["body"]!!)
                sendNotification(json)
            } catch (e: Exception) {
                Log.e("TAG", "Exception: " + e.message)
            }
            //JSONObject(remoteMessage.data.get("body")).get("name")
        }
    }


    fun sendNotification(json: JSONObject) {
        val intent: Intent
        if (json.getString("type")=="4"){
            intent = Intent(this, OngoingChatActivity::class.java)
            intent.putExtra("name",json.getString("name"))
            intent.putExtra("image",json.getString("sender_image"))
            intent.putExtra("user2Id",json.getString("id"))
        }else{
            intent = Intent(this, MainActivity::class.java)

        }


        val notificationId = 1
        val channelId = "channel-01"
        val channelName = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_HIGH

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val mChannel = NotificationChannel(channelId, channelName, importance)
            mChannel.enableVibration(true)
            mNotificationManager.createNotificationChannel(mChannel)
            val mBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(notificationIcon).setLargeIcon(
                    BitmapFactory.decodeResource(
                        resources,
                        notificationIcon
                    )
                )
                .setAutoCancel(true)
                .setContentTitle(json.getString("title")).setContentText(json.getString("message"))
            val stackBuilder = TaskStackBuilder.create(this)
            stackBuilder.addNextIntent(intent)
            val resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            mBuilder.setContentIntent(resultPendingIntent)
            mNotificationManager.notify(notificationId, mBuilder.build())
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val context = baseContext
            val mBuilder = NotificationCompat.Builder(context).setContentTitle(json.getString("title"))
                .setContentText(json.getString("message")).setSmallIcon(notificationIcon).setLargeIcon(
                    BitmapFactory.decodeResource(
                        context.resources,
                        notificationIcon
                    )
                )
                .setPriority(Notification.PRIORITY_HIGH)
            mBuilder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            mBuilder.setAutoCancel(true)
            mBuilder.setContentIntent(pendingIntent)
            val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            mNotificationManager.notify(0, mBuilder.build())
        }
    }
    private val notificationIcon: Int
        get() {
            val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            return if (useWhiteIcon) R.drawable.logo else R.drawable.logo

        }

}
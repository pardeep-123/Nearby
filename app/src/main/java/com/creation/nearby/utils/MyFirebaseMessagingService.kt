package com.creation.nearby.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
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
        Log.d(TAG, "Refreshed token: $refreshedToken")
        Log.d("fcm_token", "-----$refreshedToken")

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.e("new_message", "=======${remoteMessage.data}")
        val myId = ""
        val data1 = remoteMessage.data


        val senderID=""
        val name =""
        var image =""
        var userId=""
        var groupId=1
        var type=""
     //   type = data1["type"].toString()

        var intent1 = Intent()
        if(!senderID.equals(""))
        {
            intent1 = Intent(this, OngoingChatActivity::class.java)
                .putExtra("senderID", senderID)
                .putExtra("senderName", name)
                .putExtra("senderImage", image)
                .putExtra("userId", userId)
                .putExtra("disconnect", userId)
        }
        else
        {

            intent1 = Intent(this, MainActivity::class.java)
                .putExtra("eventfragment", "EventFragment")
        }

        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        val pendingIntent = PendingIntent.getActivity(this, 0, if (groupId == 0) { intent1
            }
        else {
            var intent = Intent()
            if(type.equals("1"))
            {
                intent = Intent(this, MainActivity::class.java)
                    .putExtra("eventfragment", "EventFragment")
            }
            else
            {
                intent = Intent(this, MainActivity::class.java)
                    .putExtra("groupChat", "groupChat")
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }, PendingIntent.FLAG_ONE_SHOT
        )


        val channelId = applicationContext.packageName
        var numMessages = 0;

        val builder = NotificationCompat.Builder(this, channelId)
        val icon = BitmapFactory.decodeResource(resources, R.drawable.logo)


        builder.setSmallIcon(notificationIcon)
            builder.setLargeIcon(icon)
            .setColor(resources.getColor(R.color.black))
                .setContentTitle(getString(R.string.app_name))
                .setContentText(data1["body"]!!).setAutoCancel(true)
                .setContentIntent(pendingIntent)


//        builder.setContentText(currentText).setNumber(++numMessages);

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)

        }
        manager.notify(((Date().time / 1000L % Int.MAX_VALUE).toInt()), builder.build())
        Log.e("new_message", "==212=222====${myId}")


    }

    private val manager: NotificationManager?
        get() {
            if (notificationManager == null) {
                notificationManager =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            }

            return notificationManager
        }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
        var myChatVisible = true
        var currentChatUser = ""
        var chatNotification: MutableLiveData<String> = MutableLiveData()
        var chatNotifyLive: LiveData<String> = chatNotification

    }

    private val notificationIcon: Int
        get() {
            val useWhiteIcon = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            return if (useWhiteIcon) R.drawable.logo else R.drawable.logo

        }

}
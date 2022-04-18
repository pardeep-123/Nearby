package com.creation.nearby.ui.call

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.base.AppController
import com.creation.nearby.utils.Constants.IMAGE_BASE_URL
import com.creation.nearby.utils.SocketManager
import kotlinx.android.synthetic.main.activity_incoming_call.*
import org.json.JSONArray
import org.json.JSONObject

class IncomingCallActivity : AppCompatActivity(), SocketManager.Observer {
    var mCallerId = 0
    var mrecieverID = 0
    var mSenderImage = ""
    var callerName = ""
    var mChannelName = ""
    var agoraToken = ""
    var requestId = ""
    var callerImage = ""
    var receiverId = ""
   // private var mAnimator: PortraitAnimator? = null
    private var mPlayer: MediaPlayer? = null
    private var mCounter: CountDownTimer? = null
    private lateinit var socketManager: SocketManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_incoming_call)
        initializeSocket()
        activateReceiverListenerSocket()

        receiverId = intent.getStringExtra("receiverId").toString()
        mChannelName = intent.getStringExtra("channelName").toString()
        agoraToken = intent.getStringExtra("agoraToken").toString()
        // requestId = intent.getStringExtra("requestId").toString()
        callerName = intent.getStringExtra("callerName").toString()
        callerImage = intent.getStringExtra("callerImage").toString()
        tvUserName.text = callerName
        Glide.with(this).load(IMAGE_BASE_URL+callerImage).into(circleImageView4)

        Log.e("channelName", mChannelName)
        //videoToken = intent.getStringExtra("videoToken")!!


        setOnClicks()

//        mAnimator = PortraitAnimator(
//            findViewById(R.id.anim_layer_1),
//            findViewById(R.id.anim_layer_2),
//            findViewById(R.id.anim_layer_3)
//        )
        timeCounter()
        //startRinging()


    }


    fun activateReceiverListenerSocket() {
//        socketManager.call_statusActivate()
    }

    private fun setOnClicks() {
        phone_call_btn.setOnClickListener {
            stopRinging()
            if (AppController.getInstance().hasNetwork()) {
//                val jsonObject = JSONObject()
//                //jsonObject.put("requestId", requestId)
//                jsonObject.put("userType", "1")
//                jsonObject.put("status", CALL_CONNECTED.toString())
//                jsonObject.put("channelName", mChannelName)
//                jsonObject.put("callDuration", "")
//                jsonObject.put("type", VOICE)
//                jsonObject.put("friendId", receiverId)
//                socketManager.callStatus(jsonObject)

                val intent = Intent(this@IncomingCallActivity, VoiceChatViewActivity::class.java)
                // intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                intent.putExtra("receiverId", receiverId)
                intent.putExtra("channelName", mChannelName)
                intent.putExtra("agoraToken", agoraToken)
                // intentAction.putExtra("requestId", callData.requestId.toString());
                intent.putExtra("callerName", callerName)
                intent.putExtra("callerImage", callerImage)
                startActivity(intent)
                finish()

            }

        }
        cancel_call_btn.setOnClickListener {
            stopRinging()
            finish()
            /* if (AppController.hasNetwork()) {
                 val jsonObject = JSONObject()
                 jsonObject.put("userType", "1")
                 jsonObject.put("status", CALL_DECLINE.toString())
                 jsonObject.put("channelName", mChannelName)
                 jsonObject.put("callDuration", "")
                 jsonObject.put("type", VOICE)
                 jsonObject.put("friendId", receiverId)
                 socketManager.callStatus(jsonObject)

                 val notifManager: NotificationManager =
                     getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                 notifManager.cancelAll()
             } else {
             }*/
            //   showAlertWithOk(resources.getString(R.string.internet_connection))
        }
    }

    private fun initializeSocket() {

        socketManager = AppController.getSocketManager()!!
        if (!socketManager.isConnected() || socketManager.getmSocket() == null) {
            socketManager.init()
        }
        socketManager.unRegister(this)
        socketManager.onRegister(this)

    }


    override fun onDestroy() {
        socketManager.unRegister(this)

        super.onDestroy()
        stopRinging()
        mCounter?.cancel()
    }

    override fun onResume() {
        super.onResume()
        socketManager.onRegister(this)
    }

    private fun startRinging() {
        /*if (isCallee()) {
            mPlayer = playCalleeRing()
        } else if (isCaller()) {*/
        mPlayer = playCallerRing()
        /*}*/
    }

    private fun playCallerRing(): MediaPlayer {
        return startRinging(R.raw.basic_tones)
    }


    private fun startRinging(resource: Int): MediaPlayer {
        val player = MediaPlayer.create(this, resource)
        player.isLooping = true
        player.start()
        return player
    }

    private fun stopRinging() {

        try {
            runOnUiThread {
                if (mPlayer != null && mPlayer?.isPlaying!!) {
                    mPlayer?.stop()
                    mPlayer?.release()
                    mPlayer = null
                }
            }

        } catch (e: Exception) {
        }
    }

//    override fun onStart() {
//        super.onStart()
//        mAnimator?.start()
//    }

    override fun onStop() {
        super.onStop()
        stopRinging()
//        mAnimator?.stop()
        socketManager.unRegister(this)
    }


    private fun timeCounter() {
        //mCounter = object : CountDownTimer(45000, 1000) {
        mCounter = object : CountDownTimer(45000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                Log.e("Tag", "seconds remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                //showToast(resources.getString(R.string.no_answer))
                /* val jsonObject = JSONObject()
                 jsonObject.put("userType", "1")
                 jsonObject.put("status", CALL_DECLINE.toString())
                 jsonObject.put("channelName", mChannelName)
                 jsonObject.put("callDuration", "")
                 jsonObject.put("type", VOICE)
                 jsonObject.put("friendId", receiverId)
                 socketManager.callStatus(jsonObject)*/
                finish()

//                val notifManager: NotificationManager =
//                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//                notifManager.cancelAll()
//                finish()
            }
        }.start()
    }

    private class PortraitAnimator internal constructor(
        private val mLayer1: View,
        private val mLayer2: View,
        private val mLayer3: View
    ) {
        private val mAnim1: Animation
        private val mAnim2: Animation
        private val mAnim3: Animation
        private var mIsRunning = false
        private fun buildAnimation(startOffset: Int): AnimationSet {
            val set = AnimationSet(true)
            val alphaAnimation = AlphaAnimation(1.0f, 0.0f)
            alphaAnimation.duration = ANIM_DURATION.toLong()
            alphaAnimation.startOffset = startOffset.toLong()
            alphaAnimation.repeatCount = Animation.INFINITE
            alphaAnimation.repeatMode = Animation.RESTART
            alphaAnimation.fillAfter = true
            val scaleAnimation = ScaleAnimation(
                1.0f, 1.3f, 1.0f, 1.3f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f
            )
            scaleAnimation.duration = ANIM_DURATION.toLong()
            scaleAnimation.startOffset = startOffset.toLong()
            scaleAnimation.repeatCount = Animation.INFINITE
            scaleAnimation.repeatMode = Animation.RESTART
            scaleAnimation.fillAfter = true
            set.addAnimation(alphaAnimation)
            set.addAnimation(scaleAnimation)
            return set
        }

        fun start() {
            if (!mIsRunning) {
                mIsRunning = true
                mLayer1.visibility = View.VISIBLE
                mLayer2.visibility = View.VISIBLE
                mLayer3.visibility = View.VISIBLE
                mLayer1.startAnimation(mAnim1)
                mLayer2.startAnimation(mAnim2)
                mLayer3.startAnimation(mAnim3)
            }
        }

        fun stop() {
            mLayer1.clearAnimation()
            mLayer2.clearAnimation()
            mLayer3.clearAnimation()
            mLayer1.visibility = View.GONE
            mLayer2.visibility = View.GONE
            mLayer3.visibility = View.GONE
        }

        companion object {
            const val ANIM_DURATION = 3000
        }

        init {
            mAnim1 = buildAnimation(0)
            mAnim2 = buildAnimation(1000)
            mAnim3 = buildAnimation(2000)
        }
    }

    override fun onBackPressed() {
        // showToast("Please accept or decline call")
    }

    override fun onResponseArray(event: String, args: JSONArray) {

    }

    override fun onResponse(event: String, args: JSONObject) {

        when (event) {

//            call_status_listener -> {
//                runOnUiThread {
//
//                    stopRinging()
//                    var data = args as JSONObject
//                    Log.e("callResponse", data.toString())
//                    val gson = GsonBuilder().create()
//                    val callData = gson.fromJson(data.toString(), CallConnectResponse::class.java)
//                    stopService()
//                    if (callData.status == CALL_DECLINE||callData.status ==CALL_DESCONNECT||callData.status == CALL_MISSCALLED) {
//                        val intent = Intent(this@IncomingCallActivity, MainActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//                        startActivity(intent)
//                        finish()
//                    } else {
//                        if (callData.type.equals(VOICE.toString())) {
//                            if (callData.status==CALL_CONNECTED){
//
//                                val intent = Intent(this@IncomingCallActivity, VoiceChatViewActivity::class.java)
//                               // intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
//                                intent.putExtra("receiverId", receiverId)
//                                intent.putExtra("channelName", mChannelName)
//                                intent.putExtra("agoraToken", agoraToken)
//                                startActivity(intent)
//                                finish()
//                            }
//
//                        } else {
////                        val intent = Intent(this@IncomingCallActivity, VideoCallActivity::class.java)
////                        intent.flags = Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
////                        intent.putExtra("requestId", requestId.toString())
////                        intent.putExtra("channelName", mChannelName)
////                        intent.putExtra("agoraToken", agoraToken)
////                        startActivity(intent)
////                        finish()
//                        }
//                    }
//                }
//
//            }

        }

    }

    override fun onError(event: String, vararg args: Array<*>) {

    }

}

package com.creation.nearby.ui.call

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.base.AppController
import com.creation.nearby.ui.MainActivity
import com.creation.nearby.utils.Constants.IMAGE_BASE_URL
import com.creation.nearby.utils.SocketManager
import com.google.gson.GsonBuilder
import io.agora.rtc.Constants
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import kotlinx.android.synthetic.main.activity_voice_chat_view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.jvm.Throws

class VoiceChatViewActivity : AppCompatActivity(), SocketManager.Observer {
    private var mChannel: String? = null
    private var receiverId=""
    private val mPeerUid = 0
    private var mRtcEngine: RtcEngine? = null
    var peerImage: ImageView? = null
    var usernameText: TextView? = null
    var call_role: TextView? = null
    var preference: SharedPreferences? = null
    var timer: Timer? = null
    var socketManager: SocketManager? = null
    var timeNew = 0
    private val activityScope = CoroutineScope(Dispatchers.Main)
    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        /**
         * Occurs when a remote user (Communication)/host (Live Broadcast) leaves the channel.
         *
         * There are two reasons for users to become offline:
         *
         * Leave the channel: When the user/host leaves the channel, the user/host sends a goodbye message. When this message is received, the SDK determines that the user/host leaves the channel.
         * Drop offline: When no data packet of the user or host is received for a certain period of time (20 seconds for the communication profile, and more for the live broadcast profile), the SDK assumes that the user/host drops offline. A poor network connection may lead to false detections, so we recommend using the Agora RTM SDK for reliable offline detection.
         *
         * @param uid ID of the user or host who
         * leaves
         * the channel or goes offline.
         * @param reason Reason why the user goes offline:
         *
         * USER_OFFLINE_QUIT(0): The user left the current channel.
         * USER_OFFLINE_DROPPED(1): The SDK timed out and the user dropped offline because no data packet was received within a certain period of time. If a user quits the call and the message is not passed to the SDK (due to an unreliable channel), the SDK assumes the user dropped offline.
         * USER_OFFLINE_BECOME_AUDIENCE(2): (Live broadcast only.) The client role switched from the host to the audience.
         */
        override fun onUserOffline(uid: Int, reason: Int) { // Tutorial Step 4
            runOnUiThread { onRemoteUserLeft(uid, reason) }
        }

        override fun onUserJoined(uid: Int, elapsed: Int) {
            super.onUserJoined(uid, elapsed)
            try {
                mCounter!!.cancel()
            } catch (e: Exception) {
            }
            Log.e("remote_join","onUserJoined")
        }


        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            super.onJoinChannelSuccess(channel, uid, elapsed)
            Log.e("CheckJoin","Join channel success")
            Log.e("joinUserId",uid.toString())

        }

        override fun onLeaveChannel(stats: RtcStats?) {
            super.onLeaveChannel(stats)
            Log.e("leaveChannel",stats.toString())
        }


        /**
         * Occurs when a remote user stops/resumes sending the audio stream.
         * The SDK triggers this callback when the remote user stops or resumes sending the audio stream by calling the muteLocalAudioStream method.
         *
         * @param uid ID of the remote user.
         * @param muted Whether the remote user's audio stream is muted/unmuted:
         *
         * true: Muted.
         * false: Unmuted.
         */


        override fun onUserMuteAudio(uid: Int, muted: Boolean) { // Tutorial Step 6
            runOnUiThread { onRemoteUserVoiceMuted(uid, muted) }
        }
    }
    private var mCounter: CountDownTimer? = null

    fun startTimer(){
        mCounter = object : CountDownTimer(45000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                Log.e("Tag", "seconds remaining: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                gotoHome()

            }
        }.start()

    }

    private var mAnimator:  PortraitAnimator? = null



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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_chat_view)
        initialiseSocket()
        timer = Timer()

        peerImage = findViewById<View>(R.id.circleImageView4) as ImageView
        usernameText = findViewById<View>(R.id.textView12) as TextView
//        call_role = findViewById<View>(R.id.call_role) as TextView
        activateReceiverListenerSocket()
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO, PERMISSION_REQ_ID_RECORD_AUDIO)) {
            initAgoraEngineAndJoinChannel()
        }

        /*  preference =  BaseApplication.instance.getSharedPreferences("GapHealth", 0);

        if(preference.getString("otherUserName","").equals(""))
        {}
        else
        {
            usernameText.setText(preference.getString("otherUserName",""));
        }

        Glide.with(VoiceChatViewActivity.this)
                .load("http://3.22.39.195:1233/images/uploads/" +preference.getString("otherUserImage",""))
                .placeholder(R.drawable.chat_profile_2)
                .into(peerImage);*/

        startTimer()

        var callerName = intent.getStringExtra("callerName").toString()
        var callerImage = intent.getStringExtra("callerImage").toString()

        Glide.with(this).load(IMAGE_BASE_URL+callerImage).into(circleImageView4)
        usernameText!!.setText(callerName)
//        mAnimator = PortraitAnimator(
//            findViewById(R.id.anim_layer_1),
//            findViewById(R.id.anim_layer_2),
//            findViewById(R.id.anim_layer_3)
//        )


    }

    override fun onBackPressed() {
        super.onBackPressed()
        // callDisconnect()
    }

    private fun callDisconnect() {
//        val jsonObject = JSONObject()
//        jsonObject.put("userType", "1")
//        jsonObject.put("status", CALL_DESCONNECT.toString())
//        jsonObject.put("channelName", mChannel)
//        jsonObject.put("callDuration", "")
//        jsonObject.put("type", VOICE)
//        jsonObject.put("friendId", receiverId)
//        socketManager!!.callStatus(jsonObject)

    }

    override fun onStart() {
        super.onStart()
        mAnimator?.start()

    }

    fun activateReceiverListenerSocket() {
//        socketManager?.call_statusActivate()
    }

    private fun initialiseSocket() {
        socketManager = AppController.getSocketManager()
        if (!socketManager!!.isConnected() || socketManager!!.getmSocket() == null) {
            socketManager!!.init()
        }
        socketManager!!.unRegister(this)
        socketManager!!.onRegister(this)
    }

    private fun initAgoraEngineAndJoinChannel() {
        initializeAgoraEngine() // Tutorial Step 1
        joinChannel() // Tutorial Step 2
    }

    fun checkSelfPermission(permission: String, requestCode: Int): Boolean {
        Log.i(LOG_TAG, "checkSelfPermission $permission $requestCode")
        if (ContextCompat.checkSelfPermission(
                this,
                permission
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(permission),
                requestCode
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.i(LOG_TAG, "onRequestPermissionsResult " + grantResults[0] + " " + requestCode)
        when (requestCode) {
            PERMISSION_REQ_ID_RECORD_AUDIO -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    initAgoraEngineAndJoinChannel()
                } else {
                    showLongToast("No permission for " + Manifest.permission.RECORD_AUDIO)
                    finish()
                }
            }
        }
    }

    fun showLongToast(msg: String?) {
        runOnUiThread { Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show() }
    }


    override fun onDestroy() {
        super.onDestroy()


        //  callDisconnect()
        socketManager?.unRegister(this)
        endCallAgora()
        if (timer != null) {
            timer!!.cancel()
            timer!!.purge()
        }
    }

    fun endCallAgora(){
        leaveChannel()
        RtcEngine.destroy()
        mRtcEngine = null
    }
    // Tutorial Step 7
    fun onLocalAudioMuteClicked(view: View) {
        val iv = view as ImageView
        if (iv.isSelected) {
            iv.isSelected = false
            iv.clearColorFilter()
        } else {
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.albumColorPrimary), PorterDuff.Mode.MULTIPLY)
        }

        // Stops/Resumes sending the local audio stream.
        mRtcEngine!!.muteLocalAudioStream(iv.isSelected)
    }

    fun onSwitchSpeakerphoneClicked(view: View) {
        val iv = view as ImageView
        if (iv.isSelected) {
            iv.isSelected = false
            iv.clearColorFilter()
        } else {
            iv.isSelected = true
            iv.setColorFilter(resources.getColor(R.color.albumColorPrimary), PorterDuff.Mode.MULTIPLY)
        }

        // Enables/Disables the audio playback route to the speakerphone.
        //
        // This method sets whether the audio is routed to the speakerphone or earpiece. After calling this method, the SDK returns the onAudioRouteChanged callback to indicate the changes.
        mRtcEngine!!.setEnableSpeakerphone(view.isSelected())
    }

    // Tutorial Step 3
    @Throws(JSONException::class)
    fun onEncCallClicked(view: View?) {
        //   callDisconnect()
        gotoHome()
    }

    // Tutorial Step 1
    private fun initializeAgoraEngine() {
        mRtcEngine = try {
            RtcEngine.create(baseContext, getString(R.string.agora_app_id), mRtcEventHandler)
        } catch (e: Exception) {
            Log.e(LOG_TAG, Log.getStackTraceString(e))
            throw RuntimeException(
                """
    NEED TO check rtc sdk init fatal error
    ${Log.getStackTraceString(e)}
    """.trimIndent()
            )
        }
    }

    // Tutorial Step 2
    private fun joinChannel() {
        val intent = intent
//        mChannel = intent.getStringExtra("channelName")
//        receiverId = intent.getStringExtra("receiverId").toString()

        // requestId = intent.getStringExtra("requestId").toString()
        mRtcEngine?.setDefaultAudioRoutetoSpeakerphone(false)
        /*  try {
            mPeerUid = Integer.valueOf(intent.getStringExtra(KEY_CALLING_PEER));
        }
        catch (NumberFormatException e)
        {
            Toast.makeText(this, R.string.message_wrong_number, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }*/
        var accessToken = intent.getStringExtra("agoraToken")
        if (TextUtils.equals(accessToken, "") || TextUtils.equals(
                accessToken,
                "<#YOUR ACCESS TOKEN#>"
            )
        ) {
            accessToken = null
        }
        mRtcEngine!!.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION)
        mRtcEngine!!.joinChannel(accessToken, "Hello Cghaneel", "",  0)
        if (timer != null) {
            timer!!.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        val millisUntilFinished1 = timeNew / 1000
                        val seconds = millisUntilFinished1 % 60
                        val minutes = millisUntilFinished1 / 60 % 60
                        val hours = millisUntilFinished1 / 3600 % 24
                        tvCallTime!!.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                        timeNew += 1000
                    }
                }
            }, 0, 1000)
        }


    }


    // Tutorial Step 3
    private fun leaveChannel() {
        try {
            mRtcEngine!!.leaveChannel()

        } catch (e: Exception) {
            Log.e("exceptionEndCall",e.localizedMessage)
        }
    }

    // Tutorial Step 4
    private fun onRemoteUserLeft(uid: Int, reason: Int) {
        //   showLongToast(String.format(Locale.US, "user %d left %d", (uid & 0xFFFFFFFFL), reason));
        Log.e("leftUserId",uid.toString())
        try {
            mCounter!!.cancel()
        } catch (e: Exception) {
        }
        gotoHome()
//        if (uid.toString()==receiverId){
//            //finish()
//            gotoHome()
//        }
    }

    // Tutorial Step 6
    private fun onRemoteUserVoiceMuted(uid: Int, muted: Boolean) {
        /*  showLongToast(
              String.format(
                  Locale.US,
                  "user %d muted or unmuted %b",
                  uid and 0xFFFFFFFFL,
                  muted
              )
          )*/
    }
    override fun onResume() {
        super.onResume()
        socketManager?.unRegister(this)
        socketManager?.onRegister(this)
        mRtcEngine?.enableAudio()

        if (onPause){
            gotoHome()
            //  callDisconnect()
        }
    }
    fun gotoHome(){
        //  callDisconnect()
        val intent = Intent(this@VoiceChatViewActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    var onPause=false

    override fun onPause() {
        super.onPause()



    }

    override fun onRestart() {
        super.onRestart()
        Log.e("onrestart","true")
        onPause=true
    }


    override fun onResponseArray(event: String, args: JSONArray) {
        TODO("Not yet implemented")
    }

    override fun onResponse(event: String, args: JSONObject) {
        when (event) {

//            call_status_listener -> {
//                activityScope.launch {
//                    var data = args as JSONObject
//                    Log.e("callTermination", data.toString())
//
//                    val gson = GsonBuilder().create()
//                    val callData = gson.fromJson(data.toString(), CallConnectResponse::class.java)
//                    if (callData.status==CALL_DECLINE||callData.status==CALL_DESCONNECT) {
////                        val intent = Intent(this@VoiceChatViewActivity, MainActivity::class.java)
////                        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
////                        startActivity(intent)
////                        finish()
//                    }
//                }
//            }



        }

    }

    override fun onError(event: String, vararg args: Array<*>) {
        TODO("Not yet implemented")
    }

    companion object {
        private val LOG_TAG = VoiceChatViewActivity::class.java.simpleName
        private const val PERMISSION_REQ_ID_RECORD_AUDIO = 22
    }
}

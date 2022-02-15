package com.creation.nearby.base

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.creation.nearby.utils.Constants
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URISyntaxException

class SocketManager private constructor() {
    var isSocketConnected = false
    lateinit var mSocket: Socket
    private var mSocketInterface: SocketInterface? = null
    private var observerList: MutableList<SocketInterface>? = null
    private val TAG = SocketManager::class.java.canonicalName

    companion object {
        private var mSocketClass: SocketManager? = null

        @JvmStatic
        val socket: SocketManager?
            get() {
                if (mSocketClass == null) mSocketClass = SocketManager()
                return mSocketClass
            }


        //************************Listeners************************
        const val CONNECT_LISTENER = "connect_listner"
        const val CHAT_MESSAGE_LIST_LISTENER = "get_list"
        const val NEW_MESSAGE_LISTENER = "new_message"
        const val Rec_MESSAGE_LISTENER = "receive_message"
        const val MY_CHAT_LISTENER = "my_chat"
        const val READ_DATA_STATUS_LISTENER = "read_data_status"

        //*************************Emitters************************
        const val CONNECT_USER = "connect_user"
        const val GET_CHAT_LISTING_EMIT = "get_chat_list"
        const val SEND_MESSAGE_EMIT = "send_message"
        const val GET_CHAT_EMIT = "get_chat"
        const val READ_UNREAD_EMIT = "read_unread"

    }


    private val socketServer = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            val data = args[0] as JSONObject
            if (mSocketInterface != null) Log.e("$TAG response_socket", args.toString())
            mSocketInterface!!.onSocketCall(CONNECT_USER, *args)
            for (observer in observerList!!) {
                observer.onSocketCall(CONNECT_USER, *args)
            }
        }
    }


    private val connectListener = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            Log.e(TAG + "Connect Listener", args[0].toString())

            for (observer in observerList!!) {
                observer.onSocketCall(CONNECT_LISTENER, args)
            }
        }
    }


    private val chatList = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            Log.e(TAG + "chat_message", args[0].toString())

            for (observer in observerList!!) {
                observer.onSocketCall(CHAT_MESSAGE_LIST_LISTENER, args)
            }
        }
    }
    private val msgList = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            Log.e(TAG + "get_data_message", args[0].toString())

            for (observer in observerList!!) {
                observer.onSocketCall(MY_CHAT_LISTENER, args)
            }
        }
    }
    private val mgsReadUnread = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            Log.e(TAG + "get_read_unread_msg", args[0].toString())

            for (observer in observerList!!) {
                observer.onSocketCall(READ_DATA_STATUS_LISTENER, args)
            }
        }
    }

    private val sendMsg = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            Log.e(TAG + "new_message", args[0].toString())

            for (observer in observerList!!) {
                observer.onSocketCall(NEW_MESSAGE_LISTENER, args)
                observer.onSocketCall(Rec_MESSAGE_LISTENER, args)
            }
        }
    }


    /**
     * Default Listener
     * Define what you want to do when there's a connection error
     */
    private val onConnectError =
        Emitter.Listener { args ->
            // Get a handler that can be used to post to the main thread
            Handler(Looper.getMainLooper()).post {
                for (observer in observerList!!) {
                    observer.onError("ERROR", *args)
                }
                Log.e(TAG, "Run" + args[0])
                Log.e(TAG, "Error connecting")
            }
        }

    /**
     * Default Listener
     * Define what you want to do when connection is established
     */
    private val onConnect = Emitter.Listener { args ->
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            Log.e(TAG, "Emitting Connect User")
            isSocketConnected = true
            if (mSocketInterface != null) mSocketInterface!!.onSocketConnect(*args)
            try {
                val user_id =
                    PreferenceFile.retrieveLoginData(AppController.getInstance())?.body?.id.toString()
                if (user_id != "") {
                    val jsonObject = JSONObject()
                    jsonObject.put("userId", user_id)
                    sendDataToServer(CONNECT_USER, jsonObject)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            for (observer in observerList!!) {
                observer.onSocketConnect(*args)
            }
        }
    }


    private val onDisconnect = Emitter.Listener { args ->
        Handler(Looper.getMainLooper()).post {
            isSocketConnected = false
//            if (mSocketInterface != null)
//                mSocketInterface.onSocketDisconnect(args);
            for (observer in observerList!!) {
                observer.onSocketDisconnect(*args)
            }
        }
    }

    /* * Send Data to server by use of socket * */

    fun sendDataToServer(methodName: String, mObject: Any) {
        // Get a handler that can be used to post to the main thread
        Handler(Looper.getMainLooper()).post {
            mSocket.emit(methodName, mObject)
            Log.e("===emit==$methodName", mObject.toString())
        }
    }

    fun isConnected(): Boolean {
        return mSocket != null && mSocket.connected()
    }

    fun updateSocketInterface(mSocketInterface: SocketInterface?) {
        this.mSocketInterface = mSocketInterface
    }

    fun onRegister(observer: SocketInterface) {
        if (observerList != null && !observerList!!.contains(observer)) {
            observerList!!.add(observer)
        } else {
            observerList = ArrayList()
            (observerList as ArrayList<SocketInterface>).add(observer)
        }
    }

    fun unRegister(observer: SocketInterface) {
        if (observerList != null) {
            for (i in observerList!!.indices) {
                val model = observerList!![i]
                if (model === observer) {
                    observerList!!.remove(model)
                }
            }
        }
    }

    fun onConnect() {
        if (!mSocket.connected()) {
            Log.e(TAG, "Connecting Sockets")
            mSocket.on(Socket.EVENT_CONNECT, onConnect)
            mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect)
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
//            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
            mSocket.on(CONNECT_LISTENER, connectListener)
            mSocket.on(CHAT_MESSAGE_LIST_LISTENER, chatList)
            mSocket.on(NEW_MESSAGE_LISTENER, sendMsg)
            mSocket.on(Rec_MESSAGE_LISTENER, sendMsg)
            mSocket.on(MY_CHAT_LISTENER, msgList)
            mSocket.on(READ_DATA_STATUS_LISTENER, mgsReadUnread)
            mSocket.connect()
        } else {
            Log.e(TAG, "Connecting Sockets Error!")
        }
    }

    fun onDisconnect() {
        Log.e(TAG, "Disconnecting Sockets")
        isSocketConnected = false
        mSocket.disconnect()
        mSocket.off(Socket.EVENT_CONNECT, onConnect)
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
//        mSocket.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        mSocket.off(CONNECT_LISTENER, connectListener)
        mSocket.off(CHAT_MESSAGE_LIST_LISTENER, chatList)
        mSocket.off(NEW_MESSAGE_LISTENER, sendMsg)
        mSocket.off(Rec_MESSAGE_LISTENER, sendMsg)
        mSocket.off(MY_CHAT_LISTENER, msgList)
        mSocket.off(READ_DATA_STATUS_LISTENER, mgsReadUnread)
    }

    /*
     * Interface for Socket Callbacks
     * */
    interface SocketInterface {
        fun onSocketCall(event: String?, vararg args: Any?)
        fun onSocketConnect(vararg args: Any?)
        fun onSocketDisconnect(vararg args: Any?)
        fun onError(event: String?, vararg args: Any?)
    }

    init {
        try {
            val options = IO.Options()
            options.reconnection = true
            options.reconnectionAttempts = Int.MAX_VALUE
            mSocket = IO.socket(Constants.BASEURL, options)
            if (observerList == null || observerList!!.size == 0) {
                observerList = ArrayList()
            }
            onDisconnect()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }
}
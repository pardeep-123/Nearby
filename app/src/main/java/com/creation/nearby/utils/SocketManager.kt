package com.creation.nearby.utils

import android.util.Log
import com.creation.nearby.base.PreferenceFile

import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URISyntaxException
import java.util.*

class SocketManager {
    // sockets listener
    private val errorMessage = "error_message"

    companion object {
        //for chat
        //emitter
        // sockets events
        private const val connectUser = "connect_user"
        const val get_chat_list = "get_chat_list"
        const val get_chat = "get_chat"
        const val send_message = "send_message"
        const val read_unread_emitter = "read_unread"

        const val call_socket_disconnect_emitter = "socket_disconnect"

        /*listener*/
        const val send_message_listener = "send_message_listner"
        private const val connect_listener = "connect_listener"
        private const val get_list = "get_list"
        const val my_chat = "my_chat"
        const val read_unread_listener = "read_unreadlistner"
        const val call_socket_disconnect_listener = "socket_disconnectlistner"

    }

    private var mSocket: Socket? = null
    private var observerList: MutableList<Observer>? = null

    fun getmSocket(): Socket? {
        return mSocket
    }

    // use for get SOCKET_URL
    private fun getSocket(): Socket? {
        run {
            try {
                mSocket = IO.socket(Constants.SOCKET_URL)
            } catch (e: URISyntaxException) {
                throw RuntimeException(e)
            }
        }
        return mSocket
    }

    fun onRegister(observer: Observer) {
        if (observerList != null && !observerList!!.contains(observer)) {
            observerList!!.clear()
            observerList!!.add(observer)
        } else {
            observerList = ArrayList()
            observerList!!.clear()
            observerList!!.add(observer)
        }
    }

    fun unRegister(observer: Observer) {
        observerList?.let { list ->
            for (i in 0 until list.size - 1) {
                val model = list[i]
                if (model === observer) {
                    observerList?.remove(model)
                }
            }
        }
    }


    fun init() {
        initializeSocket()
    }

    private fun initializeSocket() {
        if (mSocket == null) {
            mSocket = getSocket()
        }
        if (observerList == null || observerList!!.size == 0) {
            observerList = ArrayList()
        }

        disconnect()
        mSocket!!.connect()
        mSocket!!.on(Socket.EVENT_CONNECT, onConnect)
        // mSocket!!.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket!!.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        // mSocket!!.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
        mSocket!!.on(errorMessage, onErrorMessage)

       /* mSocket!!.off(deliveryStatusListener)
        mSocket!!.on(deliveryStatusListener, onGettingMessageUpdate)*/

    }

    private fun disconnect() {
        if (mSocket != null) {
            // mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
            mSocket!!.off(Socket.EVENT_CONNECT, onConnect)
            mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
            mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
            // mSocket!!.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
            mSocket!!.off()
            mSocket!!.disconnect()
        }
    }

    fun disconnectAll() {
        try {
            if (mSocket != null) {
                // mSocket!!.emit(Socket.EVENT_DISCONNECT, onDisconnect)
                mSocket!!.off(Socket.EVENT_CONNECT, onConnect)
                mSocket!!.off(Socket.EVENT_DISCONNECT, onDisconnect)
                mSocket!!.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
                //mSocket!!.off(Socket.EVENT_CONNECT_TIMEOUT, onConnectError)
                mSocket!!.off()
                mSocket!!.disconnect()
            }
        } catch (e: Exception) {
        }
    }


    private val onConnect = Emitter.Listener {
        if (isConnected()) {
            try {
                val jsonObject = JSONObject()

                    val userid = PreferenceFile.retrieveUserId()
                    if (userid != "0") {
                        jsonObject.put("userId", userid)
                        mSocket!!.off(connect_listener, onConnectListener)
                        mSocket!!.on(connect_listener, onConnectListener)
                        mSocket!!.emit(connectUser, jsonObject)

                    }


            } catch (e: JSONException) {
                e.printStackTrace()
            }
        } else {
            initializeSocket()
        }
    }


    fun isConnected(): Boolean {
        return mSocket != null && mSocket!!.connected()
    }

    private val onConnectListener = Emitter.Listener { args ->
        try {

            Log.e("Socket", "Connected" + args[0].toString())

            Log.e("Socket", "Connected" + args[0].toString())

            // val data = args[1] as JSONObject

            // val data = args[1] as JSONObject
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    private val onDisconnect = Emitter.Listener { args ->
        try {
            Log.e("Socket", "DISCONNECTED :::$args")
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    private val onConnectError = Emitter.Listener { args ->
        try {
            Log.e("Socket", "CONNECTION ERROR :::$args")
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    private val onErrorMessage = Emitter.Listener { args ->
        for (observer in observerList!!) {
            try {
                val data = args[0] as JSONObject
                Log.e("Socket", "Error Message :::$data")
                observer.onError(connectUser, args)
            } catch (ex: Exception) {
                ex.localizedMessage
            }
        }
    }


    fun readUnReadMessage(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()
                    mSocket!!.off(read_unread_listener)
                    mSocket!!.on(read_unread_listener, onReadDataStatus)
                    mSocket!!.emit(read_unread_emitter, jsonObject)
                } else {
                    mSocket!!.off(read_unread_listener)
                    mSocket!!.on(read_unread_listener, onReadDataStatus)
                    mSocket!!.emit(read_unread_emitter, jsonObject)
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }

            Log.i("Socket", "Send Message Called")
        }
    }

    private val onReadDataStatus = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            Log.e("Socket", "read_unread :::$data")
            for (observer in observerList!!) {
                observer.onResponse(read_unread_emitter, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    fun getFriendChat(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()
                    mSocket!!.off(my_chat)
                    mSocket!!.on(my_chat, onGetFriendChat)
                    mSocket!!.emit(get_chat, jsonObject)
                } else {
                    mSocket!!.off(my_chat)
                    mSocket!!.on(my_chat, onGetFriendChat)
                    mSocket!!.emit(get_chat, jsonObject)
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }

            Log.i("Socket", "Send Message Called")
        }
    }

    private val onGetFriendChat = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONArray
            Log.e("Socket", "onGetFriendChat :::$data")
            for (observer in observerList!!) {
                observer.onResponseArray(get_chat, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    fun sendMessageForChat(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()
                    mSocket!!.off(send_message_listener)
                    mSocket!!.on(send_message_listener, onSendMessageListenerForChat)
                    mSocket!!.emit(send_message, jsonObject)
                } else {
                    mSocket!!.off(send_message_listener)
                    mSocket!!.on(send_message_listener, onSendMessageListenerForChat)
                    mSocket!!.emit(send_message, jsonObject)
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }

            Log.i("Socket", "Send Message Called")
        }
    }

    private val onSendMessageListenerForChat = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            Log.e("Socket", "sendMessage :::$data")
            for (observer in observerList!!) {
                observer.onResponse(send_message, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    fun getMessageList(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.connect()
                    mSocket!!.off(get_list)
                    mSocket!!.on(get_list, chatListListener)
                    mSocket!!.emit(get_chat_list, jsonObject)
                } else {
                    mSocket!!.off(get_list)
                    mSocket!!.on(get_list, chatListListener)
                    mSocket!!.emit(get_chat_list, jsonObject)
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }

            Log.i("Socket", "Send Message Called")
        }
    }

    fun receiveMsgListener() {
        try {
            if (!mSocket!!.connected()) {
                mSocket!!.connect()
                mSocket!!.off(send_message_listener)
                mSocket!!.on(send_message_listener, onReceiverMsgListener)

            } else {
                mSocket!!.off(send_message_listener)
                mSocket!!.on(send_message_listener, onReceiverMsgListener)


            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }

    }



    private val chatListListener = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONArray
            Log.e("Socket", "chatList :::$data")
            for (observer in observerList!!) {
                observer.onResponseArray(get_chat_list, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }


    private val onReceiverMsgListener = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            Log.d("SocketListener", "CallStatusList :::$data")
            for (observer in observerList!!) {
                observer.onResponse(send_message_listener, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }

    }


    fun socketDisconnect(jsonObject: JSONObject?) {
        if (jsonObject != null) {
            try {
                if (!mSocket!!.connected()) {
                    mSocket!!.off(call_socket_disconnect_listener)
                    mSocket!!.on(call_socket_disconnect_listener, callSocketDisconnectListener)
                    mSocket!!.emit(call_socket_disconnect_emitter, jsonObject)
                } else {
                    mSocket!!.off(call_socket_disconnect_listener)
                    mSocket!!.on(call_socket_disconnect_listener, callSocketDisconnectListener)
                    mSocket!!.emit(call_socket_disconnect_emitter, jsonObject)
                }
            } catch (ex: Exception) {
                ex.localizedMessage
            }
        }
    }

    private val callSocketDisconnectListener = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            Log.e("Socket", "SocketDisconnectList :::$data")
            for (observer in observerList!!) {
                observer.onResponse(call_socket_disconnect_emitter, data)
            }
        } catch (ex: Exception) {
            ex.localizedMessage
        }
    }

    interface Observer {
        fun onResponseArray(event: String, args: JSONArray)
        fun onResponse(event: String, args: JSONObject)
        fun onError(event: String, vararg args: Array<*>)
    }

}
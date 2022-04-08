package com.creation.nearby.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.ChatsAdapter
import com.creation.nearby.adapter.OnlineUserChatAdapter
import com.creation.nearby.adapter.RecyclerAdapter
import com.creation.nearby.base.AppController
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.ActivityChatBinding
import com.creation.nearby.model.ChatListModel
import com.creation.nearby.model.ChatModel
import com.creation.nearby.model.FriendListModel
import com.creation.nearby.model.OnlineUserChatModel
import com.creation.nearby.utils.SocketManager
import com.creation.nearby.viewmodel.MessageListVM
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class ChatActivity : AppCompatActivity(), SocketManager.Observer {

    private lateinit var binding: ActivityChatBinding

    private val messageListVM: MessageListVM by viewModels()

    private var socketManager: SocketManager? = null

    private var chatAdapter : ChatsAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        binding.chatVM = messageListVM
        binding.lifecycleOwner = this
        setContentView(binding.root)

        socketManager = AppController.getSocketManager()
        socketManager?.init()


        binding.onlineChatsRecyView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL, false
        )

        binding.chatsRecView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )

        CoroutineScope(Dispatchers.Main).launch {
            messageListVM.getFriendApi(this@ChatActivity)
        }

        CoroutineScope(Dispatchers.Main).launch {
            getMessageList()
        }


//        chatList.add(ChatModel(R.drawable.chat_pic_1,"Juliana Watson","Typing...","30s ago",true))
//        chatList.add(ChatModel(R.drawable.chat_pic_9,"Fotball Lovers","Active Group Call","Currently Ongoing",false))
//        chatList.add(ChatModel(R.drawable.chat_pic_8,"Sin Juni","Great work","1 hour ago",false))
//        chatList.add(ChatModel(R.drawable.chat_pic_7,"Jenny Wilson","\uD83E\uDD23","07:00",false))
//        chatList.add(ChatModel(R.drawable.chat_pic_4,"Alexander Lorse","See you there!","3 days ago",true))
//        chatList.add(ChatModel(R.drawable.chat_pic_6,"Elena Smith","Not quite sure?","3 days ago",false))

//

        onClickHandler()

    }

    private fun onClickHandler() {

        binding.backiv.setOnClickListener {
            onBackPressed()
        }
        binding.chatIv.setOnClickListener {
            startActivity(Intent(this, OngoingChatActivity::class.java))
        }
        binding.startRandomChat.setOnClickListener {
            startActivity(Intent(this, OngoingChatActivity::class.java))
        }

    }

    fun getMessageList() {
//        socketManager?.unRegister(this)
//        socketManager?.onRegister(this)
        socketManager?.getMessageList(getParamAsJson())
    }

    // method for getting chat list
    private fun getParamAsJson(): JSONObject? {
        val jsonObject = JSONObject()
        jsonObject.put("userid", PreferenceFile.retrieveUserId())
        return try {
            jsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
            null
        }
    }

    override fun onResume() {
        super.onResume()
        socketManager?.unRegister(this)
        socketManager?.onRegister(this)
    }

    override fun onResponse(event: String, args: JSONObject) {

    }

    override fun onResponseArray(event: String, args: JSONArray) {

        val gson = GsonBuilder().create()
        val list = gson.fromJson(
            args.toString(),
            Array<ChatListModel.ChatListModelItem>::class.java
        ).toList()
        chatAdapter = ChatsAdapter(list)
        binding.chatsRecView.adapter = chatAdapter
        chatAdapter?.notifyDataSetChanged()
    }

    override fun onError(event: String, vararg args: Array<*>) {

    }
}
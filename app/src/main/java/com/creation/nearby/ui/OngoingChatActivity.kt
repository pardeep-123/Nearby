package com.creation.nearby.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creation.nearby.adapter.OnlineUserChatAdapter
import com.creation.nearby.adapter.SuggestionAdapter
import com.creation.nearby.base.AppController
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.ActivityOngoingChatBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.OneToOneChatListModel
import com.creation.nearby.model.SuggestionsModel
import com.creation.nearby.showToast
import com.creation.nearby.ui.call.VideoCallActivity
import com.creation.nearby.ui.call.VoiceChatViewActivity
import com.creation.nearby.utils.AppUtils
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.SocketManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class OngoingChatActivity : AppCompatActivity(), View.OnClickListener, SocketManager.Observer {
    private lateinit var binding: ActivityOngoingChatBinding

    private lateinit var suggestionAdapter: SuggestionAdapter
    private var suggestionList = ArrayList<SuggestionsModel>()
    private var socketManager: SocketManager? = null

    private var user2Id = ""
    private var adapter : OnlineUserChatAdapter?=null
    var linearLayoutManager:LinearLayoutManager?=null
    private var chatList : ArrayList<OneToOneChatListModel.OneToOneChatListModelItem> = ArrayList()
    private var activityScope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOngoingChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * set name and image from previous screen
         */
        binding.tvUserName.text = intent.getStringExtra("name")
        Glide.with(this).load(Constants.IMAGE_BASE_URL + intent.getStringExtra("image"))
            .into(binding.circleImageView)
        user2Id = intent?.getStringExtra("user2Id")!!
        AppUtils.hideKeyboard(this)
        binding.suggestionRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
        setAdapter()
        socketManager = AppController.getSocketManager()
        socketManager?.init()
//        suggestionList.add(SuggestionsModel("Hi \uD83D\uDE4C"))
//        suggestionList.add(SuggestionsModel("Wyd"))
//        suggestionList.add(SuggestionsModel("How was your day?"))

        initAdapter()

        binding.goback.setOnClickListener(this)
        binding.sendMessageBtn.setOnClickListener(this)
        binding.phoneIv.setOnClickListener(this)
        binding.videoIv.setOnClickListener(this)


    }

    override fun onResume() {
        super.onResume()
        socketManager?.unRegister(this)
        socketManager?.onRegister(this)

        socketManager?.getFriendChat(getOneToOneChatList())
    }

    private fun setAdapter() {

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = OnlineUserChatAdapter(chatList)
        binding.chatRecyclerView.layoutManager = linearLayoutManager
        binding.chatRecyclerView.adapter = adapter
    }

    /**
     * function for get one to one chat
     */
    private fun getOneToOneChatList() : JSONObject{
        val jsonObject = JSONObject()
        jsonObject.put("userid",PreferenceFile.retrieveUserId())
        jsonObject.put("user2id",user2Id)
        return jsonObject
    }
    private fun initAdapter() {

        val onActionListener = object : OnActionListener<SuggestionsModel> {
            override fun notify(model: SuggestionsModel, position: Int, view: View) {

                binding.sendMessageEdiText.setText(model.suggestion)

            }
        }

        suggestionAdapter = SuggestionAdapter(suggestionList, onActionListener)
        binding.suggestionRecyclerView.adapter = suggestionAdapter
        suggestionAdapter.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        when (p0) {

            binding.goback -> {
                onBackPressed()
            }
            binding.sendMessageBtn -> {
//                binding.emptyLayout.visibility = View.GONE
                if (binding.sendMessageEdiText.text.toString().isEmpty()) {
                    showToast("Enter Message")
                } else
                    sendChatList()
            }
            binding.phoneIv -> {
                val intent = Intent(this, VoiceChatViewActivity::class.java)
                intent.putExtra("name",intent.getStringExtra("name"))
                intent.putExtra("image",intent.getStringExtra("image"))
                startActivity(intent)
            }
            binding.videoIv -> {
                startActivity(Intent(this, VideoCallActivity::class.java))
            }

        }
    }

    /**
     * set socket method
     * "{
    ""userid"":208,
    ""user2id"":209,
    ""messageType"":0,
    ""message"":""hiii""
    }"
     */
    private fun sendChatList() {

        socketManager?.sendMessageForChat(sendMessageAsJson())
        binding.sendMessageEdiText.text.clear()
    }

    private fun sendMessageAsJson(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("userid", PreferenceFile.retrieveUserId())
        jsonObject.put("user2id", user2Id)
        jsonObject.put("messageType", "0")
        jsonObject.put("message", binding.sendMessageEdiText.text.toString().trim())
        return jsonObject
    }

    override fun onResponseArray(event: String, args: JSONArray) {
        Log.d("event",args.toString())
        activityScope.launch {
        if (event == "get_chat") {
            val gson = GsonBuilder().create()
            val list = gson.fromJson(
                args.toString(),
                Array<OneToOneChatListModel.OneToOneChatListModelItem>::class.java
            )
                .toList()
            chatList.addAll(list)
            adapter?.notifyDataSetChanged()
            scrollToBottom()
        }
        }
    }

    override fun onResponse(event: String, args: JSONObject) {
        Log.d("event",args.toString())
        activityScope.launch {
            val gson = GsonBuilder().create()
            val list = gson.fromJson(
                args.toString(),
                OneToOneChatListModel.OneToOneChatListModelItem::class.java
            )
            chatList.add(list)
            adapter?.notifyDataSetChanged()
            binding.chatRecyclerView.scrollToPosition(chatList.size - 1)

        }
    }

    override fun onError(event: String, vararg args: Array<*>) {

    }

    fun scrollToBottom(){
        linearLayoutManager?.scrollToPosition(chatList.size-1)
    }
}
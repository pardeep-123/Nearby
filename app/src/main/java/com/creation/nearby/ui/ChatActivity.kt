package com.creation.nearby.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.ChatsAdapter
import com.creation.nearby.adapter.OnlineUserChatAdapter
import com.creation.nearby.databinding.ActivityChatBinding
import com.creation.nearby.model.ChatModel
import com.creation.nearby.model.OnlineUserChatModel

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    
    private var onlineChatList = ArrayList<OnlineUserChatModel>()
    private lateinit var onlineChatAdapter: OnlineUserChatAdapter


    private var chatList = ArrayList<ChatModel>()
    private lateinit var chatAdapter: ChatsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.onlineChatsRecyView.layoutManager = LinearLayoutManager(this,
            RecyclerView.HORIZONTAL,false)

        onlineChatList.add(OnlineUserChatModel(R.drawable.chat_pic_1))
        onlineChatList.add(OnlineUserChatModel(R.drawable.chat_pic_2,))
        onlineChatList.add(OnlineUserChatModel(R.drawable.chat_pic_3,))
        onlineChatList.add(OnlineUserChatModel(R.drawable.chat_pic_4,))
        onlineChatList.add(OnlineUserChatModel(R.drawable.chat_pic_5,))

        onlineChatAdapter = OnlineUserChatAdapter(onlineChatList)
        binding.onlineChatsRecyView.adapter = onlineChatAdapter
        onlineChatAdapter.notifyDataSetChanged()



        binding.chatsRecView.layoutManager = LinearLayoutManager(this,
            RecyclerView.VERTICAL,false)

        chatList.add(ChatModel(R.drawable.chat_pic_1,"Juliana Watson","Typing...","30s ago",true))
        chatList.add(ChatModel(R.drawable.chat_pic_9,"Fotball Lovers","Active Group Call","Currently Ongoing",false))
        chatList.add(ChatModel(R.drawable.chat_pic_8,"Sin Juni","Great work","1 hour ago",false))
        chatList.add(ChatModel(R.drawable.chat_pic_7,"Jenny Wilson","\uD83E\uDD23","07:00",false))
        chatList.add(ChatModel(R.drawable.chat_pic_4,"Alexander Lorse","See you there!","3 days ago",true))
        chatList.add(ChatModel(R.drawable.chat_pic_6,"Elena Smith","Not quite sure?","3 days ago",false))

        chatAdapter = ChatsAdapter(chatList)
        binding.chatsRecView.adapter = chatAdapter
        chatAdapter.notifyDataSetChanged()

        onClickHandler()

    }

    private fun onClickHandler() {

        binding.backiv.setOnClickListener{
            onBackPressed()
        }
        binding.chatIv.setOnClickListener{
            startActivity(Intent(this,OngoingChatActivity::class.java))
        }
        binding.startRandomChat.setOnClickListener{
            startActivity(Intent(this,OngoingChatActivity::class.java))
        }

    }
}
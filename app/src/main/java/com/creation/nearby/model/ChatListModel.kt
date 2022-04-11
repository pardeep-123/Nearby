package com.creation.nearby.model

import com.creation.nearby.adapter.AbstractModel

class ChatListModel : ArrayList<ChatListModel.ChatListModelItem>() {

    data class ChatListModelItem(
        val id: Int,
        val userid: Int,
        val user2id: Int,
        val ReceiverType: Int,
        val blockstatus: Int,
        val created: Int,
        val createdAt: String,
        val deletedId: Int,
        val lastMessage: String,
        val lastMsgId: Int,
        val msg_type: Int,
        val onlinestatus: Int,
        val role: String,
        val senderType: Int,
        val unreadcount: Int,
        val updatedAt: String,
        val user_id: Int,
        val userImage: String,
        val FirstName: String,
        val LastName: String
        /*
        "[
    {

        ""last_msg_id"": 6,
        ""deleted_id"": 0,
        ""created_at"": ""2022-04-08T07:38:14.000Z"",
        ""updated_at"": ""2022-04-08T07:47:41.000Z"",
        ""user_id"": 209,
        ""lastMessage"": ""here"",
        ""userName"": """",
        ""userRole"": 1,
        ""onlinestatus"": 1,
        ""userImage"": ""/public/uploads/user/1648030418083-file.jpeg"",
        ""created_att"": 1649404061,
        ""msg_type"": 0,
        ""unreadcount"": 0,
        ""noificationCount"": 0
    }
]"

  [{"id":6,
  "userid":236,
  "user2id":209,
  "last_msg_id":36,
  "deleted_id":0,
  "created_at":"2022-04-11T08:13:58.000Z",
  "updated_at":"2022-04-11T09:22:53.000Z",
  "user_id":209,"lastMessage":"yes",
  "FirstName":"Test","LastName":"IOS",
  "userRole":1,"onlinestatus":1,
  "userImage":"\/public\/uploads\/user\/1648030418083-file.jpeg",
  "created_att":1649668974,
  "msg_type":0,
  "unreadcount":0,
  "noificationCount":1}]


         */
    ) : AbstractModel()
}
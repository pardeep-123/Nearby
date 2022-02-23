package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.EventsAdapter
import com.creation.nearby.adapter.FriendsAdapter
import com.creation.nearby.databinding.ActivitySearchBinding
import com.creation.nearby.model.EventsModel
import com.creation.nearby.model.FriendsModel

class SearchActivity : AppCompatActivity(), TextWatcher {
    private lateinit var binding: ActivitySearchBinding

    private lateinit var friendsAdapter: FriendsAdapter
    private var friendsList = ArrayList<FriendsModel>()

    lateinit var eventsAdapter: EventsAdapter
    private var eventsList = ArrayList<EventsModel>()

    private var isUser = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchEditext.addTextChangedListener(this)



        clickHandler()

    }

    private fun clickHandler() {
        binding.searchUser.setOnClickListener{

            isUser = true
            binding.searchUser.backgroundTintList =
                ActivityCompat.getColorStateList(this, R.color.sky_blue)
            binding.searchEvent.backgroundTintList =
                ActivityCompat.getColorStateList(this, R.color.edittext_grey)

            friendsList.clear()
            eventsList.clear()
            userAdapter()


            if(binding.searchEditext.text.isNotEmpty()){
                userList()
            }



        }
        binding.searchEvent.setOnClickListener{

            isUser = false
            binding.searchUser.backgroundTintList =
                ActivityCompat.getColorStateList(this, R.color.edittext_grey)
            binding.searchEvent.backgroundTintList =
                ActivityCompat.getColorStateList(this, R.color.sky_blue)

            friendsList.clear()
            eventsList.clear()
            eventsAdapter()

            if(binding.searchEditext.text.isNotEmpty()){
                eventList()
            }


        }
        binding.goBack.setOnClickListener{
            onBackPressed()
        }
    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        friendsList.clear()
        eventsList.clear()
        userAdapter()
        eventsAdapter()

        if (p0 != null) {
            if (p0.isNotEmpty())

                if (isUser) {

                    userList()
                    userAdapter()

                } else {

                    eventList()
                    eventsAdapter()
                }
        }


    }

    override fun afterTextChanged(p0: Editable?) {

    }

    private fun userList(){

        friendsList.clear()
        friendsList.add(FriendsModel(R.drawable.friends_pic_7, "Soham, 18", false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_8, "Shawn, 19", false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_9, "Dianne, 17", false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_10, "Jenna, 17", false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_11, "Ronald, 17", false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_12, "Arthur, 16", false))

        friendsList.add(FriendsModel(R.drawable.friends_pic_7, "Soham, 18", false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_8, "Shawn, 19", false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_9, "Dianne, 17", false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_10, "Jenna, 17", false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_11, "Ronald, 17", false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_12, "Arthur, 16", false))

    }


    private fun eventList(){


        eventsList.clear()
        eventsList.add(EventsModel(R.drawable.image, "Let’s Co-work", "5km away from you."))
        eventsList.add(EventsModel(R.drawable.image_1, "Coffee Break", "300m away from you."))
        eventsList.add(EventsModel(R.drawable.image_2, "Let’s Co-work", "5km away from you."))


    }


    private fun userAdapter(){

        binding.searchRecyView.layoutManager = GridLayoutManager(
            this, 3,
            RecyclerView.VERTICAL, false
        )

        friendsAdapter = FriendsAdapter(friendsList)
        binding.searchRecyView.adapter = friendsAdapter
        friendsAdapter.notifyDataSetChanged()


    }

    private fun eventsAdapter(){
        binding.searchRecyView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

       // eventsAdapter = EventsAdapter(eventsList)
        binding.searchRecyView.adapter = eventsAdapter
        eventsAdapter.notifyDataSetChanged()

    }

}
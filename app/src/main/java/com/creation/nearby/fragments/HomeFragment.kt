package com.creation.nearby.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.DiscoverAdapter
import com.creation.nearby.adapter.FeedAdapter
import com.creation.nearby.adapter.NotificationAdapter
import com.creation.nearby.databinding.FragmentHomeBinding
import com.creation.nearby.model.DiscoverModel
import com.creation.nearby.model.NotificationModel

class HomeFragment : Fragment() {


    private lateinit var discoverAdapter: DiscoverAdapter
    private lateinit var feedAdapter: FeedAdapter

    companion object{
        lateinit var notificationAdapter: NotificationAdapter
    }

    @SuppressLint("NotifyDataSetChanged")

    lateinit var binding: FragmentHomeBinding

    private var discoverList = ArrayList<DiscoverModel>()
    private var feedList = ArrayList<DiscoverModel>()
    private var notificationList = ArrayList<NotificationModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // discover recycler view

        binding.discoverRecyclerView.layoutManager = LinearLayoutManager(view.context,
            RecyclerView.HORIZONTAL,false)

        discoverList.add(DiscoverModel("300m away","Starbucks","Let’s meet and talk."))
        discoverList.add(DiscoverModel("1km away","Basketball","We are up to play...."))

        discoverAdapter = DiscoverAdapter(discoverList)
        binding.discoverRecyclerView.adapter = discoverAdapter
        discoverAdapter.notifyDataSetChanged()

        // discover recycler view


        //feed recycler view

        binding.feedRecView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL,false)

        feedList.add(DiscoverModel("2min ago","Coffee?","Let’s meet and talk."))
        feedList.add(DiscoverModel("3min ago","New here","We are up to play..."))

        feedAdapter = FeedAdapter(feedList)
        binding.feedRecView.adapter = feedAdapter
        feedAdapter.notifyDataSetChanged()

        //feed recycler view

        //notification recycler view

        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL,false)

        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Brooklyn Simmons","You are friends! \uD83C\uDF89","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",isHomeFragment = true))

        notificationAdapter = NotificationAdapter(notificationList)
        binding.notificationRecyclerView.adapter = notificationAdapter
        notificationAdapter.notifyDataSetChanged()

        //notification recycler view


    }
}
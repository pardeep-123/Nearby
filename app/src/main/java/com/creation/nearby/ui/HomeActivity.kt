package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.ActivityAdapter
import com.creation.nearby.adapter.DiscoverAdapter
import com.creation.nearby.model.ActivitiesModel
import com.creation.nearby.model.DiscoverModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    lateinit var adapter: ActivityAdapter
    lateinit var discoverAdapter: DiscoverAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        section_recycler_view.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)

        val list = ArrayList<ActivitiesModel>()

        list.add(ActivitiesModel(R.drawable.user_icon,"Home"))
        list.add(ActivitiesModel(R.drawable.user_icon,"Swipe"))
        list.add(ActivitiesModel(R.drawable.friends_icon,"Friends"))
        list.add(ActivitiesModel(R.drawable.map_icon,"Map"))
        list.add(ActivitiesModel(R.drawable.event_icon,"Events"))

        adapter = ActivityAdapter(list)
        section_recycler_view.adapter = adapter
        adapter.notifyDataSetChanged()


        discover_recycler_view.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)

        val discoverList = ArrayList<DiscoverModel>()

        discoverList.add(DiscoverModel("300m away","Starbucks","Let’s meet and talk."))
        discoverList.add(DiscoverModel("1km away","Basketball","We are up to play...."))

        discoverAdapter = DiscoverAdapter(discoverList)
        discover_recycler_view.adapter = discoverAdapter
        discoverAdapter.notifyDataSetChanged()


    }
}
package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.creation.nearby.R
import com.creation.nearby.adapter.ActivityAdapter
import com.creation.nearby.databinding.ActivityMainBinding
import com.creation.nearby.fragments.HomeFragment
import com.creation.nearby.model.ActivitiesModel


class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ActivityAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.sectionRecyclerView.layoutManager = LinearLayoutManager(this,
            RecyclerView.HORIZONTAL,false)

        val list = ArrayList<ActivitiesModel>()

        list.add(ActivitiesModel(R.drawable.user_icon,"Home"))
        list.add(ActivitiesModel(R.drawable.user_icon,"Swipe"))
        list.add(ActivitiesModel(R.drawable.friends_icon,"Friends"))
        list.add(ActivitiesModel(R.drawable.map_icon,"Map"))
        list.add(ActivitiesModel(R.drawable.event_icon,"Events"))

        adapter = ActivityAdapter(list)
        binding.sectionRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()


        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.selection_frame_layout,HomeFragment())
        fragment.commit()


    }
}
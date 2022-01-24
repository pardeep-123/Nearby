package com.creation.nearby.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.ActivityAdapter
import com.creation.nearby.databinding.ActivityMainBinding
import com.creation.nearby.fragments.HomeFragment
import com.creation.nearby.model.ActivitiesModel


class MainActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var adapter: ActivityAdapter
    private lateinit var binding: ActivityMainBinding
    private var list = ArrayList<ActivitiesModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.sectionRecyclerView.layoutManager = LinearLayoutManager(this,
            RecyclerView.HORIZONTAL,false)

        list.add(ActivitiesModel(R.drawable.user_icon,"Home"))
        list.add(ActivitiesModel(R.drawable.user_icon,"Swipe"))
        list.add(ActivitiesModel(R.drawable.map_icon,"Map"))
        list.add(ActivitiesModel(R.drawable.event_icon,"Events"))
        list.add(ActivitiesModel(R.drawable.event_icon,"Feed"))
        list.add(ActivitiesModel(R.drawable.feed_icon,"Friends"))

        adapter = ActivityAdapter(list)
        binding.sectionRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()


        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.selection_frame_layout,HomeFragment())
        fragment.commit()


        binding.chatImageView.setOnClickListener(this)
        binding.settingsImageView.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when(p0){

            binding.chatImageView->{

                startActivity(Intent(this,ChatActivity::class.java))

            }
            binding.settingsImageView->{

            startActivity(Intent(this,SideBarMenuActivity::class.java))

        }

        }
    }
}
package com.creation.nearby.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.ActivityAdapter
import com.creation.nearby.databinding.ActivityMainBinding
import com.creation.nearby.databinding.FilterBottomSheetDialogBinding
import com.creation.nearby.fragments.*
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.ActivitiesModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var adapter: ActivityAdapter
    private lateinit var binding: ActivityMainBinding
    private var list = ArrayList<ActivitiesModel>()

    lateinit var dialogBinding: FilterBottomSheetDialogBinding
    lateinit var filterDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filterDialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        dialogBinding = FilterBottomSheetDialogBinding.inflate(layoutInflater, null, false)
        filterDialog.setContentView(dialogBinding.root)
        filterDialog.setCancelable(true)
        filterDialog.setCanceledOnTouchOutside(true)

        binding.sectionRecyclerView.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL, false
        )

        list.add(ActivitiesModel(R.drawable.home, "Home", isChecked = true))
        list.add(ActivitiesModel(R.drawable.user_icon, "Swipe", isChecked = false))
        list.add(ActivitiesModel(R.drawable.map_icon, "Map", isChecked = false))
        list.add(ActivitiesModel(R.drawable.event_icon, "Events", isChecked = false))
        list.add(ActivitiesModel(R.drawable.feed_icon, "Feed", isChecked = false))
        list.add(ActivitiesModel(R.drawable.both_icon, "Friends", isChecked = false))

        initAdapter()

        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.selection_frame_layout, HomeFragment())
        fragment.commit()

        binding.chatImageView.setOnClickListener(this)
        binding.settingsImageView.setOnClickListener(this)
        binding.userProfileIv.setOnClickListener(this)
        binding.filterImageView.setOnClickListener(this)

        dialogBinding.closeDialog.setOnClickListener(this)

    }


    private fun initAdapter() {

        val onActionListener = object : OnActionListener<ActivitiesModel> {
            override fun notify(model: ActivitiesModel, position: Int, view: View) {

                val activityPic: ImageView = view.findViewById(R.id.activity_pic)
                val activityName: TextView = view.findViewById(R.id.activity_name)
                val mapLayout: FrameLayout = findViewById(R.id.complete_frame_layout)
                val mainLayout: FrameLayout = findViewById(R.id.selection_frame_layout)

                if (position == 0) {

                    mapLayout.visibility = View.GONE
                    mainLayout.visibility=View.VISIBLE
                    activityPic.imageTintList = resources.getColorStateList(R.color.blue)
                    activityName.setTextColor(resources.getColorStateList(R.color.blue))
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.selection_frame_layout, HomeFragment())
                    fragment.commit()
                }
                if (position == 1) {

                    mapLayout.visibility = View.GONE
                    mainLayout.visibility=View.VISIBLE
                    activityPic.imageTintList = resources.getColorStateList(R.color.blue)
                    activityName.setTextColor(resources.getColorStateList(R.color.blue))
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(
                        R.id.selection_frame_layout,
                        SwipeCardFragment()
                    )
                    fragment.commit()
                }

                if (position == 2) {

                    mapLayout.visibility = View.VISIBLE
                    mainLayout.visibility=View.GONE
                    activityPic.imageTintList = resources.getColorStateList(R.color.green1)
                    activityName.setTextColor(resources.getColorStateList(R.color.green1))
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.complete_frame_layout, MapFragment())
                    fragment.commit()
                }
                if (position == 3) {
                    mapLayout.visibility = View.GONE
                    mainLayout.visibility=View.VISIBLE
                    activityPic.imageTintList =
                        resources.getColorStateList(com.creation.nearby.R.color.sky_blue)
                    activityName.setTextColor(resources.getColorStateList(com.creation.nearby.R.color.sky_blue))
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.selection_frame_layout, EventsFragment())
                    fragment.commit()
                }
                if (position == 4) {

                    mapLayout.visibility = View.GONE
                    mainLayout.visibility=View.VISIBLE
                    activityPic.imageTintList = resources.getColorStateList(R.color.red)
                    activityName.setTextColor(resources.getColorStateList(R.color.red))
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.selection_frame_layout, FeedFragment())
                    fragment.commit()
                }
                if (position == 5) {
                    mapLayout.visibility = View.GONE
                    mainLayout.visibility=View.VISIBLE
                    activityPic.imageTintList =
                        resources.getColorStateList(com.creation.nearby.R.color.red)
                    activityName.setTextColor(resources.getColorStateList(R.color.red))
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.selection_frame_layout, FriendsFragment())
                    fragment.commit()
                }
            }
        }
        adapter = ActivityAdapter(list, onActionListener)
        binding.sectionRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
    override fun onClick(p0: View?) {
        when (p0) {

            binding.chatImageView -> {

                startActivity(Intent(this, ChatActivity::class.java))

            }
            binding.settingsImageView -> {

                startActivity(Intent(this, SideBarMenuActivity::class.java))

            }
            binding.userProfileIv -> {

                startActivity(Intent(this, EditProfileActivity::class.java))

            }
            binding.filterImageView -> {

                filterDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                filterDialog.behavior.skipCollapsed = true
                filterDialog.show()

            }
            dialogBinding.closeDialog -> {
                filterDialog.dismiss()
            }

        }
    }


}





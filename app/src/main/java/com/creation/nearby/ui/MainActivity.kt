package com.creation.nearby.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
        binding.searchImageView.setOnClickListener(this)

        dialogBinding.closeDialog.setOnClickListener(this)

    }


    private fun initAdapter() {

        val onActionListener = object : OnActionListener<ActivitiesModel> {
            override fun notify(model: ActivitiesModel, position: Int, view: View) {

                val mapLayout: FrameLayout = findViewById(R.id.complete_frame_layout)
                val mainLayout: FrameLayout = findViewById(R.id.selection_frame_layout)

//                val mainActivityLayout: ConstraintLayout = findViewById(R.id.mainActivityLayout)
//                val set = ConstraintSet()
//                set.clone(mainActivityLayout)
//                set.connect(R.id.selection_frame_layout,ConstraintSet.TOP,R.id.section_recycler_view,ConstraintSet.BOTTOM,24)
//                set.applyTo(mainActivityLayout)

                if (position == 0) {

                    mapLayout.visibility = View.GONE
                    mainLayout.visibility=View.VISIBLE

                    openFragment(HomeFragment(),R.id.selection_frame_layout)

                }
                if (position == 1) {

                    mapLayout.visibility = View.GONE
                    mainLayout.visibility=View.VISIBLE
                    openFragment(SwipeCardFragment(),R.id.selection_frame_layout)

                }

                if (position == 2) {


                    mapLayout.visibility = View.VISIBLE
                    mainLayout.visibility=View.GONE
                //    mainLayout.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT)
                    openFragment(MapFragment(),R.id.complete_frame_layout)
                }
                if (position == 3) {
                    mapLayout.visibility = View.GONE
                    mainLayout.visibility=View.VISIBLE

                    openFragment(EventsFragment(),R.id.selection_frame_layout)
                }
                if (position == 4) {

                    mapLayout.visibility = View.GONE
                    mainLayout.visibility=View.VISIBLE
                    openFragment(FeedFragment(),R.id.selection_frame_layout)

                }
                if (position == 5) {
                    mapLayout.visibility = View.GONE
                    mainLayout.visibility=View.VISIBLE

                    openFragment(FriendsFragment(),R.id.selection_frame_layout)

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
            binding.searchImageView -> {

                startActivity(Intent(this, ContactUsActivity::class.java))

            }

            dialogBinding.closeDialog -> {
                filterDialog.dismiss()
            }
        }
    }

    private fun openFragment( fragment: Fragment, id : Int){

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(id, fragment)
        fragmentTransaction.commit()

    }
}





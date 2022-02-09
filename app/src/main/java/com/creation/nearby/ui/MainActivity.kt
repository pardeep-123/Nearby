package com.creation.nearby.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.ActivityAdapter
import com.creation.nearby.databinding.ActivityMainBinding
import com.creation.nearby.databinding.FilterBottomSheetDialogBinding
import com.creation.nearby.fragments.*
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.ActivitiesModel
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var adapter: ActivityAdapter
    companion object{
         lateinit var binding: ActivityMainBinding

    }
    private var list = ArrayList<ActivitiesModel>()
    private lateinit var fragmentTransaction: FragmentTransaction

    lateinit var dialogBinding: FilterBottomSheetDialogBinding
    lateinit var filterDialog: BottomSheetDialog

    lateinit var set: ConstraintSet
    lateinit var mainLayout: FrameLayout
    lateinit var constraint: ConstraintLayout

    var MY_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
     /*   window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
*/
        setContentView(binding.root)

        if (!Places.isInitialized()) {
            Places.initialize(this, resources.getString(R.string.map_key))
        }

        mainLayout = findViewById(R.id.selection_frame_layout)
        constraint = findViewById(R.id.mainActivityLayout)
        set = ConstraintSet()
        set.clone(constraint)

        set.connect(R.id.selection_frame_layout,ConstraintSet.TOP,R.id.section_recycler_view,ConstraintSet.BOTTOM)
        set.connect(R.id.selection_frame_layout,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START)
        set.connect(R.id.selection_frame_layout,ConstraintSet.END,ConstraintSet.PARENT_ID,ConstraintSet.END)
        set.connect(R.id.selection_frame_layout,ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM)
        set.constrainDefaultHeight(R.id.selection_frame_layout,ConstraintSet.MATCH_CONSTRAINT_SPREAD)
        set.constrainDefaultWidth(R.id.selection_frame_layout,ConstraintSet.MATCH_CONSTRAINT_SPREAD)

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
        dialogBinding.allFilter.setOnClickListener(this)
        dialogBinding.maleLooking.setOnClickListener(this)
        dialogBinding.femaleLooking.setOnClickListener(this)
        dialogBinding.bothLooking.setOnClickListener(this)
        dialogBinding.filterOnline.setOnClickListener(this)
        dialogBinding.filterNew.setOnClickListener(this)
        dialogBinding.filterNew.setOnClickListener(this)
        dialogBinding.selectLocation.setOnClickListener(this)
    }
    private fun initAdapter() {

        val onActionListener = object : OnActionListener<ActivitiesModel> {
            override fun notify(model: ActivitiesModel, position: Int, view: View) {


                if (position == 0) {
                    openFragment(HomeFragment(),R.id.selection_frame_layout)
                    set.applyTo(constraint)

                }
                if (position == 1) {
                    openFragment(SwipeCardFragment(),R.id.selection_frame_layout)
                    set.applyTo(constraint)
                }

                if (position == 2) {
                    mainLayout.removeAllViews()
                    mainLayout.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT)
                    openFragment(MapFragment(),R.id.selection_frame_layout)
                }
                if (position == 3) {
                    openFragment(EventsFragment(),R.id.selection_frame_layout)
                    set.applyTo(constraint)
                }
                if (position == 4) {
                    openFragment(FeedFragment(),R.id.selection_frame_layout)
                    set.applyTo(constraint)

                }
                if (position == 5) {
                    openFragment(FriendsFragment(),R.id.selection_frame_layout)
                    set.applyTo(constraint)

                }
            }
        }
        adapter = ActivityAdapter(list, onActionListener)
        binding.sectionRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }
    override fun onClick(v: View?) {
        when (v) {

            binding.chatImageView -> {

                startActivity(Intent(this, ChatActivity::class.java))

            }
            binding.settingsImageView -> {

                startActivity(Intent(this, SideBarMenuActivity::class.java))

            }
            binding.userProfileIv -> {

                startActivity(Intent(this, MyProfileActivity::class.java))

            }
            binding.filterImageView -> {

                filterDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                filterDialog.behavior.skipCollapsed = true
                filterDialog.show()

            }
            binding.searchImageView -> {

                startActivity(Intent(this, SearchActivity::class.java))

            }

            dialogBinding.closeDialog -> {
                filterDialog.dismiss()
            }

               dialogBinding.selectLocation -> {
                openPlacePicker()
            }

            dialogBinding.maleLooking->{

                dialogBinding.maleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.sky_blue)
                dialogBinding.maleTv.setTextColor(ContextCompat.getColor(this,R.color.white))
                dialogBinding.maleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.white)

                dialogBinding.femaleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                dialogBinding.femaleTv.setTextColor(ContextCompat.getColor(this,R.color.black))
                dialogBinding.femaleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)

                dialogBinding.bothLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                dialogBinding.bothTv.setTextColor(ContextCompat.getColor(this,R.color.black))
                dialogBinding.bothColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)


            }
            dialogBinding.femaleLooking->{

            dialogBinding.maleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
            dialogBinding.maleTv.setTextColor(ContextCompat.getColor(this,R.color.black))
            dialogBinding.maleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)

            dialogBinding.femaleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.sky_blue)
            dialogBinding.femaleTv.setTextColor(ContextCompat.getColor(this,R.color.white))
            dialogBinding.femaleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.white)

            dialogBinding.bothLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
            dialogBinding.bothTv.setTextColor(ContextCompat.getColor(this,R.color.black))
            dialogBinding.bothColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)

        }
            dialogBinding.bothLooking->{

            dialogBinding.maleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
            dialogBinding.maleTv.setTextColor(ContextCompat.getColor(this,R.color.black))
            dialogBinding.maleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)

            dialogBinding.femaleLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
            dialogBinding.femaleTv.setTextColor(ContextCompat.getColor(this,R.color.black))
            dialogBinding.femaleColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.black)

            dialogBinding.bothLooking.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.sky_blue)
            dialogBinding.bothTv.setTextColor(ContextCompat.getColor(this,R.color.white))
            dialogBinding.bothColor.imageTintList = ActivityCompat.getColorStateList(this,R.color.white)
        }

            dialogBinding.allFilter->{

                dialogBinding.allFilter.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.sky_blue)
                dialogBinding.allFilter.setTextColor(ContextCompat.getColor(this,R.color.white))

                dialogBinding.filterOnline.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                dialogBinding.filterOnline.setTextColor(ContextCompat.getColor(this,R.color.black))

                dialogBinding.filterNew.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                dialogBinding.filterNew.setTextColor(ContextCompat.getColor(this,R.color.black))


            }
            dialogBinding.filterOnline->{

                dialogBinding.allFilter.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                dialogBinding.allFilter.setTextColor(ContextCompat.getColor(this,R.color.black))

                dialogBinding.filterOnline.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.sky_blue)
                dialogBinding.filterOnline.setTextColor(ContextCompat.getColor(this,R.color.white))

                dialogBinding.filterNew.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                dialogBinding.filterNew.setTextColor(ContextCompat.getColor(this,R.color.black))


            }
            dialogBinding.filterNew->{

                dialogBinding.allFilter.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                dialogBinding.allFilter.setTextColor(ContextCompat.getColor(this,R.color.black))

                dialogBinding.filterOnline.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.edittext_grey)
                dialogBinding.filterOnline.setTextColor(ContextCompat.getColor(this,R.color.black))

                dialogBinding.filterNew.backgroundTintList = ActivityCompat.getColorStateList(this,R.color.sky_blue)
                dialogBinding.filterNew.setTextColor(ContextCompat.getColor(this,R.color.white))

            }



        }
    }

    fun openPlacePicker() {
        MY_REQUEST = 1
        val fields = Arrays.asList(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.LAT_LNG,
            Place.Field.ADDRESS
        )
        val intent =
            Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this)
        startActivityForResult.launch(intent)
    }

    var startActivityForResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == AutocompleteActivity.RESULT_OK) {
                val place: Place? =
                    result.data?.let { Autocomplete.getPlaceFromIntent(it) }
                place?.let {
                    setPlaceData(it)
                }
            }
        }

    private fun setPlaceData(it: Place) {
        dialogBinding.selectLocation.setText(it.address.toString())
    }


    private fun openFragment( fragment: Fragment, id : Int){
         fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(id, fragment)
        fragmentTransaction.commit()


    }
}





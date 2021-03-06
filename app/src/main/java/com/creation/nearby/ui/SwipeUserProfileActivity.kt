package com.creation.nearby.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.GallaryAdapter
import com.creation.nearby.adapter.OtherProfileInterestAdapter
import com.creation.nearby.databinding.ActivitySwipeUserProfileBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.GallaryModel
import com.creation.nearby.model.InterestedModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView

class SwipeUserProfileActivity : AppCompatActivity(),View.OnClickListener{
    private lateinit var binding: ActivitySwipeUserProfileBinding

    private  var interestsList = ArrayList<InterestedModel>()
    private lateinit var  interestsAdapter: OtherProfileInterestAdapter

    private var galleryList = ArrayList<GallaryModel>()
    private lateinit var galleryAdapter: GallaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySwipeUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        interestsList.add(InterestedModel("Travel",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Chatting",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Athlete",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("House Parties",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Cricket",isSelected = false,isProfile = true))

        interestsAdapter = OtherProfileInterestAdapter(interestsList)
        binding.interestRecyclerView.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW)
      //  binding.interestRecyclerView.layoutManager = GridLayoutManager(this, 3,RecyclerView.VERTICAL,false)
        binding.interestRecyclerView.adapter = interestsAdapter
        interestsAdapter.notifyDataSetChanged()

        galleryList.add(GallaryModel(""))
        galleryList.add(GallaryModel(""))
        galleryList.add(GallaryModel(""))
        galleryList.add(GallaryModel(""))
        galleryList.add(GallaryModel(""))



        initAdapter()
        binding.goBack.setOnClickListener(this)
        binding.blockTv.setOnClickListener(this)

    }

    private fun initAdapter() {
        val onActionListener = object : OnActionListener<GallaryModel> {
            override fun notify(model: GallaryModel, position: Int, view: View) {

                val intent  = Intent(this@SwipeUserProfileActivity,FullPictureActivity::class.java)

                val transitionName: String = getString(R.string.open_with_animation)
                val viewImage: MaterialCardView = view.findViewById(R.id.layoutCard)

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@SwipeUserProfileActivity,viewImage,transitionName)
                ActivityCompat.startActivity(this@SwipeUserProfileActivity,intent,options.toBundle())

            }
        }
        binding.gallaryRecyclerView.layoutManager = GridLayoutManager(this,3)
//        galleryAdapter = GallaryAdapter(this, galleryList, onActionListener)
//        binding.gallaryRecyclerView.adapter = galleryAdapter
        galleryAdapter.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {

        when(v){
            binding.goBack->{
                onBackPressed()
            }
            binding.blockTv->{
                optionsDialog()
            }
        }


    }

    private fun optionsDialog() {
        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                android.R.color.transparent
            )
        )
        dialog.setContentView(R.layout.report_bottom_sheet)

        val tvYes: TextView? = dialog.findViewById(R.id.select_photo_library)
        val tvDetail: TextView? = dialog.findViewById(R.id.select_camera)
        val tvNo: TextView? = dialog.findViewById(R.id.cancel)

        tvDetail?.text = "Are you sure you want to block?"

        tvYes?.setOnClickListener {
            dialog.dismiss()
        }

        tvNo?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


}
package com.creation.nearby.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.adapter.GallaryAdapter
import com.creation.nearby.adapter.ImageAdapter
import com.creation.nearby.adapter.InterestsAdapter
import com.creation.nearby.databinding.ActivityMyProfileBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.GallaryModel
import com.creation.nearby.model.ImageModel
import com.creation.nearby.model.InterestedModel
import com.creation.nearby.utils.ImagePickerUtility
import com.creation.nearby.utils.ToastUtils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.permissionx.guolindev.PermissionX

class MyProfileActivity : ImagePickerUtility() {

    private lateinit var binding: ActivityMyProfileBinding
    private  var interestsList = ArrayList<InterestedModel>()
    private lateinit var  interestsAdapter: InterestsAdapter

    private var gallaryList = ArrayList<GallaryModel>()
    private lateinit var gallaryAdapter: GallaryAdapter
    override fun selectedImage(imagePath: String?) {
        Glide.with(this).load(imagePath).into(binding.userProfilePicMyProfile)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(binding.root)

        initAdapter()

        interestsList.add(InterestedModel("Travel",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Chatting",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Athlete",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("House Parties",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Cricket",isSelected = false,isProfile = true))

        interestsAdapter = InterestsAdapter(interestsList)
        binding.profileInterestRecView.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW)
     //   binding.profileInterestRecView.layoutManager = GridLayoutManager(this, 3,RecyclerView.VERTICAL,false)
        binding.profileInterestRecView.adapter = interestsAdapter
        interestsAdapter.notifyDataSetChanged()

        gallaryList.add(GallaryModel(R.drawable.swipe_card_image))
        gallaryList.add(GallaryModel(R.drawable.swipe_card_image))
        gallaryList.add(GallaryModel(R.drawable.swipe_card_image))
        gallaryList.add(GallaryModel(R.drawable.swipe_card_image))
        gallaryList.add(GallaryModel(R.drawable.swipe_card_image))

        clickHandler()

    }

    private fun clickHandler() {

        binding.backbtn1.setOnClickListener{
            onBackPressed()
        }

        binding.cameraIv.setOnClickListener{

         getImage(this,0)

        }
        binding.editProfileIv.setOnClickListener{

            startActivity(Intent(this,EditProfileActivity::class.java))
        }

    }

    private fun initAdapter() {

        val onActionListener = object : OnActionListener<GallaryModel> {
            override fun notify(model: GallaryModel, position: Int,view: View) {

                val intent  = Intent(this@MyProfileActivity,FullPictureActivity::class.java)

                val transitionName: String = getString(R.string.open_with_animation)
                val viewImage: MaterialCardView = view.findViewById(R.id.layoutCard)

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MyProfileActivity,viewImage,transitionName)
                ActivityCompat.startActivity(this@MyProfileActivity,intent,options.toBundle())


            }
        }
        binding.gallaryRecyclerView.layoutManager = GridLayoutManager(this,3)
        gallaryAdapter = GallaryAdapter(this, gallaryList, onActionListener)
        binding.gallaryRecyclerView.adapter = gallaryAdapter
        gallaryAdapter.notifyDataSetChanged()
    }

}
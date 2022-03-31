package com.creation.nearby.ui

import android.R.string
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.split
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.adapter.GallaryAdapter
import com.creation.nearby.adapter.InterestsAdapter
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.ActivityMyProfileBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.GallaryModel
import com.creation.nearby.model.InterestedModel
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.ImagePickerUtility
import com.creation.nearby.viewmodel.ProfileVM
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.card.MaterialCardView
import java.util.*
import kotlin.collections.ArrayList


class MyProfileActivity : ImagePickerUtility() {

    private lateinit var binding: ActivityMyProfileBinding
    private  var interestsList = ArrayList<InterestedModel>()
    private lateinit var  interestsAdapter: InterestsAdapter

    private var gallaryList = ArrayList<String>()
    private lateinit var gallaryAdapter: GallaryAdapter

    private val profileViewModel: ProfileVM by viewModels()

    override fun selectedImage(imagePath: String?) {
        Glide.with(this).load(imagePath).into(binding.userProfilePicMyProfile)
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        binding.profileVM = profileViewModel

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(binding.root)

        initAdapter()

        clickHandler()


        //observe get profile response
        profileViewModel.profileData.observeForever { response->
            response?.let {profileRes->
                val profileImage = profileRes.body?.image
                if (profileImage?.isNotEmpty() == true) {
                    Glide.with(this)
                        .load("${Constants.IMAGE_BASE_URL}$profileImage")
                        .placeholder(R.drawable.placeholder)
                        .into(binding.userProfilePicMyProfile)
                }
                /**
                 * Add Images in gallary list
                 */
                gallaryList.clear()
                profileRes.body?.userImages?.forEach {
                    gallaryList.add(it.image!!)
                }
                gallaryAdapter.notifyDataSetChanged()
                /**
                 * Add Interests in Array List
                 */

                val intersetList = profileRes.body?.interests?.split(",")?.toList()
                interestsList.clear()
                intersetList?.forEach {
                    interestsList.add(InterestedModel(it,isSelected = false,isProfile = true))
                }
                interestsAdapter.notifyDataSetChanged()
                binding.tvUserName.text = "${profileRes.body?.firstname} ${profileRes.body?.lastname}"
                binding.tvBio.text = profileRes.body?.biography
                binding.myZodiacSignTv.text = profileRes.body?.zodiac
                binding.interestedInTv.text = profileRes.body?.intrestedIn
                binding.userDescription.text = "based in"+ profileRes.body?.location

                /**
                 * save image and name in shared preference
                 */
                PreferenceFile.storeKey(this,"username",profileRes.body?.firstname!!)
                PreferenceFile.storeKey(this, Constants.USER_IMAGE,profileRes.body.image!!)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //get profile api call
        profileViewModel.getProfileApi(this)
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



        interestsAdapter = InterestsAdapter(interestsList)
        binding.profileInterestRecView.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW)
        binding.profileInterestRecView.adapter = interestsAdapter


    }

}
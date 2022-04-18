package com.creation.nearby.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.ActivitySideBarMenuBinding
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.Constants.IMAGE_BASE_URL
import com.creation.nearby.utils.Constants.USER_IMAGE
import com.creation.nearby.utils.ImagePickerUtility
import com.creation.nearby.viewmodel.SettingVM
import kotlinx.android.synthetic.main.activity_side_bar_menu.*

class SideBarMenuActivity : ImagePickerUtility() {
    val settingVM : SettingVM by viewModels()
   private lateinit var binding: ActivitySideBarMenuBinding
    override fun selectedImage(imagePath: String?) {
        Glide.with(this).load(imagePath).into(binding.userImage)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySideBarMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.settingVM = settingVM
        username.text = PreferenceFile.retrieveKey(this,"username")
        onClickEvent()

        val image = PreferenceFile.retrieveKey(this,USER_IMAGE)
        if(image?.isNotEmpty() == true) {
            Glide.with(this).load("$IMAGE_BASE_URL$image").into(binding.userImage)
        }

    }

    private fun onClickEvent() {

        binding.notificationLayout.setOnClickListener{
            startActivity(Intent(this,NotificationsActivity::class.java))
        }
        binding.settingsLayout.setOnClickListener{
            startActivity(Intent(this,SettingsActivity::class.java))
        }
        binding.myProfileLayout.setOnClickListener{
            startActivity(Intent(this,MyProfileActivity::class.java))
        }
        binding.feedbackLayout.setOnClickListener{
            startActivity(Intent(this,FeedBackActivity::class.java))
        }
        binding.contactUsLayout.setOnClickListener{
            startActivity(Intent(this,ContactUsActivity::class.java))
        }
        binding.toggleOnLayout.setOnClickListener{
            binding.toggleOffLayout.visibility = View.VISIBLE
            binding.toggleOnLayout.visibility = View.GONE
        }
        binding.toggleOffLayout.setOnClickListener{
            binding.toggleOffLayout.visibility = View.GONE
            binding.toggleOnLayout.visibility = View.VISIBLE
        }
        binding.userCamera.setOnClickListener{

           getImage(this,0)

        }
        binding.backBtn.setOnClickListener{
            onBackPressed()
        }
    }
}
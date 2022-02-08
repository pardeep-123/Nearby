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
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivitySideBarMenuBinding
import com.creation.nearby.ui.authentication.LoginActivity
import com.creation.nearby.utils.ImagePickerUtility
import com.creation.nearby.utils.ToastUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.permissionx.guolindev.PermissionX

class SideBarMenuActivity : ImagePickerUtility() {

   private lateinit var binding: ActivitySideBarMenuBinding
    override fun selectedImage(imagePath: String?) {
        Glide.with(this).load(imagePath).into(binding.userImage)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySideBarMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickEvent()

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
        binding.logoutBtn.setOnClickListener{
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }


    }

}
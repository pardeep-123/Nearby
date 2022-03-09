package com.creation.nearby.ui

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.ActivitySideBarMenuBinding
import com.creation.nearby.ui.authentication.LoginActivity
import com.creation.nearby.utils.ImagePickerUtility
import kotlinx.android.synthetic.main.activity_side_bar_menu.*

class SideBarMenuActivity : ImagePickerUtility() {

   private lateinit var binding: ActivitySideBarMenuBinding
    override fun selectedImage(imagePath: String?) {
        Glide.with(this).load(imagePath).into(binding.userImage)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySideBarMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        username.text = PreferenceFile.retrieveKey(this,"username")
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
            logoutDialog()
        }
    }


    private fun logoutDialog(){
        AlertDialog.Builder(this, R.style.MyDialogTheme)
            .setTitle("Log Out")
            .setMessage("Are you sure want to Quit?")
            .setPositiveButton("Yes") { dialog: DialogInterface?, whichButton: Int ->
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()
            }
            .setNegativeButton("No") { dialog: DialogInterface?, i: Int ->
                dialog?.dismiss()
            }
            .show()
    }

}
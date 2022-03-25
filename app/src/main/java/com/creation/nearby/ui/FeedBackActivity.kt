package com.creation.nearby.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.creation.nearby.databinding.ActivityFeedBackBinding
import com.creation.nearby.utils.ImagePickerUtility
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.viewmodel.FeedBackVM
import com.permissionx.guolindev.PermissionX

class FeedBackActivity : ImagePickerUtility(), View.OnClickListener {

    private lateinit var binding: ActivityFeedBackBinding
    private val feedBackVM: FeedBackVM by viewModels()

    override fun selectedImage(imagePath: String?) {
        feedBackVM.image.set(imagePath)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBackBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.feedbackVM = feedBackVM
        binding.lifecycleOwner = this
        binding.uploadPicLayout.setOnClickListener(this)
        binding.goBack.setOnClickListener(this)
        binding.sendFeedback.setOnClickListener(this)
    }

    private fun openResourceWithPermissionCheck() {

        PermissionX.init(this)
            .permissions(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->

                scope.showRequestReasonDialog(
                    deniedList, "You need to allow permissions, to select photo.",
                    "Allow",
                    "Deny"
                )

            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "You need to allow necessary permissions in Settings manually",
                    "Open Settings",
                    "Cancel"
                )
            }
            .request { allGranted, _, _ ->
                if (allGranted) {
                    getImageFromGallery()
                } else
                    ToastUtils.showToast(this, "Unable to perform action due to permissions")
            }

    }

    private fun getImageFromGallery() {
        galleryResultLauncher.launch(
            Intent(Intent.ACTION_PICK).apply {
                type = "image/*"
            },
        )
    }

    private var galleryResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                binding.feedbackImage.setImageURI(data?.data)
                binding.feedbackIcon.visibility = View.GONE
            }
        }

    override fun onClick(v: View?) {

        when (v) {
            binding.uploadPicLayout -> {
            getImage(this,0)


            }
            binding.goBack -> {

                onBackPressed()

            }
            binding.sendFeedback -> {

                finish()

            }
        }

    }

}
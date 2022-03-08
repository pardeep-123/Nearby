package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityEventDetailsBinding
import com.creation.nearby.model.GetEventModel
import com.creation.nearby.viewmodel.EventDetailsVM
import com.google.android.material.bottomsheet.BottomSheetDialog

class EventDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailsBinding
    private val eventDetailsVM: EventDetailsVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        binding.detailsVM = eventDetailsVM
        binding.lifecycleOwner = this
        val data = intent.extras?.get("eventData") as GetEventModel.Body
        eventDetailsVM.detailsModel.set(data)


        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(binding.root)

        clickHandler()
    }

    private fun clickHandler() {
        binding.goBack.setOnClickListener{
            onBackPressed()
        }
        binding.eventsCheck.setOnClickListener{
            onBackPressed()
        }
        binding.reportTv.setOnClickListener {
            optionsDialog()
        }
        binding.eventsCross.setOnClickListener {
            onBackPressed()
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
        val tvNo: TextView? = dialog.findViewById(R.id.cancel)

        tvYes?.setOnClickListener {
            dialog.dismiss()
        }

        tvNo?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


}
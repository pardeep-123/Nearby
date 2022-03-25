package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.HistoryAdapter
import com.creation.nearby.databinding.ActivityContactUsBinding
import com.creation.nearby.model.HistoryModel
import com.creation.nearby.utils.AppUtils
import com.creation.nearby.viewmodel.ContactUsVM

class ContactUsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityContactUsBinding
   val contactUsVM : ContactUsVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
      binding.contactVM = contactUsVM
        binding.lifecycleOwner = this
        binding.historyRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        binding.goBack.setOnClickListener(this)
        binding.submitMessage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.goBack -> {
                onBackPressed()
            }
            binding.submitMessage -> {
                finish()
        }
        }
    }
}
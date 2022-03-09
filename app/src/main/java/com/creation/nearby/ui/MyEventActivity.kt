package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityMyEventBinding
import com.creation.nearby.viewmodel.MyEventVM

class MyEventActivity : AppCompatActivity() {
    private val myEventVM : MyEventVM by viewModels()
    lateinit var binding : ActivityMyEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.myEventVM = myEventVM
    }

    override fun onResume() {
        super.onResume()
        myEventVM.eventListApi(this)
    }
}
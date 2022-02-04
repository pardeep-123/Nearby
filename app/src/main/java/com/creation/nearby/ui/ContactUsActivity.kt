package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.HistoryAdapter
import com.creation.nearby.databinding.ActivityContactUsBinding
import com.creation.nearby.model.HistoryModel

class ContactUsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityContactUsBinding
    private var historyList = ArrayList<HistoryModel>()
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.historyRecyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        historyList.add(
            HistoryModel(
                R.drawable.chat_pic_3,
                "5 hrs ago",
                "Loren ipsom dolor sit amet, consectetur adipi-scing elit.Phasellus nisineque.",
                R.drawable.history_user_icon,
                "",
                ""
            )
        )
        historyList.add(
            HistoryModel(
                R.drawable.chat_pic_3,
                "3 hrs ago",
                "Loren ipsom dolor sit amet, consectetur adipi-scing elit.Phasellus nisineque.",
                R.drawable.history_user_icon,
                "10 mins ago",
                "Loren ipsom dolor sit amet, consectetur adipi-scing elit.Phasellus nisineque."
            )
        )
        historyList.add(
            HistoryModel(
                R.drawable.chat_pic_3,
                "2 hrs ago",
                "Loren ipsom dolor sit amet, consectetur adipi-scing elit.Phasellus nisineque.",
                R.drawable.history_user_icon,
                "",
                ""
            )
        )


        historyAdapter = HistoryAdapter(historyList)
        binding.historyRecyclerView.adapter = historyAdapter
        historyAdapter.notifyDataSetChanged()

        binding.goBack.setOnClickListener(this)
        binding.submitMessage.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.goBack -> {
                onBackPressed()
            }   binding.submitMessage -> {


        }
        }
    }
}
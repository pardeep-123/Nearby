package com.creation.nearby.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.ImageAdapter
import com.creation.nearby.adapter.SuggestionAdapter
import com.creation.nearby.databinding.ActivityFullPictureBinding
import com.creation.nearby.databinding.ActivityOngoingChatBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.ImageModel
import com.creation.nearby.model.SuggestionsModel
import com.creation.nearby.utils.AppUtils

class OngoingChatActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityOngoingChatBinding

    private lateinit var suggestionAdapter: SuggestionAdapter
    private  var suggestionList = ArrayList<SuggestionsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOngoingChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppUtils.hideKeyboard(this)
        binding.suggestionRecyclerView.layoutManager = LinearLayoutManager(this,
            RecyclerView.HORIZONTAL,false)

        suggestionList.add(SuggestionsModel("Hi \uD83D\uDE4C"))
        suggestionList.add(SuggestionsModel("Wyd"))
        suggestionList.add(SuggestionsModel("How was your day?"))

       initAdapter()

        binding.goback.setOnClickListener(this)
        binding.sendMessageBtn.setOnClickListener(this)
        binding.sendMessageBtn.setOnClickListener(this)
        binding.phoneIv.setOnClickListener(this)
        binding.videoIv.setOnClickListener(this)


    }

    private fun initAdapter() {

        val onActionListener = object : OnActionListener<SuggestionsModel> {
            override fun notify(model: SuggestionsModel, position: Int, view: View) {

                binding.sendMessageEdiText.setText(model.suggestion)

            }
        }

        suggestionAdapter = SuggestionAdapter(suggestionList,onActionListener)
        binding.suggestionRecyclerView.adapter = suggestionAdapter
        suggestionAdapter.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        when(p0) {

            binding.goback -> {
                onBackPressed()
            }
            binding.sendMessageBtn ->
            {
                binding.emptyLayout.visibility = View.GONE
            }
            binding.phoneIv ->
            {
                startActivity(Intent(this,AudioActivity::class.java))
            }  binding.videoIv ->
        {
            startActivity(Intent(this,VideoActivity::class.java))
        }

        }
    }
}
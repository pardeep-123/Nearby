package com.creation.nearby.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityOngoingChatBinding
import com.creation.nearby.databinding.FragmentSwipeUserProfileBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class SwipeUserProfileBottomFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSwipeUserProfileBottomBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSwipeUserProfileBottomBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}
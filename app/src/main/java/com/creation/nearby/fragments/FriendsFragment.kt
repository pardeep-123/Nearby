package com.creation.nearby.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.databinding.FragmentFriendsBinding
import com.creation.nearby.viewmodel.FriendListVM

class FriendsFragment : Fragment() {

    lateinit var binding: FragmentFriendsBinding

    private val friendListVM : FriendListVM by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFriendsBinding.inflate(inflater,container,false)
        binding.friendListVM = friendListVM
        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //online user recycler view

        binding.onlineRecView.layoutManager = GridLayoutManager(view.context,3,RecyclerView.VERTICAL,false)

        friendListVM.getFriendApi(requireContext())

        binding.friendsRecView.layoutManager = GridLayoutManager(view.context,3,RecyclerView.VERTICAL,false)

    }

}
package com.creation.nearby.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.FriendsAdapter
import com.creation.nearby.adapter.OnlineUserFriendsAdapter
import com.creation.nearby.databinding.FragmentFriendsBinding
import com.creation.nearby.model.FriendsModel

class FriendsFragment : Fragment() {

    lateinit var binding: FragmentFriendsBinding
    lateinit var onlineAdapter: OnlineUserFriendsAdapter
    private lateinit var friendsAdapter: FriendsAdapter
    private var onlineList = ArrayList<FriendsModel>()
    private var friendsList = ArrayList<FriendsModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFriendsBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //online user recycler view

        binding.onlineRecView.layoutManager = GridLayoutManager(view.context,3,RecyclerView.VERTICAL,false)

        onlineList.add(FriendsModel(R.drawable.friends_pic_1,"Juliana, 18",true))
        onlineList.add(FriendsModel(R.drawable.friends_pic_2,"Will, 17",true))
        onlineList.add(FriendsModel(R.drawable.friends_pic_3,"Jenny, 19",true))
        onlineList.add(FriendsModel(R.drawable.friends_pic_4,"Ann, 16",true))
        onlineList.add(FriendsModel(R.drawable.friends_pic_5,"Josh, 18",true))
        onlineList.add(FriendsModel(R.drawable.friends_pic_6,"William, 16",true))

        onlineAdapter = OnlineUserFriendsAdapter(onlineList)
        binding.onlineRecView.adapter = onlineAdapter
        onlineAdapter.notifyDataSetChanged()

        //online user recycler view

        //friends user recycler view

        binding.friendsRecView.layoutManager = GridLayoutManager(view.context,3,RecyclerView.VERTICAL,false)

        friendsList.add(FriendsModel(R.drawable.friends_pic_7,"Soham, 18",false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_8,"Shawn, 19",false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_9,"Dianne, 17",false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_10,"Jenna, 17",false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_11,"Ronald, 17",false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_12,"Arthur, 16",false))

        friendsList.add(FriendsModel(R.drawable.friends_pic_7,"Soham, 18",false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_8,"Shawn, 19",false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_9,"Dianne, 17",false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_10,"Jenna, 17",false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_11,"Ronald, 17",false))
        friendsList.add(FriendsModel(R.drawable.friends_pic_12,"Arthur, 16",false))

        friendsAdapter = FriendsAdapter(friendsList)
        binding.friendsRecView.adapter = friendsAdapter
        friendsAdapter.notifyDataSetChanged()


        //friends user recycler view





    }

}
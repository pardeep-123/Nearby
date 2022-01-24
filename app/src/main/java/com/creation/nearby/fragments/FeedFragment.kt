package com.creation.nearby.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.OnlineUserFriendsAdapter
import com.creation.nearby.adapter.PostAdapter
import com.creation.nearby.adapter.SuggestionAdapter
import com.creation.nearby.databinding.FragmentFeedBinding
import com.creation.nearby.model.FriendsModel
import com.creation.nearby.model.PostModel
import com.creation.nearby.model.SuggestionsModel

class FeedFragment : Fragment() {

    lateinit var binding: FragmentFeedBinding

    private lateinit var postAdapter: PostAdapter
    private lateinit var suggestionAdapter: SuggestionAdapter

    private  var postList = ArrayList<PostModel>()
    private  var suggestionList = ArrayList<SuggestionsModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.postsRecyView.layoutManager = LinearLayoutManager(view.context,RecyclerView.VERTICAL,false)

        postList.add(PostModel(R.drawable.friends_pic_1,"Jenna","3 mins ago","Hey! I don’t know what to do in this city. Anyone able to help me get around?",R.color.sky_blue))
        postList.add(PostModel(R.drawable.friends_pic_1,"Jenna","1 min ago","Anyone wanna take a coffee, I have many hours to spare.",R.color.blue))
        postList.add(PostModel(R.drawable.friends_pic_1,"Jenna","3 mins ago","Hey! I don’t know what to do in this city. Anyone able to help me get around?",R.color.sky_blue))


        postAdapter = PostAdapter(postList)
        binding.postsRecyView.adapter = postAdapter
        postAdapter.notifyDataSetChanged()



        binding.suggestionRecView.layoutManager = LinearLayoutManager(view.context,RecyclerView.HORIZONTAL,false)

        suggestionList.add(SuggestionsModel("Coffee anyone?"))
        suggestionList.add(SuggestionsModel("What’s up today?"))
        suggestionList.add(SuggestionsModel("Want to chat?"))


        suggestionAdapter = SuggestionAdapter(suggestionList)
        binding.suggestionRecView.adapter = suggestionAdapter
        suggestionAdapter.notifyDataSetChanged()




    }
}
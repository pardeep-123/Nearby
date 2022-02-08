package com.creation.nearby.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.ImageAdapter
import com.creation.nearby.adapter.OnlineUserFriendsAdapter
import com.creation.nearby.adapter.PostAdapter
import com.creation.nearby.adapter.SuggestionAdapter
import com.creation.nearby.databinding.FragmentFeedBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.FriendsModel
import com.creation.nearby.model.ImageModel
import com.creation.nearby.model.PostModel
import com.creation.nearby.model.SuggestionsModel
import com.creation.nearby.utils.ToastUtils
import com.futuremind.recyclerviewfastscroll.Utils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog

class FeedFragment : Fragment() {

    lateinit var binding: FragmentFeedBinding

    private lateinit var postAdapter: PostAdapter
    private lateinit var suggestionAdapter: SuggestionAdapter
    private  var suggestionList = ArrayList<SuggestionsModel>()

    private  var postList = ArrayList<PostModel>()

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
        postList.add(PostModel(R.drawable.friends_pic_1,"Jenna","3 mins ago","Hey! I don’t know what to do in this city. Anyone able to help me get around?",R.color.teal))
        postList.add(PostModel(R.drawable.friends_pic_1,"Jenna","1 min ago","Anyone wanna take a coffee, I have many hours to spare.",R.color.teal))
        postList.add(PostModel(R.drawable.friends_pic_1,"Jenna","3 mins ago","Hey! I don’t know what to do in this city. Anyone able to help me get around?",R.color.teal))

        initAdapter()

        binding.suggestionRecView.layoutManager = LinearLayoutManager(view.context,RecyclerView.HORIZONTAL,false)

        suggestionList.add(SuggestionsModel("Coffee anyone?"))
        suggestionList.add(SuggestionsModel("What’s up today?"))
        suggestionList.add(SuggestionsModel("Want to chat?"))

        suggestionAdapter()

    }

    private fun initAdapter() {

        val onActionListener = object : OnActionListener<PostModel> {
            override fun notify(model: PostModel, position: Int, view: View) {
                optionsDialog()
            }
        }
        postAdapter = PostAdapter(postList,onActionListener)
        binding.postsRecyView.adapter = postAdapter
        postAdapter.notifyDataSetChanged()

    }

    private fun suggestionAdapter() {

        val onActionListener = object : OnActionListener<SuggestionsModel> {
            override fun notify(model: SuggestionsModel, position: Int, view: View) {

                binding.peopleEditText.setText(model.suggestion)

            }
        }
        suggestionAdapter = SuggestionAdapter(suggestionList,onActionListener)
        binding.suggestionRecView.adapter = suggestionAdapter
        suggestionAdapter.notifyDataSetChanged()
    }


    private fun optionsDialog() {
        val dialog = BottomSheetDialog(this.requireContext(), R.style.CustomBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(
                ContextCompat.getDrawable(this.requireContext(),
                    android.R.color.transparent
                )
        )
        dialog.setContentView(R.layout.report_bottom_sheet)

        val tvYes: TextView? = dialog.findViewById(R.id.select_photo_library)
        val tvNo: TextView? = dialog.findViewById(R.id.cancel)

        tvYes?.setOnClickListener {

            optionsDialog2()
            dialog.dismiss()
        }

        tvNo?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun optionsDialog2() {
        val dialog = Dialog(this.requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(this.requireContext(),
                android.R.color.transparent
            )
        )
        dialog.setContentView(R.layout.report_dialog)

        val report: EditText? = dialog.findViewById(R.id.report_edit_text)
        val send: Button? = dialog.findViewById(R.id.send)
        val closeDialog: ImageView? = dialog.findViewById(R.id.close_dialog)

        send?.setOnClickListener {
            dialog.dismiss()
        }

        closeDialog?.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }



}
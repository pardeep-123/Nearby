package com.creation.nearby.fragments

import android.app.Dialog
import android.os.Bundle
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.adapter.PostAdapter
import com.creation.nearby.adapter.SuggestionAdapter
import com.creation.nearby.databinding.FragmentFeedBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.PostModel
import com.creation.nearby.model.SuggestionsModel
import com.creation.nearby.utils.ImagePickerFragmentUtility
import com.google.android.material.bottomsheet.BottomSheetDialog


class FeedFragment : ImagePickerFragmentUtility() {

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
        postList.add(PostModel(R.drawable.friends_pic_1,"Jenna","3 mins ago","Hey! I don’t know what to do in this city. Anyone able to help me get around?",R.drawable.swipe_image2))
        postList.add(PostModel(R.drawable.friends_pic_1,"Jenna","1 min ago","Anyone wanna take a coffee, I have many hours to spare.",R.drawable.swipe_image2))
        postList.add(PostModel(R.drawable.friends_pic_1,"Jenna","3 mins ago","Hey! I don’t know what to do in this city. Anyone able to help me get around?",null))

        initAdapter()

        binding.suggestionRecView.layoutManager = LinearLayoutManager(view.context,RecyclerView.HORIZONTAL,false)

        suggestionList.add(SuggestionsModel("Coffee anyone?"))
        suggestionList.add(SuggestionsModel("What’s up today?"))
        suggestionList.add(SuggestionsModel("Want to chat?"))

        suggestionAdapter()

        clickHandler()

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


    override fun selectedImage(imagePath: String?) {
        Glide.with(requireActivity()).load(imagePath).into(binding.gallerySelectedImage)


        val transition: Transition = Slide(Gravity.BOTTOM)
        transition.duration= 600
        transition.addTarget(binding.feedGalleryImage)
        TransitionManager.beginDelayedTransition(binding.parent, transition)
        binding.feedGalleryImage.visibility = View.VISIBLE
    }

    private fun clickHandler(){

        binding.imageCameraIv.setOnClickListener{
            getImage(requireActivity(),0)
        }

        binding.closeImage.setOnClickListener{

            val transition: Transition = Slide(Gravity.BOTTOM)
            transition.duration= 600
            transition.addTarget(binding.feedGalleryImage)
            TransitionManager.beginDelayedTransition(binding.parent, transition)
            binding.feedGalleryImage.visibility = View.GONE

        }

    }

}
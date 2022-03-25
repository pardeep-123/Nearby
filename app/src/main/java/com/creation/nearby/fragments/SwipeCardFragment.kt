package com.creation.nearby.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import com.creation.nearby.databinding.FragmentSwipeCardBinding
import com.creation.nearby.utils.LocationUpdateUtilityFragment
import com.creation.nearby.viewmodel.SwipeVM
import com.yuyakaido.android.cardstackview.*


class SwipeCardFragment : LocationUpdateUtilityFragment(), CardStackListener, View.OnClickListener {

    private lateinit var swipeLayoutManager: CardStackLayoutManager
    private lateinit var binding: FragmentSwipeCardBinding

    private val swipeVM: SwipeVM by viewModels()

    var currentLat = 0.0
    var currentLng = 0.0
    private var userId = 0
  private var ctx : Context?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }
    override fun updatedLatLng(lat: Double, lng: Double) {
        currentLat = lat
        currentLng = lng
        swipeVM.swipeListApi(ctx!!, currentLat, currentLng)
    }

    override fun liveLatLng(lat: Double, lng: Double) {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSwipeCardBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.swipeVM = swipeVM
        swipeLayoutManager = CardStackLayoutManager(context, this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }
        swipeLayoutManager.setStackFrom(StackFrom.Bottom)
        swipeLayoutManager.setScaleInterval(0.95f)

        binding.cardStackView.layoutManager = swipeLayoutManager

        // hit api

        getLiveLocation(requireActivity())
        binding.cardStackView.itemAnimator.apply {

            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

        //  swipeAdapter.notifyDataSetChanged()

        binding.swipeDislikeLayout.setOnClickListener(this)
        binding.swipeLikeLayout.setOnClickListener(this)

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {

        if (direction == Direction.Left)
            swipeVM.swipeUserApi(requireContext(), userId.toString(), "0")
        else swipeVM.swipeUserApi(requireContext(), userId.toString(), "1")
    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {
        userId = swipeVM.getSwipeList[position].id

        val visibility = swipeVM.setVisibility()
        if(visibility){
            binding.constButton.visibility = View.GONE
            binding.noDataFound.visibility = View.VISIBLE
        }else{
            binding.constButton.visibility = View.VISIBLE
            binding.noDataFound.visibility = View.GONE
        }
    }

    override fun onCardDisappeared(view: View?, position: Int) {

        if(position == swipeVM.getSwipeList.size-1){
            binding.constButton.visibility = View.GONE
            binding.noDataFound.visibility = View.VISIBLE
        }else{
            binding.constButton.visibility = View.VISIBLE
            binding.noDataFound.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {

        when (v) {
            binding.swipeDislikeLayout -> {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                swipeLayoutManager.setSwipeAnimationSetting(setting)
                binding.cardStackView.swipe()
                // hit api
                swipeVM.swipeUserApi(requireContext(), userId.toString(), "0")
            }
            binding.swipeLikeLayout -> {
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                swipeLayoutManager.setSwipeAnimationSetting(setting)
                binding.cardStackView.swipe()

                // hit api
                swipeVM.swipeUserApi(requireContext(), userId.toString(), "1")
            }
        }

    }
}
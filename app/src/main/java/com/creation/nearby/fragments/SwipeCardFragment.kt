package com.creation.nearby.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import com.creation.nearby.R
import com.creation.nearby.adapter.SwipeCardAdapter
import com.creation.nearby.databinding.FragmentSwipeCardBinding
import com.creation.nearby.model.SwipeCardModel
import com.creation.nearby.ui.CongratulationActivity
import com.yuyakaido.android.cardstackview.*


class SwipeCardFragment : Fragment(),CardStackListener,View.OnClickListener{

    private lateinit var swipeAdapter: SwipeCardAdapter
    private lateinit var swipeLayoutManager: CardStackLayoutManager
    private var swipeLIst = ArrayList<SwipeCardModel>()
    private var height: Int? = null

    private lateinit var binding: FragmentSwipeCardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSwipeCardBinding.inflate(inflater)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeLayoutManager = CardStackLayoutManager(context,this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }
        swipeLayoutManager.setStackFrom(StackFrom.Bottom)
        swipeLayoutManager.setScaleInterval(0.95f)
        swipeLIst.add(SwipeCardModel(R.drawable.swipe_card_image,"Saurabh Thakur","23","Hamirpur"))
        swipeLIst.add(SwipeCardModel(R.drawable.swipe_image2,"Amit Thakur","23","Hamirpur"))
        swipeLIst.add(SwipeCardModel(R.drawable.swipe_card_image,"Rahul Thakur","23","Hamirpur"))
        swipeLIst.add(SwipeCardModel(R.drawable.swipe_image2,"Amish Thakur","23","Hamirpur"))
        swipeLIst.add(SwipeCardModel(R.drawable.swipe_card_image,"Saurabh Thakur","23","Hamirpur"))
        swipeLIst.add(SwipeCardModel(R.drawable.swipe_card_image,"Saurabh Thakur","23","Hamirpur"))

        binding.cardStackView.layoutManager = swipeLayoutManager
        swipeAdapter = context?.let { SwipeCardAdapter(it,swipeLIst) }!!
        binding.cardStackView.adapter = swipeAdapter

        binding.cardStackView.itemAnimator.apply {

            if (this is DefaultItemAnimator){
                supportsChangeAnimations = false
            }
        }

        swipeAdapter.notifyDataSetChanged()


        binding.swipeDislikeLayout.setOnClickListener(this)
        binding.swipeLikeLayout.setOnClickListener(this)

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {

        requireContext().startActivity(Intent(requireContext(),CongratulationActivity::class.java))

    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

    override fun onClick(v: View?) {

        when(v){
            binding.swipeDislikeLayout->{
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Left)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                swipeLayoutManager.setSwipeAnimationSetting(setting)
                binding.cardStackView.swipe()
        }
            binding.swipeLikeLayout->{
                val setting = SwipeAnimationSetting.Builder()
                    .setDirection(Direction.Right)
                    .setDuration(Duration.Normal.duration)
                    .setInterpolator(AccelerateInterpolator())
                    .build()
                swipeLayoutManager.setSwipeAnimationSetting(setting)
                binding.cardStackView.swipe()
            }
        }

    }
}
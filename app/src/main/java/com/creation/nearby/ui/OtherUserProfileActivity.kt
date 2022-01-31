package com.creation.nearby.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.creation.nearby.R
import com.creation.nearby.adapter.InterestsAdapter
import com.creation.nearby.databinding.ActivityMyProfileBinding
import com.creation.nearby.databinding.ActivityOtherUserProfileBinding
import com.creation.nearby.databinding.ActivitySwipeUserProfileBinding
import com.creation.nearby.databinding.FilterBottomSheetDialogBinding
import com.creation.nearby.model.InterestedModel
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.lang.Math.abs

class OtherUserProfileActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityOtherUserProfileBinding
    private  var interestsList = ArrayList<InterestedModel>()
    private lateinit var  interestsAdapter: InterestsAdapter

    var onSwipeTouchListener: OnSwipeTouchListener? = null

    lateinit var dialogBinding: ActivitySwipeUserProfileBinding
    lateinit var fullProfileDialog: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fullProfileDialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        dialogBinding = ActivitySwipeUserProfileBinding.inflate(layoutInflater, null, false)
        fullProfileDialog.setContentView(dialogBinding.root)
        fullProfileDialog.setCancelable(true)
        fullProfileDialog.setCanceledOnTouchOutside(true)

        onSwipeTouchListener = OnSwipeTouchListener(this,fullProfileDialog, findViewById(R.id.otherUserMainLayout))

        interestsList.add(InterestedModel("Travel",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Chatting",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Athlete",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("House Parties",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Cricket",isSelected = false,isProfile = true))

        interestsAdapter = InterestsAdapter(interestsList)
        binding.profileInterestRecView.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW)
        binding.profileInterestRecView.adapter = interestsAdapter
        interestsAdapter.notifyDataSetChanged()








        binding.back.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v){
            binding.back->{
                onBackPressed()

            }
        }
    }

    class OnSwipeTouchListener internal constructor(ctx: Context,dia: BottomSheetDialog, mainView: View):View.OnTouchListener{
        private val gestureDetector: GestureDetector
        private var context: Context
        private var dialog: BottomSheetDialog
        private lateinit var onSwipe:OnSwipeListener
        init{
            gestureDetector = GestureDetector(ctx, GestureListener())
            mainView.setOnTouchListener(this)
            context = ctx
            dialog = dia
        }
        override fun onTouch(v:View, event: MotionEvent):Boolean {
            return gestureDetector.onTouchEvent(event)
        }
        private companion object {
            private const val swipeThreshold = 100
            private const val swipeVelocityThreshold = 100
        }
        inner class GestureListener:GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e:MotionEvent):Boolean {
                return true
            }
            override fun onFling(e1:MotionEvent, e2:MotionEvent, velocityX:Float, velocityY:Float):Boolean {
                var result = false
                try{
                    val diffY = e2.y - e1.y
                    val diffX = e2.x - e1.x
               /*     if (kotlin.math.abs(diffX) > kotlin.math.abs(diffY)){
                        if (kotlin.math.abs(diffX) > swipeThreshold && kotlin.math.abs(velocityX) > swipeVelocityThreshold){
                            if (diffX > 0){
                                onSwipeRight()
                            }
                            else{
                                onSwipeLeft()
                            }
                            result = true
                        }
                    }*/
                     if (kotlin.math.abs(diffY) > swipeThreshold && kotlin.math.abs(velocityY) > swipeVelocityThreshold){
                        if (diffY > 0){
                         //   onSwipeBottom()
                        }
                        else{
                            onSwipeTop()
                        }
                        result = true
                    }
                }
                catch (exception:Exception) {
                    exception.printStackTrace()
                }
                return result
            }
        }
        internal fun onSwipeRight() {
            Toast.makeText(context, "Swiped Right", Toast.LENGTH_SHORT).show()
            this.onSwipe.swipeRight()
        }
        internal fun onSwipeLeft() {
            Toast.makeText(context, "Swiped Left", Toast.LENGTH_SHORT).show()
            this.onSwipe.swipeLeft()
        }
        internal fun onSwipeTop() {
          //  Toast.makeText(context, "Swiped Up", Toast.LENGTH_SHORT).show()

            dialog.behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            dialog.behavior.skipCollapsed = true
            dialog.show()

          //  context.startActivity(Intent(context,SwipeUserProfileActivity::class.java))
           // activity.overridePendingTransition(R.anim.slide_in_bottom,R.anim.)
            this.onSwipe.swipeTop()
        }
        internal fun onSwipeBottom() {
            Toast.makeText(context, "Swiped Down", Toast.LENGTH_SHORT).show()
            this.onSwipe.swipeBottom()
        }
        internal interface OnSwipeListener {
            fun swipeRight()
            fun swipeTop()
            fun swipeBottom()
            fun swipeLeft()
        }
    }




}
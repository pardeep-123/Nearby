package com.creation.nearby.ui

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.creation.nearby.R
import com.creation.nearby.adapter.GallaryAdapter
import com.creation.nearby.adapter.InterestsAdapter
import com.creation.nearby.adapter.OtherProfileInterestAdapter
import com.creation.nearby.databinding.ActivityOtherUserProfileBinding
import com.creation.nearby.databinding.ActivitySwipeUserProfileBinding
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.*
import com.creation.nearby.viewmodel.OtherUserVM
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView

class OtherUserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtherUserProfileBinding
    private  var interestsList = ArrayList<InterestedModel>()
    private lateinit var  interestsAdapter: InterestsAdapter

   private val otherUserVM : OtherUserVM by viewModels()

    lateinit var dialogBinding: ActivitySwipeUserProfileBinding
    lateinit var fullProfileDialog: BottomSheetDialog

    private lateinit var  swipeInterestsAdapter: OtherProfileInterestAdapter

    private var galleryList = ArrayList<String>()
    private lateinit var mainGalleryAdapter: GallaryAdapter

    var userId=""
    var from = ""
    var image = ""
    var name = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherUserProfileBinding.inflate(layoutInflater)

        binding.otherUserVM = otherUserVM
        binding.lifecycleOwner = this

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(binding.root)
        userId=if (intent.getStringExtra("userId")!=null){
            intent.getStringExtra("userId")!!
        }else{
            ""
        }
        /**
         * check that from where we are coming
         */
        if (intent?.getStringExtra("from")!=null)
        if (intent?.getStringExtra("from") == "friendList"){
            otherUserVM.from.set(true)
            otherUserVM.userImage.set(intent?.getStringExtra("image")!!)
            otherUserVM.userName.set(intent?.getStringExtra("name")!!)
        }else{
            otherUserVM.from.set(false)
        }
       otherUserVM.userId.set(userId)


        interestsAdapter = InterestsAdapter(interestsList)
        binding.profileInterestRecView.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW)
        binding.profileInterestRecView.adapter = interestsAdapter
        interestsAdapter.notifyDataSetChanged()

        //full profile dialog

        fullProfileDialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        dialogBinding = ActivitySwipeUserProfileBinding.inflate(layoutInflater, null, false)
        fullProfileDialog.setContentView(dialogBinding.root)
        fullProfileDialog.setCancelable(true)
        fullProfileDialog.setCanceledOnTouchOutside(true)
        fullProfileDialog.behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
        fullProfileDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        fullProfileDialog.behavior.skipCollapsed = true


        clickHandler()

    }

    override fun onResume() {
        super.onResume()
        otherUserVM.userDetailApi(this)
        otherUserVM.userData.observeForever { response->
            setData(response)
        }
    }

    private fun setData(response: UserDetailResponse) {

       // binding.userName.text=response.body.name

        dialogBinding.gallaryRecyclerView.layoutManager = GridLayoutManager(this,3)

        /**
         * Add Interests in Array List
         */

        val intersetList = response.body.interests.split(",").toList()
        interestsList.clear()
        intersetList.forEach {
            interestsList.add(InterestedModel(it,isSelected = false,isProfile = true))
        }

        /**
         * Add Galary list
         */
        galleryList.clear()
        response.body.user_images.forEach {
            galleryList.add(it.image)
        }

        initAdapter2()

        swipeInterestsAdapter = OtherProfileInterestAdapter(interestsList)
        binding.profileInterestRecView.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW)
        binding.profileInterestRecView.adapter = swipeInterestsAdapter
    }

    private fun clickHandler() {

        binding.goBack.setOnClickListener{

            onBackPressed()

        }
//        dialogBinding.goBack.setOnClickListener{
//            fullProfileDialog.dismiss()
//        }
//        dialogBinding.blockTv.setOnClickListener{
//            optionsDialog()
//        }
//        dialogBinding.eventsCheck.setOnClickListener{
//            fullProfileDialog.dismiss()
//        }
//        dialogBinding.eventsCross.setOnClickListener{
//            fullProfileDialog.dismiss()
//        }

//        binding..setOnClickListener{
//
//            onBackPressed()
//        }
//        binding.likedBtn1.setOnClickListener{
//            fullProfileDialog.show()
//        }

    }





    private fun initAdapter2() {
        val onActionListener = object : OnActionListener<GallaryModel> {
            override fun notify(model: GallaryModel, position: Int, view: View) {

                val intent  = Intent(this@OtherUserProfileActivity,FullPictureActivity::class.java)
                val transitionName: String = getString(R.string.open_with_animation)
                val viewImage: MaterialCardView = view.findViewById(R.id.layoutCard)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@OtherUserProfileActivity,viewImage,transitionName)
                ActivityCompat.startActivity(this@OtherUserProfileActivity,intent,options.toBundle())

            }
        }
        binding.gallaryRecyclerView.layoutManager = GridLayoutManager(this,3)
        mainGalleryAdapter = GallaryAdapter(this, galleryList, onActionListener)
        binding.gallaryRecyclerView.adapter = mainGalleryAdapter
       // mainGalleryAdapter.notifyDataSetChanged()
    }

    private fun optionsDialog() {
        val dialog = BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                this,
                android.R.color.transparent
            )
        )
        dialog.setContentView(R.layout.report_bottom_sheet)

        val tvYes: TextView? = dialog.findViewById(R.id.select_photo_library)
        val tvDetail: TextView? = dialog.findViewById(R.id.select_camera)
        val tvNo: TextView? = dialog.findViewById(R.id.cancel)

        tvDetail?.text = "Are you sure you want to block?"

        tvYes?.setOnClickListener {
            dialog.dismiss()
        }

        tvNo?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

 /*   class OnSwipeTouchListener internal constructor(ctx: Context,dia: BottomSheetDialog,act:Activity, mainView: View):View.OnTouchListener{
        private val gestureDetector: GestureDetector
        private var context: Context
        private var activity: Activity
        private var dialog: BottomSheetDialog
        private lateinit var onSwipe:OnSwipeListener
        init{
            gestureDetector = GestureDetector(ctx, GestureListener())
            mainView.setOnTouchListener(this)
            context = ctx
            dialog = dia
            activity = act
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
               *//*     if (kotlin.math.abs(diffX) > kotlin.math.abs(diffY)){
                        if (kotlin.math.abs(diffX) > swipeThreshold && kotlin.math.abs(velocityX) > swipeVelocityThreshold){
                            if (diffX > 0){
                                onSwipeRight()
                            }
                            else{
                                onSwipeLeft()
                            }
                            result = true
                        }
                    }*//*
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

        internal fun onSwipeTop() {

         //   val fragment = SwipeUserProfileBottomFragment()
          //  fragment.show(supportFragmentManager,"SwipeUserProfileFragment")

            dialog.behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels
            dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            dialog.behavior.skipCollapsed = true
            dialog.show()

          //  context.startActivity(Intent(context,SwipeUserProfileActivity::class.java))
           // activity.overridePendingTransition(R.anim.slide_in_bottom,R.anim.)
            this.onSwipe.swipeTop()
        }

        internal interface OnSwipeListener {
            fun swipeTop()
            fun swipeLeft()
        }
    }

*/


}
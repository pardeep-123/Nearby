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
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.GallaryAdapter
import com.creation.nearby.adapter.InterestsAdapter
import com.creation.nearby.adapter.OtherProfileInterestAdapter
import com.creation.nearby.databinding.ActivityMyProfileBinding
import com.creation.nearby.databinding.ActivityOtherUserProfileBinding
import com.creation.nearby.databinding.ActivitySwipeUserProfileBinding
import com.creation.nearby.databinding.FilterBottomSheetDialogBinding
import com.creation.nearby.fragments.SwipeUserProfileBottomFragment
import com.creation.nearby.listeners.OnActionListener
import com.creation.nearby.model.*
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.ToastUtils
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import retrofit2.Response
import java.lang.Math.abs

class OtherUserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOtherUserProfileBinding
    private  var interestsList = ArrayList<InterestedModel>()
    private lateinit var  interestsAdapter: InterestsAdapter

   // var onSwipeTouchListener: OnSwipeTouchListener? = null

    lateinit var dialogBinding: ActivitySwipeUserProfileBinding
    lateinit var fullProfileDialog: BottomSheetDialog

    private  var swipeInterestsList = ArrayList<InterestedModel>()
    private lateinit var  swipeInterestsAdapter: OtherProfileInterestAdapter

    private var galleryList = ArrayList<GallaryModel>()
    private lateinit var galleryAdapter: GallaryAdapter
    private lateinit var mainGalleryAdapter: GallaryAdapter

    var userId=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherUserProfileBinding.inflate(layoutInflater)

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setContentView(binding.root)
        userId=if (intent.getStringExtra("userId")!=null){
            intent.getStringExtra("userId")!!
        }else{
            ""
        }

        interestsList.add(InterestedModel("Travel",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Chatting",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Athlete",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("House Parties",isSelected = false,isProfile = true))
        interestsList.add(InterestedModel("Cricket",isSelected = false,isProfile = true))

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

    //    onSwipeTouchListener = OnSwipeTouchListener(this,fullProfileDialog,this, findViewById(R.id.otherUserMainLayout))
    //    onSwipeTouchListener = OnSwipeTouchListener(this,fullProfileDialog,this, findViewById(R.id.showLayout))






        initAdapter2()

        //full profile dialog

        clickHandler()


        userDetailApi()
    }

    private fun userDetailApi() {

        try {
            CallApi().callService(
                this,
                true,
                object : RequestProcessor<Response<UserDetailResponse>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<UserDetailResponse> {
                        return retrofitApi.userDetail(userId)
                    }

                    override fun onResponse(res: Response<UserDetailResponse>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                            setData(response)
                        }
                    }

                    override fun onException(message: String?) {
                        Log.e("userException", "====$message")
                    }
                })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setData(response: UserDetailResponse) {

        binding.textView66.text=response.body.name
        response.body.user_images.forEachIndexed { index, it ->
            galleryList.add(GallaryModel(Constants.IMAGE_BASE_URL+it.image))

        }
        val onActionListener = object : OnActionListener<GallaryModel> {
            override fun notify(model: GallaryModel, position: Int, view: View) {

                val intent  = Intent(this@OtherUserProfileActivity,FullPictureActivity::class.java)
                val transitionName: String = getString(R.string.open_with_animation)
                val viewImage: MaterialCardView = view.findViewById(R.id.layoutCard)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@OtherUserProfileActivity,viewImage,transitionName)
                ActivityCompat.startActivity(this@OtherUserProfileActivity,intent,options.toBundle())
            }
        }
        dialogBinding.gallaryRecyclerView.layoutManager = GridLayoutManager(this,3)
        galleryAdapter = GallaryAdapter(this, galleryList, onActionListener)
        dialogBinding.gallaryRecyclerView.adapter = galleryAdapter


        if (!response.body.interests.isNullOrBlank()) {
            val specialisationList: MutableList<String> = response.body.interests.split(",").toList() as MutableList<String>

            specialisationList.forEach {
                swipeInterestsList.add(InterestedModel(it,isSelected = false,isProfile = true))

            }

        }



//        swipeInterestsList.add(InterestedModel("Chatting",isSelected = false,isProfile = true))
//        swipeInterestsList.add(InterestedModel("Athlete",isSelected = false,isProfile = true))
//        swipeInterestsList.add(InterestedModel("House Parties",isSelected = false,isProfile = true))
//        swipeInterestsList.add(InterestedModel("Cricket",isSelected = false,isProfile = true))

        swipeInterestsAdapter = OtherProfileInterestAdapter(swipeInterestsList)
        dialogBinding.interestRecyclerView.layoutManager = FlexboxLayoutManager(this, FlexDirection.ROW)
        dialogBinding.interestRecyclerView.adapter = swipeInterestsAdapter
        swipeInterestsAdapter.notifyDataSetChanged()
    }

    private fun clickHandler() {

        binding.backbtn1.setOnClickListener{

            onBackPressed()

        }
        dialogBinding.goBack.setOnClickListener{
            fullProfileDialog.dismiss()
        }
        dialogBinding.blockTv.setOnClickListener{
            optionsDialog()
        }
        dialogBinding.eventsCheck.setOnClickListener{
            fullProfileDialog.dismiss()
        }
        dialogBinding.eventsCross.setOnClickListener{
            fullProfileDialog.dismiss()
        }

        binding.dislikeBtn.setOnClickListener{

            onBackPressed()
        }
        binding.likedBtn1.setOnClickListener{
            fullProfileDialog.show()
        }

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
        mainGalleryAdapter.notifyDataSetChanged()
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
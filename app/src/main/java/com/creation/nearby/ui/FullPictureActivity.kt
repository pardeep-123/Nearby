package com.creation.nearby.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.creation.nearby.R
import com.creation.nearby.databinding.ActivityFullPictureBinding
import com.igreenwood.loupe.Loupe

class FullPictureActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFullPictureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullPictureBinding.inflate(layoutInflater)
        setContentView(binding.root)

     //   postponeEnterTransition()

    //    startPostponedEnterTransition()

        val loupe = Loupe.create(binding.fullPicture,binding.container){

            useFlingToDismissGesture = false

            onViewTranslateListener = object :Loupe.OnViewTranslateListener{
                override fun onDismiss(view: ImageView) {
                    finishAfterTransition()
                }

                override fun onRestore(view: ImageView) {

                }

                override fun onStart(view: ImageView) {

                }

                override fun onViewTranslate(view: ImageView, amount: Float) {

                }


            }

        }


    }
}
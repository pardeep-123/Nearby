package com.creation.nearby.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.text.format.DateFormat
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.creation.nearby.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {

    @BindingAdapter(value = ["setRecyclerAdapter"], requireAll = false)
    @JvmStatic
    fun setRecyclerAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.adapter = adapter
    }


    @JvmStatic
    @BindingAdapter(value = ["timeStampToTime"], requireAll = false)
    fun timeStampToTime(
        textView: TextView,
        zuluTime: String
    ) {
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val output = SimpleDateFormat("dd/MM/yyyy")

        var d: Date? = null
        try {
            d = input.parse(zuluTime)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val formatted = output.format(d)
        Log.i("DATE", "" + formatted)

        textView.text = formatted

    }

    @BindingAdapter(value = ["setImageSrc"], requireAll = false)
    @JvmStatic
    fun setImageSrc(
        ivImage: ImageView,
        str: String?

    ) {
        try {
            Glide.with(ivImage.context)
                .asBitmap().load(str)
                .apply(RequestOptions().override(600, 200))
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        ivImage.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        Log.e("Image", "LoadingFailed")
                        ivImage.setImageResource(R.drawable.user_icon)
                    }

                    override fun onDestroy() {
                        Log.e("Image", "destroyed")
                        ivImage.setImageResource(R.drawable.user_icon)
                    }
                })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // method for image with url
    @BindingAdapter(value = ["setImageSrcWithUrl"], requireAll = false)
    @JvmStatic
    fun setImageSrcWithUrl(
        ivImage: ImageView,
        str: String?

    ) {
        try {
            Glide.with(ivImage.context)
                .asBitmap().load("http://202.164.42.227:9022$str")
                .apply(RequestOptions().override(600, 200))
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        ivImage.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        Log.e("Image", "LoadingFailed")
                        ivImage.setImageResource(R.drawable.user_icon)
                    }

                    override fun onDestroy() {
                        Log.e("Image", "destroyed")
                        ivImage.setImageResource(R.drawable.user_icon)
                    }
                })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
package com.creation.nearby.utils

import android.content.Context
import android.widget.Toast

object ToastUtils {


    fun showToast(msg: String,context: Context){

        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

}
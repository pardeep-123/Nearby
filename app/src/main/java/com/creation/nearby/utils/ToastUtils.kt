package com.creation.nearby.utils

import android.content.Context
import android.widget.Toast
import com.creation.nearby.base.AppController

object ToastUtils {


    fun showToast(context: Context,msg: String){

        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }

}
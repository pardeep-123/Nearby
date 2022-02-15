package com.creation.nearby.utils

import android.content.Context
import android.widget.Toast
import com.creation.nearby.base.AppController

object ToastUtils {


    fun showToast(msg: String){

        Toast.makeText(AppController.mInstance,msg,Toast.LENGTH_SHORT).show()
    }

}
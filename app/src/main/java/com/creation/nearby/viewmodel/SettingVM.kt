package com.creation.nearby.viewmodel

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.CommonModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.PersonalInfoActivity
import com.creation.nearby.ui.authentication.ChangePasswordActivity
import com.creation.nearby.ui.authentication.LoginActivity
import retrofit2.Response

class SettingVM : ViewModel() {

    fun onClick(v:View, s: String){
        when (s) {
            "logout" -> {
                logoutDialog(v.context)
            }
            "changepassword" -> {
                v.context.startActivity(Intent(v.context, ChangePasswordActivity::class.java))

            }
            "personInfo" -> {
                v.context.startActivity(Intent(v.context, PersonalInfoActivity::class.java))

            }
        }
    }


     fun logoutDialog(context: Context){
        AlertDialog.Builder(context, R.style.MyDialogTheme)
            .setTitle("Log Out")
            .setMessage("Are you sure want to Quit?")
            .setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
                logoutApi(context)
            }
            .setNegativeButton("No") { dialog: DialogInterface?, i: Int ->
                dialog?.dismiss()
            }
            .show()
    }

    // function for logout api
    private fun logoutApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,
                object : RequestProcessor<Response<CommonModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<CommonModel> {

                        return retrofitApi.logoutApi()
                    }

                    override fun onResponse(res: Response<CommonModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")
                            context.startActivity(Intent(context, LoginActivity::class.java))
                            (context as Activity).finishAffinity()
                            PreferenceFile.clearPreference(context)
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
}
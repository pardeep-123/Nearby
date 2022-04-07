package com.creation.nearby.ui.authentication

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.creation.nearby.R


class NewSplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_splash)

        // Use package name which we want to check
        // Use package name which we want to check
        val isAppInstalled: Boolean = appInstalledOrNot("com.check.application")

        if (isAppInstalled) {
            //This intent will help you to launch if the package is already installed
            val LaunchIntent = packageManager
                .getLaunchIntentForPackage("com.wallet.crypto.trustapp")
            startActivity(LaunchIntent)
            Log.i("SampleLog", "Application is already installed.")
        } else {
            // Do whatever we want to do if application not installed
            // For example, Redirect to play store
            Log.i("SampleLog", "Application is not currently installed.")

            myalert()

        }
    }
    private fun appInstalledOrNot(uri: String): Boolean {
        val pm = packageManager
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
            return true
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return false
    }

    fun myalert(){
        val layoutInflater: LayoutInflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popUp: View = layoutInflater.inflate(R.layout.report_bottom_sheet, null)
        val popUpWindowReport = PopupWindow(
            popUp, ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT, true
        )
        popUpWindowReport.showAtLocation(popUp, Gravity.CENTER, 0, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            popUpWindowReport.elevation = 10f
        }
        val yesBtn: TextView = popUp.findViewById(R.id.select_photo_library)

        val dialogtext11: TextView = popUp.findViewById(R.id.select_camera)
        dialogtext11.text = "want to Install"
        yesBtn.setOnClickListener {

            popUpWindowReport.dismiss()
            val i =  Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://play.google.com/store/apps/details?id=com.wallet.crypto.trustapp");
            startActivity(i)
        }
    }
}
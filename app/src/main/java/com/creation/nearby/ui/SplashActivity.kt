package com.creation.nearby.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.R
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.ui.authentication.NewLoginActivity
import com.creation.nearby.ui.authentication.WelcomeActivity
import com.creation.nearby.utils.Constants
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fullScreen()
        setContentView(R.layout.activity_splash)

        // check the conditions to open the screens after splash screen
        /**
         * @author Pardeep Sharma
         */
        Handler(Looper.getMainLooper()).postDelayed({
           callNextScreen()
        }, 3000) // 3000 is the delayed time in milliseconds.

        // set firebase

        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(
                        "gdddfg",
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@OnCompleteListener
                }
                val token = task.result
                PreferenceFile.storeKey(this,Constants.FIREBASE_FCM_TOKEN,token)
            })
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }

    private fun fullScreen() {

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

    }

    private fun callNextScreen(){
        if (PreferenceFile.retrieveLoginData(this)!=null){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            val intent = Intent(this, NewLoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
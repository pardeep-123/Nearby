package com.creation.nearby.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.R
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.ui.authentication.WelcomeActivity

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
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
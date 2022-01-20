package com.creation.nearby.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity(),View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        login_with_email.setOnClickListener(this)
        sign_up.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v){
            login_with_email->{

                startActivity(Intent(this,LoginActivity::class.java))

            }
            sign_up->{
                startActivity(Intent(this,SignUpActivity::class.java))

            }
        }

    }


}
package com.creation.nearby.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.sign_up
import kotlinx.android.synthetic.main.activity_welcome.*

class LoginActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        forgot_password.setOnClickListener(this)
        goBack.setOnClickListener(this)
        sign_up.setOnClickListener(this)


    }

    override fun onClick(v: View?) {

        when(v){

            forgot_password->{
             startActivity(Intent(this,ForgotPasswordActivity::class.java))

            }
            goBack->{
               finish()
            }

            sign_up->{
                startActivity(Intent(this,SignUpActivity::class.java))

            }



        }

    }
}
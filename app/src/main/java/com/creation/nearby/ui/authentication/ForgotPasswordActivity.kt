package com.creation.nearby.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.creation.nearby.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        goBack_forgot.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        when(v){

            goBack_forgot->{
                finish()
            }
        }

    }
}
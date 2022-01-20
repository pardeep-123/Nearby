package com.creation.nearby.ui.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import com.creation.nearby.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.show_hide
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        goBack.setOnClickListener(this)
        show_hide_signup.setOnClickListener(this)
        show_hide_signup_confirm.setOnClickListener(this)
    }

    override fun onClick(v: View?) {


        when(v){

            goBack->{
                finish()
            }

            show_hide_signup->{

                if(show_hide_signup.text.toString() == "Display"){
                    password_signup.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    show_hide_signup.text = "Hide"
                    password_signup.setSelection(password_signup.length())
                } else{
                    password_signup.transformationMethod = PasswordTransformationMethod.getInstance()
                    show_hide_signup.text = "Display"
                    password_signup.setSelection(password_signup.length())

                }
            }

            show_hide_signup_confirm->{

                if(show_hide_signup_confirm.text.toString() == "Display"){
                    confirm_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    show_hide_signup_confirm.text = "Hide"
                    confirm_password.setSelection(confirm_password.length())
                } else{
                    confirm_password.transformationMethod = PasswordTransformationMethod.getInstance()
                    show_hide_signup_confirm.text = "Display"
                    confirm_password.setSelection(confirm_password.length())

                }
            }

            already_have_an_account->{

                startActivity(Intent(this,LoginActivity::class.java))

            }



        }

    }
}
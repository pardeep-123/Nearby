package com.creation.nearby.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.R
import com.creation.nearby.ui.HomeActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.sign_up

class LoginActivity : AppCompatActivity(),View.OnClickListener,TextWatcher {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        forgot_password.setOnClickListener(this)
        goBack.setOnClickListener(this)
        sign_up.setOnClickListener(this)
        show_hide.setOnClickListener(this)
        sign_in_login.setOnClickListener(this)


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

            show_hide->{

                if(show_hide.text.toString() == "Display"){
                    password_login.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    show_hide.text = "Hide"
                    password_login.setSelection(password_login.length())
                } else{
                    password_login.transformationMethod = PasswordTransformationMethod.getInstance()
                    show_hide.text = "Display"
                    password_login.setSelection(password_login.length())

                }

            }
            sign_in_login->{
                startActivity(Intent(this,HomeActivity::class.java))

            }



        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {



    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (email_login.text.toString().isNotEmpty()){

            sign_in_login.isEnabled = password_login.text.toString().isNotEmpty() && password_login.text.toString().length >= 7

        }else{
            sign_in_login.isEnabled = false
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }
}
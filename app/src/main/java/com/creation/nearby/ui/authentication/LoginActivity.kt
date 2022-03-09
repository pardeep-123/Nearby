package com.creation.nearby.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.R
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.ActivityLoginBinding
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.FacebookHelper
import com.creation.nearby.utils.GoogleHelper
import com.creation.nearby.viewmodel.LoginVm
import com.facebook.FacebookException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging


class LoginActivity : AppCompatActivity(), View.OnClickListener,  FacebookHelper.FacebookHelperCallback {
    lateinit var binding: ActivityLoginBinding
    val loginVm: LoginVm by viewModels()
    private var isChecked = false

    private var facebookHelper: FacebookHelper? = null

    lateinit var googleHelper: GoogleHelper
    private var socialLoginType = ""
    var firstName = ""
    var lastName = ""
    var socialEmail = ""
    var socialId = ""
    var socialImage = ""
    var socialtype = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginVM = loginVm
        binding.showHide.setOnClickListener(this)
        binding.rememberCheckbox.setOnClickListener(this)
        binding.fbBtn.setOnClickListener(this)
        binding.googleBtn.setOnClickListener(this)
        facebookHelper = FacebookHelper(this, this)
        googleLogin()


        // set firebase

        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(
                        "gg",
                        "Fetching FCM registration token failed",
                        task.exception
                    )
                    return@OnCompleteListener
                }
                val token = task.result
                PreferenceFile.storeKey(this, Constants.FIREBASE_FCM_TOKEN,token)
            })
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }

    private fun googleLogin() {
        googleHelper = GoogleHelper(this, object : GoogleHelper.GoogleHelperCallback {
            override fun onSuccessGoogle(account: GoogleSignInAccount) {
                try {
                    var photo = ""
                    if (account.photoUrl != null) {
                        photo = account.photoUrl.toString()
                    }

                    googleHelper.signOut()
                    val fatchName = account.displayName!!.split(" ")

                    try {
                        var firstNames = ""
                        for (i in 0 until fatchName.size - 1) {
                            firstNames = if (firstNames.isEmpty()) {
                                fatchName[i]
                            } else {
                                firstNames + " " + fatchName[i]
                            }
                        }

                        socialtype = "1"
                        firstName = firstNames
                        lastName = fatchName[fatchName.size - 1]
                        socialEmail = account.email!!
                        socialId = account.id!!
                        socialImage = photo

                        // hit api here

                        loginVm.socialLoginApi(this@LoginActivity,firstName+lastName,socialEmail,socialId,socialtype)

                    } catch (e: Exception) {
                    }
                } catch (ex: Exception) {
                    ex.localizedMessage
                }
            }

            override fun onErrorGoogle() {}
        })


    }


    override fun onClick(v: View?) {

        when (v) {

            binding.showHide -> {

                if (binding.showHide.text.toString() == "Display") {
                    binding.passwordLogin.transformationMethod =
                        HideReturnsTransformationMethod.getInstance()
                    binding.showHide.text = "Hide"
                    binding.passwordLogin.setSelection(binding.passwordLogin.length())
                } else {
                    binding.passwordLogin.transformationMethod =
                        PasswordTransformationMethod.getInstance()
                    binding.showHide.text = "Display"
                    binding.passwordLogin.setSelection(binding.passwordLogin.length())

                }

            }

            binding.rememberCheckbox -> {
                isChecked = !isChecked
                if (isChecked)
                    binding.rememberCheckbox.setImageResource(R.drawable.checked)
                else
                    binding.rememberCheckbox.setImageResource(R.drawable.unchecked)
            }

            // set click listner on fb button
            binding.fbBtn -> {
                socialLoginType = "Fb"
                facebookHelper!!.login(this)

            }

            // set click listner on google button
            binding.googleBtn -> {
                signIn()
            }
        }

    }

    private fun signIn() {
        socialLoginType = "Google"
        googleHelper.signIn()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (socialLoginType == "Fb")
            facebookHelper!!.onResult(requestCode, resultCode, data)
        else if (socialLoginType == "Google")
            googleHelper.onResult(requestCode, resultCode, data)
    }

    override fun onSuccessFacebook(bundle: Bundle?) {
        firstName = bundle!!.getString(FacebookHelper.FIRST_NAME)!!
        lastName = bundle.getString(FacebookHelper.LAST_NAME)!!

        socialEmail = if (bundle.getString(FacebookHelper.EMAIL) != null){

            bundle.getString(FacebookHelper.EMAIL)!!
        }else{
            "$socialId@gmail.com"
        }
        socialId = bundle.getString(FacebookHelper.FACEBOOK_ID)!!
        socialImage = bundle.getString(FacebookHelper.PROFILE_PIC)!!
        socialtype = "2"

        loginVm.socialLoginApi(this,firstName+lastName,socialEmail,socialId,socialtype)
    }

    override fun onCancelFacebook() {
     }

    override fun onErrorFacebook(ex: FacebookException?) {
     }

}
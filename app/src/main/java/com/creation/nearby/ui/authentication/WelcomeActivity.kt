package com.creation.nearby.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.R
import com.creation.nearby.animateFade
import com.creation.nearby.databinding.ActivityWelcomeBinding
import com.creation.nearby.utils.FacebookHelper
import com.creation.nearby.utils.GoogleHelper
import com.creation.nearby.viewmodel.LoginVm
import com.facebook.FacebookException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

class WelcomeActivity : AppCompatActivity(),View.OnClickListener, FacebookHelper.FacebookHelperCallback{

    lateinit var binding: ActivityWelcomeBinding
    val loginVm : LoginVm by viewModels()

    var facebookHelper: FacebookHelper? = null

    lateinit var googleHelper: GoogleHelper
    var socialLoginType = ""
    var firstName = ""
    var lastName = ""
    var socialEmail = ""
    var socialId = ""
    var socialImage = ""
    var socialtype = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginVM = loginVm
        binding.loginWithEmail.setOnClickListener(this)
        binding.signUp.setOnClickListener(this)
        binding.fbBtn.setOnClickListener(this)
        binding.googleBtn.setOnClickListener(this)

        facebookHelper = FacebookHelper(this, this)
        googleLogin()

    }

    override fun onClick(v: View?) {

        when(v){
            binding.loginWithEmail->{
                startActivity(Intent(this,LoginActivity::class.java))
                animateFade(this)
            }
            binding.signUp->{
                startActivity(Intent(this,SignUpActivity::class.java))
                animateFade(this)
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

                        loginVm.socialLoginApi(this@WelcomeActivity,firstName+lastName,socialEmail,socialId,socialtype)

                    } catch (e: Exception) {
                    }
                } catch (ex: Exception) {
                    ex.localizedMessage
                }
            }

            override fun onErrorGoogle() {}
        })


    }
}
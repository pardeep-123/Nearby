package com.creation.nearby.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.animateSlide
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.ActivityOtpBinding
import com.creation.nearby.model.auth.LoginModel
import com.creation.nearby.showToast
import com.creation.nearby.ui.MainActivity
import com.creation.nearby.utils.Constants

class OtpActivity : AppCompatActivity() {

    var loginModel : LoginModel?=null
    lateinit var binding : ActivityOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginModel = intent.getParcelableExtra("data")!!

        showToast(loginModel?.body?.otp.toString())
      setClicks()
    }

    private fun setClicks() {
        binding.confirmCodeBtn.setOnClickListener {
            when {
                binding.pinViewOtp.text.toString().isEmpty() -> {
                    showToast("Fill Otp First")
                }
                loginModel?.body?.otp.toString() != binding.pinViewOtp.text.toString() -> {
                    showToast("Enter Correct Otp")
                }
                else -> {
                    startActivity(Intent(this, ThankYouActivity::class.java))
                    animateSlide(this)
                    finishAffinity()
                    saveData()
                }
            }
            }
        }


    private fun saveData(){
        PreferenceFile.storeLoginData(this, loginModel!!)
        PreferenceFile.storeKey(this,"username",loginModel?.body?.firstname+" "+loginModel?.body?.lastname)
        PreferenceFile.storeKey(this, Constants.USER_IMAGE,loginModel?.body?.image!!)
        PreferenceFile.storeKey(this, Constants.IS_FIRST_LOGIN,loginModel?.body?.firstLogin!!)
        PreferenceFile.storeKey(this, Constants.AUTH_KEY,"Bearer "+loginModel?.body?.token)
        PreferenceFile.storeUserId(this,loginModel?.body?.id.toString())
    }
}
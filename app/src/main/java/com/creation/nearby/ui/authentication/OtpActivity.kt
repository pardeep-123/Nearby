package com.creation.nearby.ui.authentication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.animateFade
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.ActivityOtpBinding
import com.creation.nearby.model.auth.LoginModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.showToast
import com.creation.nearby.utils.Constants
import retrofit2.Response
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {

    var loginModel : LoginModel?=null
    lateinit var binding : ActivityOtpBinding
   private var otp = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginModel = intent.getParcelableExtra("data")!!
         otp = loginModel?.body?.otp.toString()

      setClicks()
        resendTimingDialog()
    }

    private fun setClicks() {
        binding.confirmCodeBtn.setOnClickListener {
            when {
                binding.pinViewOtp.text.toString().isEmpty() -> {
                    showToast("Fill Otp First")
                }
                  otp!= binding.pinViewOtp.text.toString() -> {
                    showToast("Enter Correct Otp")
                }
                else -> {
                    startActivity(Intent(this, ThankYouActivity::class.java))
                    animateFade(this)
                    finishAffinity()
                    saveData()
                }
            }
            }
        binding.goBack.setOnClickListener {
            finish()
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

    private fun resendTimingDialog() {

        val timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

               binding.tvResend.text ="Resend my SMS code "+"(0:"+ String.format("%d", TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished))+")"

            }

            override fun onFinish() {
                loginApi(this@OtpActivity)
            }
        }
        timer.start()

    }

    // function for hashmap
    fun mapValues(context: Context): HashMap<String, String> {

        val hashMap = HashMap<String, String>()

        hashMap["phone"] = loginModel?.body?.phone!!
        hashMap["countryCode"] = loginModel?.body?.countryCode!!
        hashMap["deviceToken"] = PreferenceFile.retrieveKey(context,Constants.FIREBASE_FCM_TOKEN).toString()
        // hashMap["deviceToken"] = PreferenceFile.retrieveFcmDeviceId(context)
        hashMap["deviceType"] = "1"

        return hashMap
    }

    private fun loginApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<LoginModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<LoginModel> {

                        return retrofitApi.loginApiNew(
                            mapValues(context)
                        )
                    }

                    override fun onResponse(res: Response<LoginModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            otp = response.body.otp.toString()
                            showToast("Otp Code has been Sent to your Number")
                        }
                    }

                    override fun onException(message: String?) {
                        Log.e("userException", "====$message")
                    }
                })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
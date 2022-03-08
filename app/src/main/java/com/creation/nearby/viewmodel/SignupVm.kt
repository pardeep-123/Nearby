package com.creation.nearby.viewmodel

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.creation.nearby.R
import com.creation.nearby.animateFade
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.model.auth.LoginModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.PrivacyPolicyActivity
import com.creation.nearby.ui.TermsOfUse
import com.creation.nearby.ui.authentication.ForgotPasswordActivity
import com.creation.nearby.ui.authentication.LoginActivity
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.ToastUtils
import com.creation.nearby.utils.Validator
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class SignupVm : ViewModel() {

    // decalre variables
    var firstname: ObservableField<String> = ObservableField("")
    var lastname: ObservableField<String> = ObservableField("")
    var email: ObservableField<String> = ObservableField("")
    var location: ObservableField<String> = ObservableField("")
    var latitude: ObservableField<String> = ObservableField("")
    var longitude: ObservableField<String> = ObservableField("")
    var password: ObservableField<String> = ObservableField("")
    var confirmPassword: ObservableField<String> = ObservableField("")

    // create function for onclicks
    fun onClick(v: View, s:String){
        when (s) {
            "signup" -> {
                validateSignup(v.context)
            }
            "terms" -> {
                v.context.startActivity(Intent(v.context, TermsOfUse::class.java))
                animateFade(v.context as Activity)
            }
            "privacy" -> {
                v.context.startActivity(Intent(v.context, PrivacyPolicyActivity::class.java))
                animateFade(v.context as Activity)
            }
            "login" ->{
                v.context.startActivity(Intent(v.context, LoginActivity::class.java))
                (v.context as Activity).finishAffinity()
                animateFade(v.context as Activity)
            }
        }
    }

    // function for validation check
    private fun validateSignup(context: Context){
        if (Validator().validateRegister(
                firstname.get()!!,
                lastname.get()!!,
                email.get()!!,
                password.get()!!,
                confirmPassword.get()!!
            )
        ) {


            signUpApi(context)

        } else {
            ToastUtils.showToast((context as Activity),Validator.ErrorMessage)
        }
    }
    private fun signUpApi(context: Context) {
        try {

            CallApi().callService(
                context,
                true,

                object : RequestProcessor<Response<LoginModel>> {
                    override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<LoginModel> {

                        return retrofitApi.signup(
                            mapValues(context)
                        )

                    }

                    override fun onResponse(res: Response<LoginModel>) {
                        if (res.isSuccessful) {
                            val response = res.body()!!
                            Log.e("isSuccess", "====$response")

                            confirmationDialog(context)
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

    // function for hashmap
    fun mapValues(context: Context) : HashMap<String,String>{

     val hashMap = HashMap<String,String>()

        hashMap["email"] = email.get()!!
        hashMap["firstname"] = firstname.get()!!
        hashMap["lastname"] = lastname.get()!!
        hashMap["password"] = password.get()!!
        hashMap["location"] = location.get()!!
        hashMap["latitude"] = latitude.get()!!
        hashMap["longitude"] = longitude.get()!!
        hashMap["deviceToken"] = PreferenceFile.retrieveKey(context, Constants.FIREBASE_FCM_TOKEN).toString()
       // hashMap["deviceToken"] = PreferenceFile.retrieveFcmDeviceId(context)
        hashMap["deviceType"] = "1"

        return hashMap
    }

    private fun confirmationDialog(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.setBackgroundDrawable(
            ContextCompat.getDrawable(
                context,
                android.R.color.transparent
            )
        )
        dialog.setContentView(R.layout.send_verification_dialog)

        val ok: AppCompatButton? = dialog.findViewById(R.id.ok)

        ok?.setOnClickListener {
            dialog.dismiss()
           // context.startActivity(Intent(context, LoginActivity::class.java))
            (context as Activity).finish()
        }


        dialog.show()
    }
}
package com.creation.nearby.retrofit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AlertDialogLayout
import com.creation.nearby.R
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.ui.authentication.NewLoginActivity
import com.creation.nearby.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CallApi {

    fun <T> callService(
        mContext: Context,
        dialogFlag: Boolean,
        requestProcessor: RequestProcessor<T>
    ) {
        try {
            val authKey = PreferenceFile.retrieveKey(mContext,Constants.AUTH_KEY)
            if (dialogFlag) {
                showDialog(mContext)
            }

            val okHttpClient: OkHttpClient?
            if (authKey.isNullOrEmpty()) {

                val client = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
                    OkHttpClient.Builder()
                        .readTimeout(10, TimeUnit.MINUTES)
                        .connectTimeout(10, TimeUnit.MINUTES)
                        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .addInterceptor { chain ->
                            val original = chain.request()

                            val request = original.newBuilder()
                                .method(original.method, original.body)
                                .build()

                            chain.proceed(request)
                        }
                } else {
                    TODO("VERSION.SDK_INT < GINGERBREAD")
                }

                okHttpClient = client.build()
            } else {
                val client = OkHttpClient.Builder()
                    .readTimeout(10, TimeUnit.MINUTES)
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .addInterceptor { chain ->
                        val original = chain.request()

                        val request = original.newBuilder()
                            .header("Authorization", authKey)
                            .method(original.method, original.body)
                            .build()

                        chain.proceed(request)
                    }

                okHttpClient = client.build()

            }

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())

                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            val retrofitApi = retrofit.create(RetrofitInterface::class.java)

            val coRoutineExceptionHandler = CoroutineExceptionHandler { _, t ->
                t.printStackTrace()

                CoroutineScope(Dispatchers.Main).launch {
                    hideDialog()
                    requestProcessor.onException(t.message)
                    t.printStackTrace()
                    Log.e("apiResponse", "=11===" + t.message.toString())
                    if (t.message.equals("Unable to resolve host")) {
                      //  CommonMethods.centerToast(t.message.toString())
                       Toast.makeText(mContext,t.message.toString(),Toast.LENGTH_LONG).show()
                    } else {
                        //timeout
                        Log.e("apiResponse", "=11===" + t.message.toString())
                        Log.e("apiResponse", "=11===" + t.cause.toString())
                        Log.e("apiResponse", "=11===" + t.suppressed.toString())
                       // CommonMethods.centerToast(mContext.resources.getString(R.string.server_error))
                        Toast.makeText(mContext,"Server Error",Toast.LENGTH_LONG).show()
                    }
                }
            }

            CoroutineScope(Dispatchers.IO + coRoutineExceptionHandler).launch {

                val response = requestProcessor.sendRequest(retrofitApi) as Response<*>

                CoroutineScope(Dispatchers.Main).launch {
                    hideDialog()
                    requestProcessor.onResponse(response as T)
                    Log.e("apiResponse", "=11===" + response.body().toString())
                    Log.e("apiResponse", "=22===" + response.code().toString())
                    Log.e("apiResponse", "=33===" + response.raw().message)
                    Log.e("apiResponse", "=44===$response")
                    if (!response.isSuccessful) {
                        val res = response.errorBody()!!.string()

                        //log
                        val jsonObject2 = JSONObject(res)

                        /* if (response.raw().code.toString() == "403") {
                            mContext.startActivity(Intent(mContext, LoginActivity::class.java))
                            CommonMethods.centerToast( "Unauthorized")

                        } else {*/
                        val jsonObject = JSONObject(res)
                        when {
                            jsonObject.has("message") && !jsonObject.isNull("message") -> {
                                val message = jsonObject.getString("message")

                                if (jsonObject.getString("message") == "Invalid Authorization Key" ||
                                    jsonObject.getString("message") =="Authorization is required.") {
                                    Toast.makeText(mContext,message.toString(),Toast.LENGTH_LONG).show()

                                    PreferenceFile.clearPreference(mContext)

                                    (mContext as Activity).finishAffinity()
                                    mContext.startActivity(
                                        Intent(
                                            mContext,
                                            NewLoginActivity::class.java
                                        )
                                    )
                                }
                               /* else if (jsonObject.getInt("code") == 400) {
                                    ToastUtils.showToast(mContext,jsonObject.getString("message").toString())
                                   Log.e("====","behjdbchjbdcdc")
                                }*/
                                else {
                                    val errorMsg= jsonObject.getString("message")
                                    Toast.makeText(mContext,errorMsg.toString(),Toast.LENGTH_LONG).show()

                                }


                            }

                        }
                        /*}*/
                    }

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: Throwable) {
        }
    }


    lateinit var dialogBuilderMain: AlertDialog

    private fun showDialog(mContext: Context) {
        val dialogBuilder = AlertDialog.Builder(mContext)
        val layout = AlertDialogLayout.inflate(mContext, R.layout.dialog_loading, null)
        dialogBuilder.setView(layout)

        dialogBuilderMain = dialogBuilder.create()
        dialogBuilderMain.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBuilderMain.setCancelable(false)
        dialogBuilderMain.setCanceledOnTouchOutside(false)
        dialogBuilderMain.show()
    }


    private fun hideDialog() {
        if (this::dialogBuilderMain.isInitialized && dialogBuilderMain.isShowing) {
            dialogBuilderMain.dismiss()
        }
    }

}

package com.creation.nearby.ui.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.creation.nearby.R
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.ActivityNewLoginBinding
import com.creation.nearby.showToast
import com.creation.nearby.utils.Constants
import com.creation.nearby.viewmodel.LoginVm
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_new_login.*
import org.json.JSONObject
import java.net.URL

class NewLoginActivity : AppCompatActivity(),GraphRequest.GraphJSONObjectCallback {
     val loginVm : LoginVm by viewModels()
    private var mCallbackManager: CallbackManager? = null
    private var googleSignInClient: GoogleSignInClient? = null
   lateinit var binding : ActivityNewLoginBinding
    var firstName :String?=null
    var lastName :String?=null
    var socialEmail :String?=null
    var socialId = ""
    var socialImage = ""
    var socialtype = ""

    private val googleLoginActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleSignInResult(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginVM = loginVm
        binding.lifecycleOwner = this
        mCallbackManager = CallbackManager.Factory.create()

        fbIcon?.setOnClickListener {
            LoginManager.getInstance().logOut()
            fbLogin()
        }

        googleIcon?.setOnClickListener {
            signIn()
        }

        // set country picker
        ccp.setDefaultCountryUsingNameCode("CA")
        ccp.setOnCountryChangeListener {
            ccp.selectedCountryCode
            ccp.showFlag(false)
            ccp.enableHint(true)
            ccp.setKeyboardAutoPopOnSearch(false)
            loginVm.password.set(ccp.selectedCountryCode)
        }
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
    private fun signIn() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        val intentGoogle = googleSignInClient?.signInIntent
        googleLoginActivityResult.launch(intentGoogle)
// googleHelper.signIn()

    }
    // for google
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

// Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
// The ApiException status code indicates the detailed failure reason.
// Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("GoogleLogin", "signInResult:failed code=" + e.statusCode)
        }
    }

    // for google
    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {

            firstName = account.displayName
            socialEmail = account.email
            val familyName = account.familyName
            val idToken = account.idToken
            socialId = account.id.toString()
            socialImage = account.photoUrl.toString()
            val givenName = account.givenName
            socialtype = "1"
            googleSignInClient?.signOut()

// api hit
            if (firstName != null && socialEmail != null) {
                loginVm.socialLoginApi(this,firstName+lastName,socialEmail!!,socialId,socialtype)


            } else {
                showToast("Google error")
            }

        } else {
            showToast("Google error")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    // for facebook
    private fun fbLogin() {
        val loginButton = LoginButton(this)
        loginButton.setPermissions(listOf("public_profile, email"))
        loginButton.performClick()
        LoginManager.getInstance()
            .registerCallback(mCallbackManager, object : FacebookCallback<LoginResult?>{
                override fun onCancel() {
                    Log.e("FacebookLogin", "Cancel Facebook Login")
                }

                override fun onError(error: FacebookException) {
                    Log.e("FacebookLogin", error.message.toString())
                }

                override fun onSuccess(result: LoginResult?) {
                    val request = GraphRequest.newMeRequest(
                        result!!.accessToken,
                        this@NewLoginActivity
                    )
                    val parameters = Bundle()
                    parameters.putString("fields", "id, first_name, last_name, email")
                    request.parameters = parameters
                    request.executeAsync()

                }

            })
    }
    // facebook completion
    override fun onCompleted(obj: JSONObject?, response: GraphResponse?) {
        Log.e("FacebookLogin", response.toString())

// Application code
        if (obj != null) {

            socialId = obj.getString("id")

            socialImage =
                URL("https://graph.facebook.com/$socialId/picture?width=200&height=150").toString()
            Log.i("profile_pic", socialImage.toString() + "")

            if (obj.has("email")) {
                socialEmail = obj.getString("email")
            }

            if (obj.has("birthday")) {
                val birthday: String = obj.getString("birthday") // 01/31/1980 format
            }

            if (obj.has("first_name")) {
                firstName = obj.getString("first_name")
            }

            if (obj.has("last_name")) {
                lastName = obj.getString("last_name")
            }
            socialtype = "2"
// api hit

            loginVm.socialLoginApi(this,firstName+lastName,socialEmail!!,socialId,socialtype)
        }
    }
}
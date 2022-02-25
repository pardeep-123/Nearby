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
import com.creation.nearby.databinding.ActivityLoginBinding
import com.creation.nearby.showToast
import com.creation.nearby.utils.FacebookAuth
import com.creation.nearby.viewmodel.LoginVm
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject


class LoginActivity : AppCompatActivity(),View.OnClickListener, FacebookAuth.FbResult {
    lateinit var binding: ActivityLoginBinding
    val loginVm : LoginVm by viewModels()
    var isChecked = false

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var mCallbackManager: CallbackManager
    private lateinit var loginButton: LoginButton
    private lateinit var facebookAuth: FacebookAuth
    private val rcSignIn: Int = 231

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginVM = loginVm
        binding.showHide.setOnClickListener(this)
        binding.rememberCheckbox.setOnClickListener(this)
        binding.fbBtn.setOnClickListener(this)

        /*
        facebook code starts from here
         */
        loginButton = LoginButton(this)
        loginButton.setPermissions(listOf("email", "public_profile"))
        mCallbackManager = CallbackManager.Factory.create()
        facebookAuth = FacebookAuth(loginButton, mCallbackManager, this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val account = GoogleSignIn.getLastSignedInAccount(this)  //Check last Signed account
        if (account != null) {
            signOut()
        }

        loginButton.setOnClickListener {
            LoginManager.getInstance().logOut()
            facebookAuth.allowUserToFacebookLogin()
        }
    }

    override fun onClick(v: View?) {

        when(v){

            binding.showHide->{

                if(binding.showHide.text.toString() == "Display"){
                    binding.passwordLogin.transformationMethod = HideReturnsTransformationMethod.getInstance()
                    binding.showHide.text = "Hide"
                    binding.passwordLogin.setSelection(binding.passwordLogin.length())
                } else{
                    binding.passwordLogin.transformationMethod = PasswordTransformationMethod.getInstance()
                    binding.showHide.text = "Display"
                    binding.passwordLogin.setSelection(binding.passwordLogin.length())

                }

            }

            binding.rememberCheckbox->{
                isChecked = !isChecked
                if (isChecked)
                    binding.rememberCheckbox.setImageResource(R.drawable.checked)
                else
                    binding.rememberCheckbox.setImageResource(R.drawable.unchecked)
            }

        // set click listner on fb button
            binding.fbBtn->{
                loginButton.performClick()

            }

        }

    }

    override fun fbResult(token: String, jsonObj: JSONObject) {
        val facebookId = jsonObj.optString("id")
        val fbEmail = jsonObj.optString("email")
        val fbName = jsonObj.optString("name")
        val picObject: JSONObject = jsonObj.getJSONObject("picture")
        val data: JSONObject = picObject.getJSONObject("data")
        val imgUrl = data.getString("url")

        //perform task here
    }
    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, rcSignIn)
    }

    private fun signOut() {
        mGoogleSignInClient?.signOut()?.addOnCompleteListener(this) {
            Log.e("JSON", "Logout")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (loginButton.hasOnClickListeners()) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data)
        }

        if (requestCode == rcSignIn) {
            Log.e("SocialResultCode", "$resultCode---$requestCode")
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }
    }
    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        if (completedTask.isSuccessful) {
            Log.e("UserLoginActivity", "Google$completedTask")
            try {
                val account = completedTask.getResult(ApiException::class.java)
                Log.e(
                    "JSON",
                    account?.id + " " + account?.email + " " + account?.displayName + " " + account?.photoUrl
                            + "Token" + account?.idToken
                )

                //perform task here


            } catch (e: ApiException) {
                Log.e("JSON", e.toString())
            }
        } else {
            Log.e("UserLoginActivity", "Google Cancel")
            showToast("Google Cancel")
        }
    }


//    private fun loginFnGoogle(map: HashMap<String, RequestBody>, imagenPerfil: MultipartBody.Part) {
//        loginViewModel.socialLogin(getSecurityKey(context)!!, map, imagenPerfil)
//        setSocialLoginObservables()
//    }

//    private fun setSocialLoginObservables() {
//        loginViewModel.loginObservable.observe(this, Observer {
//            saveUser(this, it.data!!)
//
//            if (it?.data.phone.isNullOrEmpty()) {
//                val intent = Intent(this, SignupActivity::class.java)
//                intent.putExtra("screen_type", "social")
//                startActivity(intent)
//                finish()
//            } else {
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
//                finishAffinity()
//            }
//        })
//    }

    fun createRequestBody(param: String): RequestBody {
        val request=  RequestBody.create("text/plain".toMediaTypeOrNull(), param)
        return request
    }

    override fun onFbCancel() {
        showToast("cancel")
    }
}
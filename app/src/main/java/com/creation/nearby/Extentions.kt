package com.creation.nearby

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.creation.nearby.ui.authentication.NewLoginActivity
import com.github.chrisbanes.photoview.PhotoView
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
Created by Pardeep Sharma on 15/02/2022
*/
fun delay(millis: Long, runnable: () -> Unit) {
    Handler().postDelayed({
        runnable.invoke()
    }, millis)
}
fun Context.isValidGlideContext() = this !is Activity || (!this.isDestroyed && !this.isFinishing)



fun Activity.open(activityToOpen: Class<*>) {
    startActivity(Intent(this, activityToOpen))
    finish()

}

fun Activity.openwithback(activityToOpen: Class<*>) {
    startActivity(Intent(this, activityToOpen))
// Bungee.slideLeft(this)
}

fun Activity.openWithAlpha(activityToOpen: Class<*>, finish: Boolean = true) {
    startActivity(Intent(this, activityToOpen))
// Bungee.fade(this)
    if (finish) finish()
}

fun Fragment.openfromfragment(activityToOpen: Class<*>, finish: Boolean = true) =
    activity?.openWithAlpha(activityToOpen, finish)

fun View.click(callback: () -> Unit) {
    setOnClickListener {
        callback()
    }
}

fun Activity.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun Fragment.showToast(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
fun View.isVisible() {
    visibility = View.VISIBLE
}

fun View.isGone() {
    visibility = View.GONE
}



/**
 * Shows the Snackbar inside an Activity or Fragment
 */

fun View.mySnack(messageRes: String, length: Int = Snackbar.LENGTH_SHORT, f: Snackbar.() -> Unit) {
    val snackBar = Snackbar.make(this, messageRes, length)
    snackBar.f()
    snackBar.show()
}

/**
 * Loads URL into an ImageView using Glide
 *
 * @param url URL to be loaded
 */
fun ImageView.loadFromUrl(url: String, context: Context) {
    Glide.with(context).load(url).placeholder(R.drawable.user).into(this)
}

/**
 * LOads static photo when image view is null or empty
 */
fun ImageView.load(context: Context) {
    Glide.with(context).load(R.drawable.user).into(this)
}

// for text change listener
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}
// for text change listener
fun EditText.onTextChanged(onTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(s.toString())
        }

        override fun afterTextChanged(editable: Editable?) {
            //afterTextChanged.invoke(editable.toString())
        }
    })
}
fun hidekeyboard(activity: Activity?){
    val view: View? = activity?.currentFocus

    val imm =
        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

//Pop UP window for simple Okay Button
fun myalert(context: Context, @StringRes messageRes: Int){
    val layoutInflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val popUp: View = layoutInflater.inflate(R.layout.report_bottom_sheet, null)
    val popUpWindowReport = PopupWindow(
        popUp, ConstraintLayout.LayoutParams.MATCH_PARENT,
        ConstraintLayout.LayoutParams.MATCH_PARENT, true
    )
    popUpWindowReport.showAtLocation(popUp, Gravity.CENTER, 0, 0)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

        popUpWindowReport.elevation = 10f
    }
    val yesBtn: TextView = popUp.findViewById(R.id.select_photo_library)

    val dialogtext11: TextView = popUp.findViewById(R.id.select_camera)
    dialogtext11.setText(messageRes)
    yesBtn.setOnClickListener {

        popUpWindowReport.dismiss()

    }
}
/**
 * Method for Progress Dialog
 */
fun progress(ctx: Context){
    val progressDialog = ProgressDialog(ctx)
    progressDialog.setMessage("Please wait...")
    progressDialog.setCancelable(false)
    progressDialog.show()
}

fun showPermissionIntent(str: String, ctx: Activity){
    val alertDialogBuilder: android.app.AlertDialog.Builder =  android.app.AlertDialog.Builder(ctx)
    var message = ""


    if (str ==  Manifest.permission.CAMERA){
        message = "Camera permission needed for profile picture"
    }else if (str ==  Manifest.permission.WRITE_EXTERNAL_STORAGE || str ==  Manifest.permission.READ_EXTERNAL_STORAGE){
        message = "Storage  permission needed for profile picture"
    }
    alertDialogBuilder.setTitle("Permission needed")

    alertDialogBuilder.setMessage(message)
    alertDialogBuilder.setPositiveButton(
        "Open Setting"
    ) { dialogInterface, _ ->
        dialogInterface.dismiss()
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts(
            "package", ctx.packageName,
            null
        )
        intent.data = uri
        ctx.startActivity(intent)
    }
    alertDialogBuilder.setNegativeButton(
        "Cancel"
    ) { dialogInterface, i ->
        Log.d(
            "TAG",
            "onClick: Cancelling"
        )
        dialogInterface.dismiss()
    }

    val dialog: android.app.AlertDialog = alertDialogBuilder.create()
    dialog.show()
}

    fun locationPermission(ctx: Activity) {
    ActivityCompat.requestPermissions(
        ctx,
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        PackageManager.PERMISSION_GRANTED
    )
}

/**
 * Method for Opening Images
 */
fun openImagePopUp(pos: String?, ctx: Context) {

    val popup: View
    val layoutInflater: LayoutInflater =
        ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    if (layoutInflater!= null) {
        popup = layoutInflater.inflate(R.layout.profileimage_popup, null)
        val popupWindow = PopupWindow(
            popup,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            true
        )
        popupWindow.showAtLocation(popup, Gravity.CENTER, 0, 0)
        popupWindow.isTouchable = false
        popupWindow.isOutsideTouchable = false
        val headImagePopUp = popup.findViewById<PhotoView>(R.id.headImagePopUp)
        val backpress = popup.findViewById<ImageView>(R.id.backpress)
        backpress.setOnClickListener {
            popupWindow.dismiss()
        }

        Glide.with(ctx).load(pos).into(headImagePopUp)

    }
}

//fun invalidAuthToken(context: Context, activity: Activity) {
//    var sharedPreferences: PreferencesShared? = null
//    sharedPreferences = PreferencesShared(context)
//    val dialog = AlertDialog.Builder(context)
//    dialog.setTitle("Session Expired")
//    dialog.setCancelable(false)
//    dialog.setMessage("Your Session is expired.Please Login again")
//    dialog.setPositiveButton("OK") { dialog, _ ->
//        sharedPreferences.clearShared()
//        val intent = Intent(context, LoginActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//        context.startActivity(intent)
//        dialog.dismiss()
//        activity.finishAffinity()
//    }
//    dialog.show()
//}

/**
Check Email Pattern is Valid
 **/
fun isEmailPatternValid(pView: String): Boolean {
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(pView.trim()).matches()) {
        return false
    }
    return true
}

fun animateFade(activity:Activity) {
    activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
}


fun animateSlide(activity: Activity) {
    activity.overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
}

 fun confirmationDialog(context: Context) {
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
        context.startActivity(Intent(context, NewLoginActivity::class.java))
        (context as Activity).finish()
    }


    dialog.show()
}

// automatically date and time formatting by get api
fun getNotificationTime(time_stamp: Long): String {
    var date: Date? = null
    try {
        date = Date(time_stamp * 1000)
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    System.out.println("dateeee" + date.toString())
    var string_date = ""
    val current = Calendar.getInstance().time
    var diffInSeconds = (current.time - date!!.time) / 1000
    val sec = if (diffInSeconds >= 60) diffInSeconds % 60 else diffInSeconds
    val min = if ((diffInSeconds / 60).also {
            diffInSeconds = it
        } >= 60) diffInSeconds % 60 else diffInSeconds
    val hrs = if ((diffInSeconds / 60).also {
            diffInSeconds = it
        } >= 24) diffInSeconds % 24 else diffInSeconds
    val days = if ((diffInSeconds / 24).also {
            diffInSeconds = it
        } >= 30) diffInSeconds % 30 else diffInSeconds
    val weeks = days / 7
    val months = if ((diffInSeconds / 30).also {
            diffInSeconds = it
        } >= 12) diffInSeconds % 12 else diffInSeconds
    val years = (diffInSeconds / 12).also { diffInSeconds = it }
    if (years > 0) {
        string_date = if (years == 1L) {
            "1 year"
        } else {
            "$years years"
        }
    } else if (months > 0) {
        string_date = if (months == 1L) {
            "1 month"
        } else {
            "$months months"
        }
    } else if (weeks > 0) {
        string_date = if (weeks == 1L) {
            "1 week"
        } else {
            "$weeks Weeks"
        }
    } else if (days > 0) {
        string_date = if (days == 1L) {
            "1 day"
        } else {
            "$days days"
        }
    } else if (hrs > 0) {
        string_date = if (hrs == 1L) {
            "1 hour"
        } else {
            "$hrs hours"
        }
    } else if (min > 0) {
        string_date = if (min == 1L) {
            "1 minute"
        } else {
            "$min minutes"
        }
    }
    string_date = "$string_date ago"
    if (string_date == " ago") {
        string_date = "1 sec" + " ago"
    }
    return string_date
}
// zuluTime time convert to string
fun getChatListTime(zuluTime: String): Long {
    val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val output = SimpleDateFormat("dd/MM/yyyy")

    var d: Date? = null
    try {
        d = input.parse(zuluTime)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    val formatted = output.format(d)
    Log.i("DATE", "" + formatted)

    return time_to_timestamp(formatted, "dd/MM/yyyy")
}

// convert date and time book appointment fragment
fun time_to_timestamp(str_date: String?, pattren: String?): Long {
    var time_stamp: Long = 0
    try {
        val formatter = SimpleDateFormat(pattren)
//SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        formatter.timeZone = TimeZone.getTimeZone("GMT")
        val date = formatter.parse(str_date) as Date
        time_stamp = date.time
    } catch (ex: ParseException) {
        ex.printStackTrace()
    } catch (ex: java.lang.Exception) {
        ex.printStackTrace()
    }
    time_stamp /= 1000
    return time_stamp
}
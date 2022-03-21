package com.creation.nearby.utils

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.creation.nearby.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.location_alert.*

abstract class LocationUpdateUtilityFragment : Fragment(), LocationListener {

    private val TAG = "LocationUpdateUtility"
    private lateinit var mActivity: Activity
    private var locationRequest: LocationRequest? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    var latitude=0.0
    var longitude=0.0

    override fun onLocationChanged(location: Location?) {

        try {
            updatedLatLng(location!!.latitude, location.longitude)
        } catch (e: Exception) {
        }

    }

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions.isNotEmpty()) {
                permissions.entries.forEach {
                    Log.d(TAG, "${it.key} = ${it.value}")
                }

                val fineLocation = permissions[Manifest.permission.ACCESS_FINE_LOCATION]
                val coarseLocation = permissions[Manifest.permission.ACCESS_COARSE_LOCATION]

                if (fineLocation == true && coarseLocation == true) {
                    Log.e(TAG, "Permission Granted Successfully")
                    checkGpsOn()
                } else {
                    Log.e(TAG, "Permission not granted")
                    checkPermissionDenied(permissions.keys.first())
                }
            }

        }


    private val gpsOnLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                Log.e(TAG, "GPS Turned on successfully")
                startLocationUpdates()
            } else if (result.resultCode == RESULT_CANCELED) {
                Log.e(TAG, "GPS Turned on failed")
                locAlertDialogMethod()

            }
        }

    private fun locAlertDialogMethod() {
        val locationDialog = Dialog(requireContext(), R.style.Theme_Dialog)
        locationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        locationDialog.setContentView(R.layout.location_alert)

        locationDialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        locationDialog.setCancelable(true)
        locationDialog.setCanceledOnTouchOutside(true)
        locationDialog.window!!.setGravity(Gravity.CENTER)

        locationDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        locationDialog.btnTryAgain.setOnClickListener {
            locationDialog.dismiss()
            checkGpsOn()
        }
        locationDialog.show()

    }

    open fun getLiveLocation(activity: Activity) {

        mActivity = activity

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mActivity)

        checkLocationPermissions()
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    // Update UI with location data
                    // ...
                    Log.e(
                        TAG, "==========" + location.latitude.toString() + ", " +
                                location.longitude + "========="
                    )

                  //  if (latitude==0.0){

                      //  latitude=location.latitude
                     //   longitude=location.longitude
                        updatedLatLng(location.latitude, location.longitude)
                   // }else{

                        stopLocationUpdates()
                        //liveLatLng(location.latitude, location.longitude)

                   // }
                }
            }

        }
    }

    private fun checkLocationPermissions() {
        if (hasPermissions(permissions)) {
            Log.e(TAG, "Permissions Granted")
            // getLiveLocation(requireActivity())
            checkGpsOn()
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            checkPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            checkPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            Log.e(TAG, "Request for Permissions")
            requestPermission()
        }
    }

    // util method
    private fun hasPermissions(permissions: Array<String>): Boolean = permissions.all {
        ActivityCompat.checkSelfPermission(mActivity, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        requestMultiplePermissions.launch(permissions)
    }

    private fun checkPermissionDenied(permission: String) {
        if (shouldShowRequestPermissionRationale(permission)) {
            Log.e(TAG, "Permissions Denied")
            val mBuilder = AlertDialog.Builder(mActivity)
            val dialog: AlertDialog =
                mBuilder.setTitle(R.string.alert)
                    .setMessage(R.string.permission_rationale)
                    .setPositiveButton(
                        R.string.ok2
                    ) { dialog, which ->
                        // Request permission
                        requestPermission()
                    }.create()
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(
                        mActivity, R.color.purple_200
                    )
                )
            }
            dialog.show()


        } else {
            /*  val builder = AlertDialog.Builder(mActivity)
              val dialog: AlertDialog =
                  builder.setTitle(R.string.alert)
                      .setMessage(R.string.permission_denied_explanation)
                      .setCancelable(
                          false
                      )
                      .setPositiveButton("Settings") { dialog, which ->
                          // Build intent that displays the App settings screen.
                          val intent = Intent()
                          intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                          val uri = Uri.fromParts(
                              "package",
                              BuildConfig.APPLICATION_ID,
                              null
                          )
                          intent.data = uri
                          intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                          startActivity(intent)
                      }.create()
              dialog.setOnShowListener {
                  dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                      ContextCompat.getColor(
                          mActivity, R.color.colorPrimary
                      )
                  )
              }
              dialog.show()*/
//            locAlertDialogMethod()

        }
    }

    private fun locationPermission(permissions: Array<String>): Boolean {
        return ActivityCompat.checkSelfPermission(
            mActivity,
            permissions[0]
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            mActivity,
            permissions[1]
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun checkGpsOn() {
        locationRequest = LocationRequest.create()
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest?.interval = 500000000
        locationRequest?.fastestInterval = 2000000000

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        builder.setAlwaysShow(true)


        val result =
            LocationServices.getSettingsClient(mActivity).checkLocationSettings(builder.build())
        result.addOnCompleteListener { task ->
            try {
                val response = task.getResult(ApiException::class.java)
                Log.e(TAG, "==========GPS is ON=============")

                startLocationUpdates()
            } catch (e: ApiException) {
                when (e.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        val resolvableApiException = e as ResolvableApiException
                        gpsOnLauncher.launch(
                            IntentSenderRequest.Builder(resolvableApiException.resolution).build()
                        )

                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {

                    }
                }
            }
        }

    }

    //call startLocationUpdates() method for start live location update
    fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                mActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            hasPermissions(permissions)
            return
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
        Log.e(TAG, "Get Live Location Start")
    }


    //call stopLocationUpdates() method for stop live location update
    fun stopLocationUpdates() {
        try {
            fusedLocationClient.removeLocationUpdates(locationCallback)
            Log.e(TAG, "Get Live Location Stop")
        } catch (e: Exception) {
        }
    }

    abstract fun updatedLatLng(lat: Double, lng: Double)
    abstract fun liveLatLng(lat: Double, lng: Double)
}
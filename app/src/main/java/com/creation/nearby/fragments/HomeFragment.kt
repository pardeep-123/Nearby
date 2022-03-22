package com.creation.nearby.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.NotificationAdapter
import com.creation.nearby.base.PreferenceFile
import com.creation.nearby.databinding.FragmentHomeBinding
import com.creation.nearby.model.NotificationModel
import com.creation.nearby.ui.EditProfileActivity
import com.creation.nearby.ui.OtherUserProfileActivity
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.LocationUpdateUtilityFragment
import com.creation.nearby.viewmodel.HomeVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.socket.client.IO
import kotlinx.coroutines.*

class HomeFragment : LocationUpdateUtilityFragment(), OnMapReadyCallback,
    GoogleMap.OnInfoWindowClickListener {


    private lateinit var mMap: GoogleMap

    private val homeViewModel: HomeVM by viewModels()

    companion object {
        lateinit var notificationAdapter: NotificationAdapter
    }

    @SuppressLint("NotifyDataSetChanged")

    lateinit var binding: FragmentHomeBinding

    private var notificationList = ArrayList<NotificationModel>()

    private lateinit var mapFragment: SupportMapFragment
    var currentLat = 0.0
    var currentLng = 0.0
    override fun updatedLatLng(lat: Double, lng: Double) {

        currentLat = lat
        currentLng = lng
        homeViewModel.homeListingApi(requireContext(), currentLat, currentLng)
    }

    override fun liveLatLng(lat: Double, lng: Double) {


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeViewModel = homeViewModel
        // discover recycler view
        mapTimeScrollOff()

        binding.discoverRecyclerView.layoutManager = LinearLayoutManager(
            view.context,
            RecyclerView.HORIZONTAL, false
        )

        //notification recycler view
        binding.notificationRecyclerView.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

        homeViewModel.mapListData.observeForever {
            if (this::mMap.isInitialized) {
                if (it.isNotEmpty()) {

                    //
                    // create bounds that encompass every location we reference
                    val boundsBuilder = LatLngBounds.Builder()
                    // include all places we have markers for on the map

                    (homeViewModel.mList.indices).map {
                        boundsBuilder.include(homeViewModel.mList[it].latlng)
                    }

                    val bounds = boundsBuilder.build()

                    with(mMap) {
                        // Hide the zoom controls as the button panel will cover it.
                        uiSettings.isZoomControlsEnabled = false

                        // Setting an info window adapter allows us to change the both the contents and
                        // setInfoWindowAdapter(ChildCareInfoWindowAdapter())

                        //   setOnInfoWindowClickListener(this@HomeFragment)

                        // Ideally this string would be localised.
                        setContentDescription("Map with lots of markers.")

                        moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30))
                    }

                    // Add lots of markers to the googleMap.
                    addMarkersToMap()
                    //
                }
            }
        }

        //check first time login status
        val isFirstTimeLogin =
            PreferenceFile.retrieveKey(requireContext(), Constants.IS_FIRST_LOGIN)
        if (isFirstTimeLogin == "0") {
            openWelcomeDialog()
        }

        //notification recycler view
    }

    private fun openWelcomeDialog() {
        val dialog = Dialog(requireContext(), R.style.full_screen_dialog)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_welcome)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        val window: Window? = dialog.window
        val wlp = window?.attributes

        wlp?.gravity = Gravity.CENTER
        window?.attributes = wlp
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        val btnSkip: TextView = dialog.findViewById(R.id.btnSkip)
        val btnOk: TextView = dialog.findViewById(R.id.btnOk)

        btnSkip.setOnClickListener {
            dialog.dismiss()
            homeViewModel.checkFirstTimeLoginApi(requireContext())
        }

        btnOk.setOnClickListener {
            dialog.dismiss()
            homeViewModel.checkFirstTimeLoginApi(requireContext())
            startActivity(Intent(requireContext(), EditProfileActivity::class.java))
        }

        dialog.show()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun mapTimeScrollOff() {
        binding.ivTransparent.setOnTouchListener { view, motionEvent ->
            if (view.id == R.id.ivTransparent) {
                view.parent.requestDisallowInterceptTouchEvent(true)
                when (motionEvent.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> view.parent.requestDisallowInterceptTouchEvent(false)
                }
            }

            false
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        if (homeViewModel.mList.isNotEmpty()) {

            //
            // create bounds that encompass every location we reference
            val boundsBuilder = LatLngBounds.Builder()
            // include all places we have markers for on the map

            (homeViewModel.mList.indices).map {
                boundsBuilder.include(homeViewModel.mList[it].latlng)
            }

            val bounds = boundsBuilder.build()

            with(mMap!!) {
                // Hide the zoom controls as the button panel will cover it.
                uiSettings!!.isZoomControlsEnabled = false

                // Setting an info window adapter allows us to change the both the contents and
                // setInfoWindowAdapter(ChildCareInfoWindowAdapter())

                //   setOnInfoWindowClickListener(this@HomeFragment)

                // Ideally this string would be localised.
                setContentDescription("Map with lots of markers.")

                moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30))
            }

            // Add lots of markers to the googleMap.
            addMarkersToMap()
            //
        }

        mMap.setOnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
            // which is clicked and displaying it in a toast message.
            val intent = Intent(requireContext(), OtherUserProfileActivity::class.java)
            startActivity(intent)
            false
        }
    }

    private fun addMarkersToMap() {
        // place markers for each of the defined locations
        (homeViewModel.mList.indices).map {
            mMap.addMarker(
                MarkerOptions()
                    .position(homeViewModel.mList[it].latlng)
                    .title(homeViewModel.mList[it].name)
                    //  .snippet(list[it].snippet + "###" + list[it].url)
                    .icon(BitmapDescriptorFactory.defaultMarker())
                    .infoWindowAnchor(0.5F, 0F)
                    .draggable(false)
                    .zIndex(it.toFloat())

            )!!.tag = it.toString()

        }
    }


    private fun createCustomMarker(context: Context, @DrawableRes resource: Int): Bitmap {
        val marker: View =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.map_marker,
                null
            )
        val markerImage = marker.findViewById(R.id.markerImage) as ImageView
        markerImage.setImageResource(resource)

        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        marker.layoutParams = ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT)
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        marker.buildDrawingCache()
        val bitmap = Bitmap.createBitmap(
            marker.measuredWidth,
            marker.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        marker.draw(canvas)
        return bitmap
    }

    override fun onResume() {
        super.onResume()


        getLiveLocation(requireActivity())
        mapFragment = childFragmentManager.findFragmentById(R.id.homeMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onInfoWindowClick(p0: Marker) {

    }
}
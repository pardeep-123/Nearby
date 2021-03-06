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
import com.creation.nearby.interfaces.FilterInterface
import com.creation.nearby.model.NotificationModel
import com.creation.nearby.ui.EditProfileActivity
import com.creation.nearby.ui.MainActivity
import com.creation.nearby.ui.OtherUserProfileActivity
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.LocationUpdateUtilityFragment
import com.creation.nearby.viewmodel.HomeVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import io.socket.client.IO
import kotlinx.coroutines.*

class HomeFragment : LocationUpdateUtilityFragment(), OnMapReadyCallback,
    GoogleMap.OnInfoWindowClickListener, FilterInterface {


    private lateinit var mMap: GoogleMap

    private val homeViewModel: HomeVM by viewModels()

    companion object {
        lateinit var notificationAdapter: NotificationAdapter
    }

    @SuppressLint("NotifyDataSetChanged")

    lateinit var binding: FragmentHomeBinding

    private lateinit var mapFragment: SupportMapFragment
    var currentLat = ""
    var currentLng = ""
    var ctx: Context? = null

    override fun updatedLatLng(lat: Double, lng: Double) {

        currentLat = lat.toString()
        currentLng = lng.toString()
        homeViewModel.homeListingApi(requireContext(), "", ""
        ,"","","","","")
    }

    override fun liveLatLng(lat: Double, lng: Double) {


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
        (ctx as MainActivity).initInterface(this)
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
                    .title("${homeViewModel.mList[it].firstName} ${homeViewModel.mList[it].lastName}")
                    //  .snippet(list[it].snippet + "###" + list[it].url)
                    .icon(BitmapDescriptorFactory.defaultMarker())
                    .infoWindowAnchor(0.5F, 0F)
                    .draggable(false)
                    .zIndex(it.toFloat())

            )!!.tag = it.toString()

        }
    }



    override fun onResume() {
        super.onResume()


        getLiveLocation(requireActivity())
        mapFragment = childFragmentManager.findFragmentById(R.id.homeMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onInfoWindowClick(p0: Marker) {

    }

    override fun sendData(
        location: String,
        latitude: String,
        longitude : String,
        distance: String,
        gender: String,
        filterBy: String,
        minAge: String,
        maxAge: String
    ) {
        /**
         * set values to viewmodel
         */
        homeViewModel.latitude.set(latitude)
        homeViewModel.longitude.set(longitude)
        homeViewModel.distance.set(distance)
        homeViewModel.gender.set(gender)
        homeViewModel.filterBy.set(filterBy)
        homeViewModel.minAge.set(minAge)
        homeViewModel.maxAge.set(maxAge)
        homeViewModel.homeListingApi(requireContext(), latitude, longitude
            ,filterBy,gender,distance,minAge,maxAge)
    }
}
package com.creation.nearby.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.NotificationAdapter
import com.creation.nearby.databinding.FragmentHomeBinding
import com.creation.nearby.model.NotificationModel
import com.creation.nearby.ui.OtherUserProfileActivity
import com.creation.nearby.viewmodel.HomeVM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(),OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {


    private lateinit var mMap: GoogleMap

    private val homeViewModel : HomeVM by viewModels()
    companion object{
        lateinit var notificationAdapter: NotificationAdapter
    }

    @SuppressLint("NotifyDataSetChanged")

    lateinit var binding: FragmentHomeBinding

    private var notificationList = ArrayList<NotificationModel>()

    private lateinit var  mapFragment: SupportMapFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeViewModel = homeViewModel
        // discover recycler view



        mapTimeScrollOff()

        binding.discoverRecyclerView.layoutManager = LinearLayoutManager(view.context,
            RecyclerView.HORIZONTAL,false)

        //notification recycler view

        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL,false)

        homeViewModel.mapListData.observeForever {
            if(this::mMap.isInitialized){
                if (it.isNotEmpty()){

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


        //notification recycler view
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
        if (homeViewModel.mList.isNotEmpty()){

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

//        mMap.uiSettings.isMapToolbarEnabled = false
//        mMap.uiSettings.isCompassEnabled = false
//        mMap.uiSettings.isMyLocationButtonEnabled = false
//        mapFragment.view?.isClickable = false
//        //  30.7333° N, 76.7794° E
//
//        list.add(LatLng(30.7046, 76.7179))
//        list.add(LatLng(30.0668, 79.0193))
//        list.add(LatLng(29.7041, 77.1025))
//        list.add(LatLng(31.1048,77.1734))
//
//        mMap.addMarker(
//            MarkerOptions().position(list[0]).icon(
//                BitmapDescriptorFactory.fromBitmap(
//                    createCustomMarker(requireContext(), R.drawable.chat_pic_5)
//                )
//            )
//        )
//
//        mMap.addMarker(
//            MarkerOptions().position(list[1]).icon(
//                BitmapDescriptorFactory.fromBitmap(
//                    createCustomMarker(requireContext(), R.drawable.chat_pic_1)
//                )
//            )
//        )
//        mMap.addMarker(
//            MarkerOptions().position(list[2]).icon(
//                BitmapDescriptorFactory.fromBitmap(
//                    createCustomMarker(requireContext(), R.drawable.chat_pic_2)
//                )
//            )
//        )
//        mMap.addMarker(
//            MarkerOptions().position(list[3]).icon(
//                BitmapDescriptorFactory.fromBitmap(
//                    createCustomMarker(requireContext(), R.drawable.chat_pic_8)
//                )
//            )
//        )
//
//        val builder = LatLngBounds.Builder()
//        builder.include(list[0]) //Taking Point A (First LatLng)
//
//        builder.include(list[1]) //Taking Point B (Second LatLng)
//
//        val bounds = builder.build()
//        val cu = CameraUpdateFactory.newLatLngBounds(bounds, 200)
//       // mMap.moveCamera   (cu)
//        mMap.setOnMapLoadedCallback {
//            mMap.moveCamera(
//                CameraUpdateFactory.newLatLngBounds(
//                    bounds,
//                    30
//                )
//            )
//        }
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(7f), 2000, null)
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
        homeViewModel.homeListingApi(requireContext())

        mapFragment = childFragmentManager.findFragmentById(R.id.homeMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onInfoWindowClick(p0: Marker) {

    }
}
package com.creation.nearby.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.creation.nearby.R
import com.creation.nearby.databinding.FragmentMapBinding
import com.creation.nearby.ui.OtherUserProfileActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(),OnMapReadyCallback {

    lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap

    private lateinit var  mapFragment : SupportMapFragment
    private var  list  = ArrayList<LatLng>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentMapBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
         mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        mMap!!.uiSettings.isMapToolbarEnabled = false
        mMap!!.uiSettings.isCompassEnabled = false
        mMap!!.uiSettings.isMyLocationButtonEnabled = false
        mapFragment.view?.isClickable = false;
        //  30.7333° N, 76.7794° E

        list.add(LatLng(30.7046, 76.7179))
        list.add(LatLng(30.0668, 79.0193))
        list.add(LatLng(28.7041, 77.1025))
        list.add(LatLng(31.1048,77.1734))

        p0.addMarker(
            MarkerOptions().position(list[0]).icon(
                BitmapDescriptorFactory.fromBitmap(
                    createCustomMarker(requireContext(), R.drawable.chat_pic_3)
                )
            )
        )

        p0.addMarker(
            MarkerOptions().position(list[1]).icon(
                BitmapDescriptorFactory.fromBitmap(
                    createCustomMarker(requireContext(), R.drawable.chat_pic_1)
                )
            )
        )
        p0.addMarker(
            MarkerOptions().position(list[2]).icon(
                BitmapDescriptorFactory.fromBitmap(
                    createCustomMarker(requireContext(), R.drawable.chat_pic_2)
                )
            )
        )
        p0.addMarker(
            MarkerOptions().position(list[3]).icon(
                BitmapDescriptorFactory.fromBitmap(
                    createCustomMarker(requireContext(), R.drawable.chat_pic_8)
                )
            )
        )
        val builder = LatLngBounds.Builder()
        builder.include(list[0]) //Taking Point A (First LatLng)

        builder.include(list[1]) //Taking Point B (Second LatLng)

        val bounds = builder.build()
        val cu = CameraUpdateFactory.newLatLngBounds(bounds, 200)
        //  p0.moveCamera(cu)
        p0.setOnMapLoadedCallback(GoogleMap.OnMapLoadedCallback {
            p0.moveCamera(
                CameraUpdateFactory.newLatLngBounds(
                    bounds,
                    30
                )
            )
        })
        p0.animateCamera(CameraUpdateFactory.zoomTo(7f), 2000, null)
        p0.setOnMarkerClickListener(GoogleMap.OnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
            // which is clicked and displaying it in a toast message.
            var intent = Intent(requireContext(), OtherUserProfileActivity::class.java)
            startActivity(intent)
            false
        })
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
}
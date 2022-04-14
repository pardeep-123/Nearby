package com.creation.nearby.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.creation.nearby.R
import com.creation.nearby.databinding.FragmentMapBinding
import com.creation.nearby.isValidGlideContext
import com.creation.nearby.model.UserListModel
import com.creation.nearby.retrofit.CallApi
import com.creation.nearby.retrofit.RequestProcessor
import com.creation.nearby.retrofit.RetrofitInterface
import com.creation.nearby.ui.OtherUserProfileActivity
import com.creation.nearby.utils.Constants
import com.creation.nearby.utils.LocationUpdateUtilityFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Response


class MapFragment : LocationUpdateUtilityFragment(), OnMapReadyCallback {

    lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap


    private lateinit var mapFragment: SupportMapFragment
    override fun updatedLatLng(latitude: Double, longitude: Double) {
        currentLat = latitude
        currentLng = longitude
        userListApi()

    }

    var currentLat = 0.0
    var currentLng = 0.0

    override fun liveLatLng(lat: Double, lng: Double) {

    }

    override fun onDestroy() {
        stopLocationUpdates()
        super.onDestroy()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        mMap.uiSettings.isMapToolbarEnabled = false
        mMap.uiSettings.isCompassEnabled = false
        mMap.uiSettings.isMyLocationButtonEnabled = false
        mapFragment.view?.isClickable = false
        //  30.7333° N, 76.7794° E

        if (requireActivity() != null) {

            getLiveLocation(requireActivity())
        }

        /*  list.add(LatLng(30.7046, 76.7179))
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
          p0.setOnMapLoadedCallback {
              p0.moveCamera(
                  CameraUpdateFactory.newLatLngBounds(
                      bounds,
                      30
                  )
              )
          }
          p0.animateCamera(CameraUpdateFactory.zoomTo(7f), 2000, null)*/
        p0.setOnMarkerClickListener { marker -> // on marker click we are getting the title of our marker
            // which is clicked and displaying it in a toast message.

            val intent = Intent(requireContext(), OtherUserProfileActivity::class.java)
            val pos=marker.tag.toString()

            intent.putExtra("userId", pos)
            intent.putExtra("from", "mapList")
            startActivity(intent)
            false
        }
    }

      var body= ArrayList<UserListModel.Body.User>()
    private fun userListApi() {

        val haQueryMap= HashMap<String,String>()
        haQueryMap["page"]=""
        haQueryMap["is_online"]=""
        haQueryMap["gender"]=""
        haQueryMap["latitude"]=""
        haQueryMap["longitude"]=""
        haQueryMap["radius"]=""
        haQueryMap["min_age"]=""
        haQueryMap["max_age"]=""

        if (requireActivity().isValidGlideContext()) {
            try {
                CallApi().callService(
                    requireActivity(),
                    true,
                    object : RequestProcessor<Response<UserListModel>> {
                        override suspend fun sendRequest(retrofitApi: RetrofitInterface): Response<UserListModel> {
                            return retrofitApi.swipeUserList(haQueryMap)
                        }

                        override fun onResponse(res: Response<UserListModel>) {
                            if (res.isSuccessful) {
                                val response = res.body()!!
                                val list = response.body.user_list
                                val builder = ArrayList<LatLng>()
                                if (mMap != null) {
                                    list.forEachIndexed { index, it ->
                                        if (it.latitude.isNotEmpty() && it.latitude != "0.0"&&!it.latitude.contains("0.0")) {

                                            body.add(it)
                                            val latLng = LatLng(
                                                it.latitude.toDouble(),
                                                it.longitude.toDouble()
                                            )
                                            mMap.addMarker(
                                                MarkerOptions().position(latLng).icon(
                                                    BitmapDescriptorFactory.fromBitmap(
                                                        createCustomMarker(
                                                            requireContext(),
                                                            Constants.IMAGE_BASE_URL+it.image)))
                                            )!!.tag = it.id.toString()
                                            builder.add(latLng) //Taking Point A (First LatLng)

                                        }

                                    }
/*
if (list.isNotEmpty()) {
            // create bounds that encompass every location we reference
            val boundsBuilder = LatLngBounds.Builder()
            // include all places we have markers for on the map

            (list.indices).map {
                boundsBuilder.include(list[it].position)
            }

            val bounds = boundsBuilder.build()

            with(mMap!!) {
                // Hide the zoom controls as the button panel will cover it.
                uiSettings!!.isZoomControlsEnabled = false

                // Setting an info window adapter allows us to change the both the contents and
                setInfoWindowAdapter(CustomInfoWindowAdapter())

                setOnInfoWindowClickListener(this@ActivitiesOnMapActivity)

                // Ideally this string would be localised.
                setContentDescription("markers")

                moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 30))
            }

            // Add lots of markers to the googleMap.
            addMarkersToMap()
        }
 */


                                    if (list.isEmpty()) {
                                        val latLng = LatLng(currentLat, currentLng)

                                        builder.add(latLng)
                                    }


                                    zoomRoute(builder)
//                                mMap.setOnMapLoadedCallback {
//                                    mMap.moveCamera(
//                                        CameraUpdateFactory.newLatLngBounds(
//                                            bounds,
//                                            30
//                                        )
//                                    )
//                                }
                                }
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

    }

    fun zoomRoute(builderList:  ArrayList<LatLng>) {

        val lstLatLngRoute: MutableList<LatLng> = java.util.ArrayList()
        builderList.forEach {
            lstLatLngRoute.add(it)

        }
        if (mMap == null || lstLatLngRoute == null || lstLatLngRoute.isEmpty()) return
        val boundsBuilder = LatLngBounds.Builder()
        for (latLngPoint in lstLatLngRoute) boundsBuilder.include(latLngPoint)

        val width = resources.displayMetrics.widthPixels
        val height = resources.displayMetrics.heightPixels
        val routePadding = (width * 0.30).toInt()

        val latLngBounds = boundsBuilder.build()
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, routePadding))


    }



    private fun createCustomMarker(context: Context, imageUser: String): Bitmap {
        val marker: View =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                R.layout.map_marker,
                null
            )
        val markerImage = marker.findViewById(R.id.markerImage) as CircleImageView
        Glide.with(requireActivity()).load(imageUser).placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder).into(markerImage)

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
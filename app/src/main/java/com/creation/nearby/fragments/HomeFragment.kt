package com.creation.nearby.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.DiscoverAdapter
import com.creation.nearby.adapter.FeedAdapter
import com.creation.nearby.adapter.NotificationAdapter
import com.creation.nearby.databinding.FragmentHomeBinding
import com.creation.nearby.model.DiscoverModel
import com.creation.nearby.model.NotificationModel
import com.creation.nearby.ui.OtherUserProfileActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(),OnMapReadyCallback {


    private lateinit var discoverAdapter: DiscoverAdapter
    private lateinit var feedAdapter: FeedAdapter
    private lateinit var mMap: GoogleMap

    companion object{
        lateinit var notificationAdapter: NotificationAdapter
    }

    @SuppressLint("NotifyDataSetChanged")

    lateinit var binding: FragmentHomeBinding

    private var discoverList = ArrayList<DiscoverModel>()
    private var feedList = ArrayList<DiscoverModel>()
    private var notificationList = ArrayList<NotificationModel>()

    private lateinit var  mapFragment: SupportMapFragment
    private var  list = ArrayList<LatLng>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // discover recycler view

        mapFragment = childFragmentManager.findFragmentById(R.id.homeMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mapTimeScrollOff()

        binding.discoverRecyclerView.layoutManager = LinearLayoutManager(view.context,
            RecyclerView.HORIZONTAL,false)

        discoverList.add(DiscoverModel("300m away","Starbucks","Let’s meet and talk."))
        discoverList.add(DiscoverModel("1km away","Basketball","We are up to play...."))

        discoverAdapter = DiscoverAdapter(discoverList)
        binding.discoverRecyclerView.adapter = discoverAdapter
        discoverAdapter.notifyDataSetChanged()

        // discover recycler view


        //feed recycler view

        binding.feedRecView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL,false)

        feedList.add(DiscoverModel("2min ago","Coffee?","Let’s meet and talk."))
        feedList.add(DiscoverModel("3min ago","New here","We are up to play..."))

        feedAdapter = FeedAdapter(feedList)
        binding.feedRecView.adapter = feedAdapter
        feedAdapter.notifyDataSetChanged()

        //feed recycler view

        //notification recycler view

        binding.notificationRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL,false)

        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Brooklyn Simmons","You are friends! \uD83C\uDF89","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",true))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))
        notificationList.add(NotificationModel(R.drawable.user_pic_2,"Courtney Henry","You are friends! Share nearby to get a bigger circle.Share nearby to get a bigger circle.","Today",false))

        notificationAdapter = NotificationAdapter(notificationList)
        binding.notificationRecyclerView.adapter = notificationAdapter
        notificationAdapter.notifyDataSetChanged()

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
        mMap!!.uiSettings.isMapToolbarEnabled = false
        mMap!!.uiSettings.isCompassEnabled = false
        mMap!!.uiSettings.isMyLocationButtonEnabled = false
        mapFragment.view?.isClickable = false;
        //  30.7333° N, 76.7794° E

        list.add(LatLng(30.7046, 76.7179))
        list.add(LatLng(30.0668, 79.0193))
        list.add(LatLng(29.7041, 77.1025))
        list.add(LatLng(31.1048,77.1734))

        p0.addMarker(
            MarkerOptions().position(list[0]).icon(
                BitmapDescriptorFactory.fromBitmap(
                    createCustomMarker(requireContext(), R.drawable.chat_pic_5)
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
         // p0.moveCamera(cu)
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
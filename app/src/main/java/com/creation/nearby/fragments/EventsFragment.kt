package com.creation.nearby.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.R
import com.creation.nearby.adapter.ActivityAdapter
import com.creation.nearby.adapter.EventsAdapter
import com.creation.nearby.databinding.FragmentEventsBinding
import com.creation.nearby.model.EventsModel
import com.creation.nearby.ui.AddEventActivity

class EventsFragment : Fragment() {

    lateinit var binding: FragmentEventsBinding
    lateinit var eventsAdapter: EventsAdapter
    private var eventsList = ArrayList<EventsModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.eventsRecView.layoutManager = LinearLayoutManager(view.context,RecyclerView.VERTICAL,false)

        eventsList.add(EventsModel(R.drawable.image,"Let’s Co-work","5km away from you."))
        eventsList.add(EventsModel(R.drawable.image_1,"Coffee Break","300m away from you."))
        eventsList.add(EventsModel(R.drawable.image_2,"Let’s Co-work","5km away from you."))

        eventsAdapter = EventsAdapter(eventsList)
        binding.eventsRecView.adapter = eventsAdapter
        eventsAdapter.notifyDataSetChanged()


        onClickHandler()

    }

    private fun onClickHandler() {
        binding.addEventLayout.setOnClickListener{
            startActivity(Intent(requireContext(),AddEventActivity::class.java))
        }
    }


}
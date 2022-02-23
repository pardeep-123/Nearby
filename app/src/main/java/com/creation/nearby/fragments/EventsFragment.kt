package com.creation.nearby.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.creation.nearby.databinding.FragmentEventsBinding
import com.creation.nearby.ui.AddEventActivity
import com.creation.nearby.viewmodel.GetEventVM

class EventsFragment : Fragment() {

    lateinit var binding: FragmentEventsBinding

    // initialize the viewmodel
    val getEventVM: GetEventVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getEventVM = getEventVM
        binding.eventsRecView.layoutManager =
            LinearLayoutManager(view.context, RecyclerView.VERTICAL, false)

//        eventsList.add(EventsModel(R.drawable.image, "Let’s Co-work", "5km away from you."))
//        eventsList.add(EventsModel(R.drawable.image_1, "Coffee Break", "300m away from you."))
//        eventsList.add(EventsModel(R.drawable.image_2, "Let’s Co-work", "5km away from you."))

        // call api
        getEventVM.eventListApi(requireContext())
        onClickHandler()

//        eventsAdapter = EventsAdapter(getEventVM.getEventList)
//        binding.eventsRecView.adapter = eventsAdapter
//        eventsAdapter.notifyDataSetChanged()
    }

    private fun onClickHandler() {
        binding.addEventLayout.setOnClickListener {
            startActivity(Intent(requireContext(), AddEventActivity::class.java))
        }
    }


}
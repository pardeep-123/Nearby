package com.creation.nearby.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.creation.nearby.model.GetEventModel

class EventDetailsVM : ViewModel() {


    var detailsModel: ObservableField<GetEventModel.Body> = ObservableField()

   }
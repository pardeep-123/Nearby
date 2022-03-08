package com.creation.nearby.utils

import android.widget.TimePicker

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.view.View

import android.widget.TextView
import com.creation.nearby.R
import java.text.SimpleDateFormat
import java.util.*


class TimePickerUniversal(private val mEditText: TextView, withAMPM: Boolean) :
    View.OnFocusChangeListener, OnTimeSetListener, View.OnClickListener {
    private var mCalendar: Calendar? = null
    private var mFormat: SimpleDateFormat? = null
    private val withAMPM: Boolean
    override fun onFocusChange(view: View, hasFocus: Boolean) {
        if (hasFocus) {
            showPicker(view)
        }
    }

    override fun onClick(view: View) {
        showPicker(view)
    }

    private fun showPicker(view: View) {
        if (mCalendar == null) mCalendar = Calendar.getInstance()
        val hour: Int? = mCalendar?.get(Calendar.HOUR_OF_DAY)
        val minute: Int? = mCalendar?.get(Calendar.MINUTE)
        if (hour != null) {
            if (minute != null) {
                 TimePickerDialog(view.context,R.style.DialogTimePicker, this, hour, minute, !withAMPM).show()

            }
        }
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        mCalendar?.set(Calendar.HOUR_OF_DAY, hourOfDay)
        mCalendar?.set(Calendar.MINUTE, minute)
        if (mFormat == null) mFormat =
            SimpleDateFormat(if (withAMPM) "hh:mm a" else "HH:mm", Locale.getDefault())
        mEditText.text = mFormat!!.format(mCalendar?.getTime())

    }

    init {
        mEditText.onFocusChangeListener = this
        mEditText.setOnClickListener(this)
        this.withAMPM = withAMPM

    }
}
package com.creation.nearby.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.creation.nearby.R
import com.creation.nearby.model.PopupModel

 class ZodiacAdapter(val popupList: ArrayList<PopupModel>) : BaseAdapter() {
    override fun getCount(): Int {
        return popupList.size
    }

    override fun getItem(p0: Int): Any {
       return popupList[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view : View
        val viewHolder: ViewHolder
        if (convertView == null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_popup_zodiac,null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder

        }else{

            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.zodiacName.text = popupList[position].zodiacName
        return view
    }
    private class ViewHolder(row: View?){
        val zodiacName: TextView = row?.findViewById(R.id.zodiac_name) as TextView

    }
}
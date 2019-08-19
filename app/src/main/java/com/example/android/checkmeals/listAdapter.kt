package com.example.android.checkmeals

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView

class listAdapter(context: Context, val list: ArrayList<String>):
        ArrayAdapter<String>(context, R.layout.checkbox_list, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.checkbox_list, parent, false)

        val mealName = rowView.findViewById<CheckBox>(R.id.checkbox)

        mealName.text = list[position]


        return rowView
    }



}
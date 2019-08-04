package com.example.android.checkmeals.DaysActivities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.android.checkmeals.R
import com.example.android.checkmeals.listAdapter

class MondayActivity : AppCompatActivity() {

    val mealNames = arrayListOf<String>("Hello", "Test")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_days)

        val mylistAdapter = listAdapter(this, mealNames)
        var listView = findViewById(R.id.list) as ListView
        listView.adapter = mylistAdapter



    }
}

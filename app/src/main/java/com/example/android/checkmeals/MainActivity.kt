package com.example.android.checkmeals

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the textview with id monday
        val monday = findViewById<TextView>(R.id.monday)

        //set up onclicklistener

        monday.setOnClickListener {
            val mondayIntent = Intent(this, MondayActivity::class.java)
            startActivity(mondayIntent)
        }
    }
}

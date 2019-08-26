package com.example.android.checkmeals

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.android.checkmeals.DaysActivities.*

class MainActivity : AppCompatActivity() {

//    FIRST OPEN:
//    TODO: Add popup letting users know how to use the app and things to click
//    TODO: Add menu
//      - Delete all meals all days


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

        // Find the textview with id tuesday
        val tuesday = findViewById<TextView>(R.id.tuesday)
        //set up onclicklistener
        tuesday.setOnClickListener {
            val tuesdayIntent = Intent(this, TuesdayActivity::class.java)
            startActivity(tuesdayIntent)
        }

        // Find the textview with id wednesday
        val wednesday = findViewById<TextView>(R.id.wednesday)
        //set up onclicklistener
        wednesday.setOnClickListener {
            val wednesdayIntent = Intent(this, WednesdayActivity::class.java)
            startActivity(wednesdayIntent)
        }

        // Find the textview with id thursday
        val thursday = findViewById<TextView>(R.id.thursday)
        //set up onclicklistener
        thursday.setOnClickListener {
            val thursdayIntent = Intent(this, ThursdayActivity::class.java)
            startActivity(thursdayIntent)
        }

        // Find the textview with id friday
        val friday = findViewById<TextView>(R.id.friday)
        //set up onclicklistener
        friday.setOnClickListener {
            val fridayIntent = Intent(this, FridayActivity::class.java)
            startActivity(fridayIntent)
        }

        // Find the textview with id saturday
        val saturday = findViewById<TextView>(R.id.saturday)
        //set up onclicklistener
        saturday.setOnClickListener {
            val saturdayIntent = Intent(this, SaturdayActivity::class.java)
            startActivity(saturdayIntent)
        }

        // Find the textview with id sunday
        val sunday = findViewById<TextView>(R.id.sunday)
        //set up onclicklistener
        sunday.setOnClickListener {
            val sundayIntent = Intent(this, SundayActivity::class.java)
            startActivity(sundayIntent)
        }
    }

    // Inflate the menu options from the XML file.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }


    // When item is clicked, do the following things
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?: return false
        return when (item.itemId) {
            R.id.settings -> {
                // For now, just show a toast message to make sure it works.
                // TODO: Make a XML + Kotlin file for this menu setting and create an intent to go to that page.
                val toast = Toast.makeText(applicationContext, "Settings Clicked!", Toast.LENGTH_SHORT)
                toast.show()
                true
            }
            R.id.setMacros -> {
                // TODO: Make a XML + Kotlin file for this menu setting and create an intent to go to that page.
                Toast.makeText(applicationContext, "Macros Clicked!", Toast.LENGTH_SHORT).show()
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }




}

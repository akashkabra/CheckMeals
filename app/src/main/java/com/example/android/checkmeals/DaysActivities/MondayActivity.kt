package com.example.android.checkmeals.DaysActivities

import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import com.example.android.checkmeals.R
import com.example.android.checkmeals.listAdapter
import kotlinx.android.synthetic.main.activity_days.*
import kotlinx.android.synthetic.main.popup_edittext.*

class MondayActivity : AppCompatActivity() {

    val mealNames = arrayListOf<String>("Hello", "Test")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_days)

        val mylistAdapter = listAdapter(this, mealNames)
        var listView = findViewById(R.id.list) as ListView
        listView.adapter = mylistAdapter

    }

    // Inflate the menu options from the XML file.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.addmeal_menu, menu)
        return true
    }

    // When item is clicked, do the following things
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?: return false
        return when (item.itemId) {
            R.id.addmeals -> {
                // For now, just show a toast message to make sure it works.
                // TODO: Make a XML + Kotlin file for this add meal setting and create an intent to go to that page.
                // TODO: Or make it so it pops up a window in which the user enters the names of the meals.
                val toast = Toast.makeText(applicationContext, "Adding Meals...", Toast.LENGTH_SHORT)
                toast.show()
//
//                val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//                val view = inflater.inflate(R.layout.popup_edittext, null)
//                val popupWindow = PopupWindow(
//                        view,
//                        LinearLayout.LayoutParams.WRAP_CONTENT,
//                        LinearLayout.LayoutParams.WRAP_CONTENT
//                )
//                popupWindow.setFocusable(true)
//                popupWindow.update()
//
//
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    popupWindow.elevation = 10.0F
//                }
//                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    val slideIn = Slide()
//                    slideIn.slideEdge = Gravity.TOP
//                    popupWindow.enterTransition = slideIn
//
//                    val slideOut = Slide()
//                    slideOut.slideEdge = Gravity.RIGHT
//                    popupWindow.exitTransition = slideOut
//                    popupWindow.dismiss()
//                }
//
//                TransitionManager.beginDelayedTransition(root_layout)
//                popupWindow.showAtLocation(
//                        root_layout,
//                        Gravity.CENTER,
//                        0,
//                        0
//                )
//                val btn = findViewById<Button>(R.id.btn_done) as Button
//                btn.setOnClickListener{
//                    Toast.makeText(applicationContext, "Hello", Toast.LENGTH_SHORT).show()
//                }


                true
            } else -> super.onOptionsItemSelected(item)
        }
    }


}

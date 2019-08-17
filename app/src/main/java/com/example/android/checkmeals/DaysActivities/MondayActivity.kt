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
import org.w3c.dom.Text
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder

class MondayActivity : AppCompatActivity() {

    var mealNames = arrayListOf<String>("Hello", "Test")
    var placeHolder = ""
    var value = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_days)

        val mylistAdapter = listAdapter(this, mealNames)
        var listView = findViewById(R.id.list) as ListView
        listView.adapter = mylistAdapter

        outputHolder.setText("HELLO WORLD \n HELLO THERE")

        val cal = findViewById<TextView>(R.id.calories)
        cal.setOnClickListener {
            var setLine = "Enter amount of Calories"
            var setEdit = "Calories"
//            TODO: Delete the return value from popup() because we aren't using it --> also remove it from the function implementation
            value = popup(setLine, setEdit)
        }


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
//                val toast = Toast.makeText(applicationContext, "Adding Meals...", Toast.LENGTH_SHORT)
//                toast.show()
//              TODO: Delete the return value from popup() because we aren't using it --> also remove it from the function implementation
                value = popup("test","hold")
                true
            }
            R.id.test -> {
//                Toast.makeText(this, "test: " + placeHolder, Toast.LENGTH_SHORT).show()
                fileOutput()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

//    Pop-up-Window
    private fun popup(text: String, edit: String): String {
        val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_edittext, null)
        val popupWindow = PopupWindow(
                view,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        popupWindow.setFocusable(true)
        popupWindow.update()


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            val slideOut = Slide()
            slideOut.slideEdge = Gravity.RIGHT
            popupWindow.exitTransition = slideOut
//            Toast.makeText(applicationContext, "Done!", Toast.LENGTH_SHORT).show()
        }

//      Set the texts for the popup view
        val changeText = view.findViewById<TextView>(R.id.popup)
        changeText.setText(text)
        val changeEdit = view.findViewById<EditText>(R.id.editMe)
        changeEdit.setHint(edit)

//      Change R.id.calories to get from @params so it works for everything
        val macros = findViewById<TextView>(R.id.calories)

        val mealInput = view.findViewById<EditText>(R.id.editMe)
        val btnClose = view.findViewById<Button>(R.id.btndone)
        btnClose.setOnClickListener {
            popupWindow.dismiss()
            val stringBuilder: StringBuilder = StringBuilder()
            stringBuilder.append(edit + ": ")

            placeHolder = mealInput.text.toString()

            stringBuilder.append(placeHolder + " \n")
            macros.setText(placeHolder)
            fileInput(stringBuilder)
//            Toast.makeText(this, placeHolder, Toast.LENGTH_SHORT).show()
        }

        TransitionManager.beginDelayedTransition(root_layout)
        popupWindow.showAtLocation(
                root_layout,
                Gravity.CENTER,
                0,
                0
        )
        return placeHolder
    }

    private fun fileInput(data: StringBuilder) {
        val fileOutputStream: FileOutputStream
        val file = "macroDays.txt"
        try {
            fileOutputStream = openFileOutput(file, Context.MODE_APPEND)
            fileOutputStream.write(data.toString().toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "File Not Found", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "File Related Error", Toast.LENGTH_LONG).show()
        }
        Toast.makeText(applicationContext, "Data saved in file!", Toast.LENGTH_SHORT).show()
    }

//    TODO: Put openFileInput into a try and catch block to make sure the app doesn't crash
    private fun fileOutput() {
        var fileInputStream: FileInputStream? = null
        val file = "macroDays.txt"
        fileInputStream = openFileInput(file)
        var inputSteamReader: InputStreamReader = InputStreamReader(fileInputStream)
        val bufferReader: BufferedReader = BufferedReader(inputSteamReader)
        val stringBuilder: StringBuilder = StringBuilder()
        var text: String? = null
        while ( { text = bufferReader.readLine(); text }() != null) {
            stringBuilder.append(text + "\n")
        }
        outputHolder.setText(stringBuilder)
//        Toast.makeText(applicationContext, stringBuilder, Toast.LENGTH_SHORT).show()

     }


}

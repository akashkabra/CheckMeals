package com.example.android.checkmeals.DaysActivities

import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.text.InputType
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.widget.*
import com.example.android.checkmeals.R
import com.example.android.checkmeals.listAdapter
import kotlinx.android.synthetic.main.activity_days.*
import kotlinx.android.synthetic.main.popup_edittext.*
import java.io.*
import java.lang.Exception
import java.lang.StringBuilder
import kotlin.math.log

class MondayActivity : AppCompatActivity() {

    //    TODO: The whole SQLite database implementation

    var mealNames = arrayListOf<String>("Hello", "Test")

    //    TODO: REMOVE ALL FINDVIEWBYID, REPLACE USING LAMBDA. Makes code more precise since kotlin allows us to omit findviewbyid.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_days)

//      Update the macros on the top
        fileOutput()

        val mylistAdapter = listAdapter(this, mealNames)
        var listView = findViewById(R.id.list) as ListView
        listView.adapter = mylistAdapter



//        Set up OnClickListener for each textview macros so user can edit directly from there
//        TODO: Remove findviewbyid and directly use onclicklistener using ID names! This is the better Kotlin (Lambda) way!
        val cal = findViewById<TextView>(R.id.calories)
        cal.setOnClickListener {
            var setLine = "Enter your macros"
            var setEdit = "Calories"
            popup(setLine, setEdit)
        }

        val macroFats = findViewById<TextView>(R.id.fats)
        macroFats.setOnClickListener {
            var setLine = "Enter your macros"
            var setEdit = "Calories"
            popup(setLine, setEdit)
        }

        val macroCarbs = findViewById<TextView>(R.id.carbs)
        macroCarbs.setOnClickListener {
            var setLine = "Enter your macros"
            var setEdit = "Calories"
            popup(setLine, setEdit)
        }

        val macroProtein = findViewById<TextView>(R.id.protein)
        macroProtein.setOnClickListener {
            var setLine = "Enter your macros"
            var setEdit = "Calories"
            popup(setLine, setEdit)
        }




        val listview = findViewById<ListView>(R.id.list)
        val num = listview.count
        Toast.makeText(applicationContext, "num: " + num, Toast.LENGTH_SHORT).show()

        listview.setOnItemClickListener{parent, view, position, id ->
            val item = position
            Log.i("LSFSF", ":" + position)

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
        item ?: return false
        return when (item.itemId) {
            R.id.addmeals -> {
//                TODO: Make a new POPUP() function and use this popupwindow to ADD MEALS into the database and then put into the checkbox list!
//                val toast = Toast.makeText(applicationContext, "Adding Meals...", Toast.LENGTH_SHORT)
//                toast.show()
//                TODO: NEED NEW POPUP FUNCTION AS ITS VERY DIFFERENT
                popAddFood("Please enter the meal one and then press \"Add Another\" to add another meal. Press \"Finish\" once all meals are added.", "Enter Meal Name")
                true
            }
//            Just testing purposes, TODO: Delete this!
            R.id.test -> {
//                Toast.makeText(this, "test: ", Toast.LENGTH_SHORT).show()



                val listview = findViewById<ListView>(R.id.list)
                var a = 0
                while (a < listview.childCount) {
                    val c: CheckBox
                    c = listview.getChildAt(a) as CheckBox
                    if (c.isChecked) {
                        Log.i("CHECKED", ":: " + c.text.toString())
                    } else {
                        Log.i("NOT CHECKED", ":: " + c.text.toString())
                    }
                    a++
                }




                val num = listview.count
                val list = listview.checkedItemPositions
                val arrList = mutableListOf<String>()
//                Toast.makeText(applicationContext, "num: " + num, Toast.LENGTH_SHORT).show()
                var i = 0
                while( i < num) {
                    val bool = list.get(i)
                    Log.i("BOOL", ":: " + bool)
                    if (list.get(i)) {
                        Log.i("DEBUG", "HELLO?????")
                        arrList.add(listview.getItemAtPosition(i).toString())
                        Log.i("CHECKED:", "Here: " + listview.getItemAtPosition(i).toString())
                    }
                        i++
                }
                Toast.makeText(applicationContext, "checked: " + list, Toast.LENGTH_SHORT).show()


//                -----------------------------------------------------
                fileOutput()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

//    Pop-up-Window used for setting up macros
//    @param text -- A string variable which says what the popup window is for
//    @param edit -- A string variable which shows the hint behind edittext
    private fun popup(text: String, edit: String) {
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_edittext, null)
        val popupWindow = PopupWindow(
                view,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        popupWindow.setFocusable(true)
        popupWindow.update()

//        Sets up elevation for popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }
//        Only if above API level 23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            val slideOut = Slide()
            slideOut.slideEdge = Gravity.RIGHT
            popupWindow.exitTransition = slideOut
//            Toast.makeText(applicationContext, "Done!", Toast.LENGTH_SHORT).show()
        }

//        Set the text and hint for the popup view
        val changeText = view.findViewById<TextView>(R.id.popup)
        changeText.setText(text)
        val changeEdit = view.findViewById<EditText>(R.id.editMe)
        changeEdit.inputType = InputType.TYPE_CLASS_NUMBER
        changeEdit.setHint(edit)

//        Make new EDITTEXT and button and get their IDs
        val macroFatsID = addEditText(view, "Fats")
        val macroCarbsID = addEditText(view, "Carbs")
        val macroProteinID = addEditText(view, "Protein")
        val btnDone = addBtn(view, "Done")

//        Get references to the views
        val mealInput = view.findViewById<EditText>(R.id.editMe)
        val macroFats = view.findViewById<EditText>(macroFatsID)
        val macroCarbs = view.findViewById<EditText>(macroCarbsID)
        val macroProtein = view.findViewById<EditText>(macroProteinID)


//        Set onclicklistener to close the popup-window, update values, and store into .txt file internally
        val btnClose = view.findViewById<Button>(btnDone)
        btnClose.setOnClickListener {
            popupWindow.dismiss()

            val cals = mealInput.text.toString()
            val fats = macroFats.text.toString()
            val carbs = macroCarbs.text.toString()
            val protein = macroProtein.text.toString()

            findViewById<TextView>(R.id.calories).setText(cals)
            findViewById<TextView>(R.id.fats).setText(fats)
            findViewById<TextView>(R.id.carbs).setText(carbs)
            findViewById<TextView>(R.id.protein).setText(protein)

            val finalData = "Calories: " + cals + "\n" + "Fats: " + fats + "\n" + "Carbs: " + carbs + "\n" + "Protein: " + protein + "\n"
            fileInput(finalData)
        }

//        Show the popup window on top of the main root layout
        TransitionManager.beginDelayedTransition(root_layout)
        popupWindow.showAtLocation(
                root_layout,
                Gravity.CENTER,
                0,
                0
        )
    }

    //    Add new EditText inside the popup window
//    @param view -- reference to the layout
//    @param hint -- A string which is the hint for EditText
    fun addEditText(view: View, hint: String): Int {
        val linearLayout = view.findViewById<LinearLayout>(R.id.newWindow)
        val editText = EditText(this)
        editText.setHint(hint)
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.id = View.generateViewId()
        editText.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        linearLayout.addView(editText)
        return editText.id
    }

    //    Add new Button inside the popup window
//    @param view -- reference to the layout
//    @param text -- A string which is the text inside Button
    fun addBtn(view: View, text: String): Int {
        val linearLayout = view.findViewById<LinearLayout>(R.id.newWindow)
        val btn = Button(this)
        btn.text = text
        btn.id = View.generateViewId()
        btn.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        linearLayout.addView(btn)
        return btn.id
    }

    //    Create new or open if already created a file and input the data given by user
//    @param data -- A String which includes name of macro and amount of grams
    private fun fileInput(data: String) {
        val fileOutputStream: FileOutputStream
        val file = "macroDays.txt"
        try {
//            MODE_PRIVATE creates a new file and deletes old file if there
//            MODE_APPEND adds onto the previous file
            fileOutputStream = openFileOutput(file, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "File Not Found", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(applicationContext, "File Related Error", Toast.LENGTH_LONG).show()
        }
        Toast.makeText(applicationContext, "Data saved in file!", Toast.LENGTH_SHORT).show()
    }

    //    Read the data from the file and fill the macros when activity loads if user previously entered the data
    private fun fileOutput() {
        var fileInputStream: FileInputStream
        val file = "macroDays.txt"
//    Make sure the file exists, if not -- send a toast message saying file not found. (Delete later, once you remove MENU with ID @test in addmeal_menu
        try {
            fileInputStream = openFileInput(file)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "FILE NOT FOUND", Toast.LENGTH_SHORT).show()
            return
        }
        var inputSteamReader = InputStreamReader(fileInputStream)
        val bufferReader = BufferedReader(inputSteamReader)
//    TODO: Delete all stringBuilders in this method, as we don't need it anymore.
        val stringBuilder = StringBuilder()
        var text: String? = null
        var macroList = mutableListOf<String>()
        while ({ text = bufferReader.readLine(); text }() != null) {
            stringBuilder.append(text + "\n")
            macroList.add(breakString(text.toString()))
//            Log.i("TAG", "|" + text + "|")
        }
//        Log.i("MACRO", "|" + macroList[0] + "|")
//        Log.i("MACRO", "|" + macroList[1] + "|")
//        Log.i("MACRO", "|" + macroList[2] + "|")
//        Log.i("MACRO", "|" + macroList[3] + "|")

//    Set the macro values whenever the page is opened
        findViewById<TextView>(R.id.calories).setText(macroList[0])
        findViewById<TextView>(R.id.fats).setText(macroList[1])
        findViewById<TextView>(R.id.carbs).setText(macroList[2])
        findViewById<TextView>(R.id.protein).setText(macroList[3])

//    TODO: Delete this and delete outputHolder textview in XML as well after testing is done.
        outputHolder.setText(stringBuilder)
    }


    //    Break the data so we can get the macro numbers only
//    @param data -- A String which includes name of macro and amount of grams
//    @return String -- returns the number of grams in each macro
    private fun breakString(data: String): String {
        val parts = data.split(" ")
//        Log.d("SPLIT", "|" + parts[0] + "|")
//        Log.d("SPLIT", "|" + parts[1] + "|")
        return parts[1]
    }

    //    Pop-up-Window used for allowing users to add meals
//    @param text -- A string variable which says what the popup window is for
//    @param edit -- A string variable which shows the hint behind edittext
    private fun popAddFood(text: String, edit: String) {
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_edittext, null)
        val popupWindow = PopupWindow(
                view,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        popupWindow.setFocusable(true)
        popupWindow.update()

//        Sets up elevation for popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }
//        Only if above API level 23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow.enterTransition = slideIn

            val slideOut = Slide()
            slideOut.slideEdge = Gravity.RIGHT
            popupWindow.exitTransition = slideOut
//            Toast.makeText(applicationContext, "Done!", Toast.LENGTH_SHORT).show()
        }

//        Set the text and hint for the popup view
        val changeText = view.findViewById<TextView>(R.id.popup)
        changeText.setText(text)
        val changeEdit = view.findViewById<EditText>(R.id.editMe)
        changeEdit.inputType = InputType.TYPE_CLASS_TEXT
        changeEdit.setHint(edit)

//        Make new button and get the IDs
        val btnDone = addBtn(view, "Add Another")
        val btnFinished = addBtn(view, "Finished")

//        Get references to the views
        val mealInput = view.findViewById<EditText>(R.id.editMe)

//        Set onclicklistener to close the popup-window, update values, and store into .txt file internally
        val btnNew = view.findViewById<Button>(btnDone)
        btnNew.setOnClickListener {
            popupWindow.dismiss()

            val mealName = mealInput.text.toString()
            mealNames.add(mealName)

            Log.i("NEW", mealName)
            popAddFood(text, edit)
        }
        val btnclose = view.findViewById<Button>(btnFinished)
        btnclose.setOnClickListener {
            popupWindow.dismiss()

            val mealName = mealInput.text.toString()
            if (mealName.trim() != "") {
                Log.i("CLOSE", " NOT EMPTY")
                mealNames.add(mealName)
            }
            Log.i("CLOSE", "EMPTY")
            Log.i("CLOSE", mealName)
        }

//        Show the popup window on top of the main root layout
        TransitionManager.beginDelayedTransition(root_layout)
        popupWindow.showAtLocation(
                root_layout,
                Gravity.CENTER,
                0,
                0
        )
    }

}

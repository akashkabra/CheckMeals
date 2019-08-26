package com.example.android.checkmeals.DaysActivities

import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.transition.Slide
import android.transition.TransitionManager
import android.util.Log
import android.view.*
import android.widget.*
import com.example.android.checkmeals.Database.DBHelper
import com.example.android.checkmeals.Database.MealEntryTemp
import com.example.android.checkmeals.R
import kotlinx.android.synthetic.main.activity_days.*
import java.io.*
import java.lang.Exception

class MondayActivity : AppCompatActivity() {

//    TODO: Add Menu
//      - Delete all meals on specific day (These day activities...) (delete ALL from main activity only!)
//      - Uncheck all meals (Basically, a restart)

    //    TODO: The whole SQLite database implementation -- (MOSTLY done)
    lateinit var DBHelper: DBHelper

    //    TODO: REMOVE ALL FINDVIEWBYID, REPLACE USING LAMBDA. Makes code more precise since kotlin allows us to omit findviewbyid.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_days)

        DBHelper = DBHelper(this)


//      Update the macros on the top whenever this activity is open
        fileOutput()

//        Update the list of meals that user has entered
        updateView(getMeals())

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
                popAddFood("Enter meal name here and press \"Add Another\" to add another meal. \n Press \"Finish\" once all meals are added.", "Enter Meal Name")
                true
            }
//            Just testing purposes, TODO: Delete this!
            R.id.test -> {
                Toast.makeText(this, "test: ", Toast.LENGTH_SHORT).show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


//    Update the list that user sees whenever the user adds any meals
//    Uses the database to check the respectful checkboxes as well
//    @param data -- Takes in an arraylist of the meals
    fun updateView(data: ArrayList<MealEntryTemp>) {
//      Start fresh whenever this method is called
        val linearLayout = findViewById<LinearLayout>(R.id.scrollview)
        linearLayout.removeAllViews()

        data.forEach {
            val id = addCheckBox(it.mealName)
            val box = findViewById<CheckBox>(id)
            if (it.ate == 1) {
                box.isChecked = true
            } else {
                box.isChecked = false
            }

            box.setOnClickListener {
                if(box.isChecked) {
                    DBHelper.updateMeal("Monday", box.text.toString(), 1)
                    Log.i("CHECKBOX", "CHECKED: " + box.text)
                } else {
                    DBHelper.updateMeal("Monday", box.text.toString(), 2)
                    Log.i("CHECKBOX", "NOT CHECKED: " + box.text)
                }
            }
            box.setOnLongClickListener {
                Log.d("Long Press", "Long Press")
                DBHelper.deleteMeal(box.text.toString())
                finish()
                overridePendingTransition(0,0)
                startActivity(intent)
                overridePendingTransition(0,0)

                Toast.makeText(applicationContext, "Meal Deleted", Toast.LENGTH_SHORT).show()
                return@setOnLongClickListener true
            }

        }
    }


    fun addCheckBox(mealName: String): Int {
        val linearLayout = findViewById<LinearLayout>(R.id.scrollview)
        val checkBox = CheckBox(this)

        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        checkBox.setPadding(30,0,0,0)
        params.setMargins(30,20,20,20)

        checkBox.layoutParams = params
        checkBox.id = View.generateViewId()

        checkBox.setText(mealName)

        checkBox.textSize = 21F

        linearLayout.addView(checkBox)


        return checkBox.id
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
            Toast.makeText(applicationContext, "File Not Found.", Toast.LENGTH_SHORT).show()
            return
        }
        var inputSteamReader = InputStreamReader(fileInputStream)
        val bufferReader = BufferedReader(inputSteamReader)
        var text: String? = null
        var macroList = mutableListOf<String>()
        while ({ text = bufferReader.readLine(); text }() != null) {
            macroList.add(breakString(text.toString()))
        }

//    Set the macro values whenever the page is opened
        findViewById<TextView>(R.id.calories).setText(macroList[0])
        findViewById<TextView>(R.id.fats).setText(macroList[1])
        findViewById<TextView>(R.id.carbs).setText(macroList[2])
        findViewById<TextView>(R.id.protein).setText(macroList[3])
    }


//    Break the data so we can get the macro numbers only
//    @param data -- A String which includes name of macro and amount of grams
//    @return String -- returns the number of grams in each macro
    private fun breakString(data: String): String {
        val parts = data.split(" ")
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
            val mealName = mealInput.text.toString()
            if (mealName.trim() != "") {
                addMeals(mealName)
                Log.i("NEW", mealName)
                mealInput.text.clear()
            } else {
                Toast.makeText(applicationContext, "Please input a value", Toast.LENGTH_SHORT).show()
            }
        }
        val btnclose = view.findViewById<Button>(btnFinished)
        btnclose.setOnClickListener {
            popupWindow.dismiss()

            val mealName = mealInput.text.toString()
            if (mealName.trim() != "") {
                addMeals(mealName)
            }
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

//    Adds the meals into the database
//    @param mealName -- The name of the meal to add into the database
    fun addMeals(mealName: String) {
        DBHelper.insertMeal("Monday", mealName)
        updateView(getMeals())
    }

//    Get all the meals from the database that are in Monday
//    @return ArrayList -- Returns an arraylist of meals
    fun getMeals(): ArrayList<MealEntryTemp> {
        var data: ArrayList<MealEntryTemp>
        val meals = ArrayList<MealEntryTemp>()

        data = DBHelper.getData("Monday")
        data.forEach {
            val mealName = it.mealName
            val ate = it.ate
            meals.add(MealEntryTemp("Monday", mealName, ate))
        }
        return meals
    }
}

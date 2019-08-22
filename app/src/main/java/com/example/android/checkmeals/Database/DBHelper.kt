package com.example.android.checkmeals.Database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.time.DayOfWeek

class DBHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

//    Create the database
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

//    upgrade the database by deleting the old and making a new one
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_ENTRIES)
        DATABASE_VERSION++
        onCreate(db)
    }


//    Insert the meals users input into their days.
//    TODO: CHANGE INPUT PARAMETERS TO MEALENTRYTEMP, it will be one instead of two.
    @Throws(SQLiteConstraintException::class)
    fun insertMeal(day: String, mealName: String): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put(MealsDBContract.MealEntry.COLUMN_DAYS_OF_WEEK, day)
        values.put(MealsDBContract.MealEntry.COLUMN_MEAL_NAME, mealName)

        val newRow = db.insert(MealsDBContract.MealEntry.TABLE_NAME, null, values)

        return true
    }

//TODO: MAKE FUNCTION FOR UPDATE FOR WHEN USER EATS THE MEAL


//    TODO: DELETE FUNCTION.
//    @Throws(SQLiteConstraintException::class)
//    fun deleteMeal(mealName: String): Boolean {
//
//        val db = writableDatabase
//
//        return true
//    }

    fun getData(day: String): ArrayList<MealEntryTemp> {
        val db = writableDatabase
        val finalData = ArrayList<MealEntryTemp>()
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("SELECT * FROM " + MealsDBContract.MealEntry.TABLE_NAME +
                    " WHERE " + MealsDBContract.MealEntry.COLUMN_DAYS_OF_WEEK + " = '" + day + "'", null)
        } catch (e: SQLiteException) {
//            There is an error while trying to get the data
            db.execSQL(SQL_CREATE_ENTRIES)
            Log.i("DBHelper", "Error getting data or no data saved yet.")
            return ArrayList()
        }
        var foodName: String
        var dayName: String
        var ateOR: Int
        if(cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                foodName = cursor.getString(cursor.getColumnIndex(MealsDBContract.MealEntry.COLUMN_MEAL_NAME))
                ateOR = cursor.getInt(cursor.getColumnIndex(MealsDBContract.MealEntry.COLUMN_ATE))
                finalData.add(MealEntryTemp(day, foodName, ateOR))
                cursor.moveToNext()
            }
        }
        return  finalData
    }








    companion object {
        var DATABASE_VERSION = 1
        val DATABASE_NAME = "Meals.db"

        private val SQL_CREATE_ENTRIES =
                "CREATE TABLE " + MealsDBContract.MealEntry.TABLE_NAME + " (" +
                        MealsDBContract.MealEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        MealsDBContract.MealEntry.COLUMN_DAYS_OF_WEEK + " TEXT NOT NULL," +
                        MealsDBContract.MealEntry.COLUMN_MEAL_NAME + " TEXT NOT NULL," +
                        MealsDBContract.MealEntry.COLUMN_ATE + " INTEGER DEFAULT 0)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MealsDBContract.MealEntry.TABLE_NAME
    }
}
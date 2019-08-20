package com.example.android.checkmeals.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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

    fun getData() {

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
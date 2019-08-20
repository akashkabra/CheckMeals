package com.example.android.checkmeals.Database

import android.provider.BaseColumns

object MealsDBContract {

    class MealEntry: BaseColumns {

        companion object {

//            Table and column definitions
            val TABLE_NAME = "user_meals"
            val COLUMN_ID = BaseColumns._ID
            val COLUMN_DAYS_OF_WEEK = "daysofweek"
            val COLUMN_MEAL_NAME = "meal_name"
            val COLUMN_ATE = "ate"
        }
    }
}
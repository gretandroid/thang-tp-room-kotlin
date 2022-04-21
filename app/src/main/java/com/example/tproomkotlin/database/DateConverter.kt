package com.example.tproomkotlin.database

import androidx.room.TypeConverter
import java.util.*


object DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date {
        return Date(timestamp!!)
    }

    @TypeConverter
    fun fromTimeStamp(date: Date): Long {
        return date.time
    }
}
package com.artemchen.android.apod.database

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ApodTypeConverters {
    @TypeConverter
    fun fromDate(date: LocalDate?): String? {
        return date?.toString()
    }

    @TypeConverter
    fun toDate(date: String?): LocalDate? {
        return try {
            LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
        } catch(e: Exception) {
            null
        }
    }
}
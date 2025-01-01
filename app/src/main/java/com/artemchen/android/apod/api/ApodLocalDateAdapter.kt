package com.artemchen.android.apod.api

import com.artemchen.android.apod.database.ApodLocalDate
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ApodLocalDateAdapter {
    @ToJson
    fun toJson(@ApodLocalDate date: LocalDate): String {
        throw NotImplementedError("ApodLocalAdapter @ToJson not implemented")
    }

    @FromJson
    @ApodLocalDate
    fun fromJson(date: String): LocalDate? {
        //TODO: add exception handling for date parsing
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
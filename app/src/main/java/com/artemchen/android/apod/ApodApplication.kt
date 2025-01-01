package com.artemchen.android.apod

import android.app.Application
import com.artemchen.android.apod.database.ApodDbRepository

class ApodApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ApodDbRepository.initialize(this)
    }
}
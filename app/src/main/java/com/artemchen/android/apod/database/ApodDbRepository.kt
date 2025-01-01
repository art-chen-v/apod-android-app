package com.artemchen.android.apod.database

import android.content.Context
import androidx.room.Room

private const val DATABASE_NAME = "apod-database"

class ApodDbRepository private constructor(context: Context) {

    val database: ApodRoomDb = Room
        .databaseBuilder(
            context.applicationContext,
            ApodRoomDb::class.java,
            DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()

    companion object {
        private var INSTANCE: ApodDbRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ApodDbRepository(context)
            }
        }

        fun get(): ApodDbRepository {
            return INSTANCE ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}
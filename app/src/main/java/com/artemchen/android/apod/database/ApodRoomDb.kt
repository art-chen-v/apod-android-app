package com.artemchen.android.apod.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [ApodPhotoItemDb::class, ApodRemoteKeys::class], version=3)
@TypeConverters(ApodTypeConverters::class)
abstract class ApodRoomDb : RoomDatabase() {
    abstract fun apodDao(): ApodDao
    abstract fun remoteKeysDao(): ApodRemoteKeysDao
}

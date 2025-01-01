package com.artemchen.android.apod.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apod_remote_key")
data class ApodRemoteKeys(
    @PrimaryKey
    val pageNumber: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
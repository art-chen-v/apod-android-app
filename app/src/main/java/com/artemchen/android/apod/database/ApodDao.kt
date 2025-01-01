package com.artemchen.android.apod.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ApodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(apodDbItems: List<ApodPhotoItemDb>)

    @Query("SELECT * FROM apods")
    suspend fun getAllApods(): List<ApodPhotoItemDb>

    @Query("SELECT * FROM apods ORDER BY id")
    fun getApods(): PagingSource<Int, ApodPhotoItemDb>

    @Query("DELETE FROM apods")
    suspend fun clearAll()
}


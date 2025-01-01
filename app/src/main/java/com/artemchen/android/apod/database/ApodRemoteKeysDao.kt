package com.artemchen.android.apod.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKey: ApodRemoteKeys)

    @Query("SELECT * FROM apod_remote_key")
    suspend fun getRemoteKeys(): List<ApodRemoteKeys>

    @Query("SELECT * FROM apod_remote_key WHERE pageNumber = :pageNumber")
    suspend fun getRemoteKey(pageNumber: Int): ApodRemoteKeys?
//
//    @Query("SELECT * FROM apods WHERE date < :startAfter ORDER BY id ASC LIMIT :limit")
//    suspend fun getApods(startAfter: String?, limit: Int): List<ApodPhotoItemDb>
//
//    @Query("SELECT * FROM apods ORDER BY id ASC LIMIT :limit")
//    suspend fun getApods(limit: Int): List<ApodPhotoItemDb>
//
//    @Query("SELECT * FROM apods ORDER BY id ASC")
//    suspend fun getApods(): List<ApodPhotoItemDb>
//
//    @Query("SELECT * FROM apods ORDER BY id ASC LIMIT :limit")
//    fun getApodsFlow(limit: Int): Flow<List<ApodPhotoItemDb>>
//
//    @Query("DELETE FROM apods")
//    suspend fun clearAll()



    @Query("DELETE FROM apods")
    suspend fun clearAll()
}
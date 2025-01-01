package com.artemchen.android.apod.api

import com.artemchen.android.apod.database.ApodPhotoItemDb
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {
    @GET("apod")
    suspend fun getCurrentDatePhoto(): ApodPhotoItemDb

    @GET("apod")
    suspend fun getPhotos(@Query("start_date") startDate: String,
                          @Query("end_date") endDate: String): List<ApodPhotoItemDb>
}
package com.artemchen.android.apod.api

import com.artemchen.android.apod.database.ApodPhotoItemDb
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


object PhotoRepository {
    private val baseUrl = "https://api.nasa.gov/planetary/"
    private val retrofit: Retrofit

    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(PhotoInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        val moshi = Moshi
            .Builder()
            .add(ApodLocalDateAdapter())
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    private val apodApi: ApodApi by lazy {
        retrofit.create<ApodApi>()
    }

    suspend fun fetchCurrentDatePhoto(): ApodPhotoItemDb =
        apodApi.getCurrentDatePhoto()

    suspend fun fetchPhotos(startDate: String, endDate: String): List<ApodPhotoItemDb> =
        apodApi.getPhotos(startDate, endDate)
}
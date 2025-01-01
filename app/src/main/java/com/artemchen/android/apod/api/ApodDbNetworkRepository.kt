package com.artemchen.android.apod.api

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.artemchen.android.apod.database.ApodDbRepository
import com.artemchen.android.apod.database.ApodPhotoItemDb
import com.artemchen.android.apod.ui.gallery.ApodGalleryRemoteMediator
import kotlinx.coroutines.flow.Flow

class ApodDbNetworkRepository() {

    val apodDb = ApodDbRepository.get().database

    @OptIn(ExperimentalPagingApi::class)
    fun getPagedItems(): Flow<PagingData<ApodPhotoItemDb>> {
        return Pager(
           config = PagingConfig(20, prefetchDistance = 10),
            remoteMediator = ApodGalleryRemoteMediator(apodDb, PhotoRepository)
        ) {
            apodDb.apodDao().getApods()
        }.flow
    }
}
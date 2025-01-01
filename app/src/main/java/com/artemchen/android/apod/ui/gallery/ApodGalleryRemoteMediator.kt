package com.artemchen.android.apod.ui.gallery

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.artemchen.android.apod.api.PhotoRepository
import com.artemchen.android.apod.database.ApodPhotoItemDb
import com.artemchen.android.apod.database.ApodRemoteKeys
import com.artemchen.android.apod.database.ApodRoomDb
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

private const val TAG = "ApodGalleryRemoteMediator"

@OptIn(ExperimentalPagingApi::class)
class ApodGalleryRemoteMediator(
    private val database: ApodRoomDb,
    private val networkService: PhotoRepository
) : RemoteMediator<Int, ApodPhotoItemDb>() {

    val apodDao = database.apodDao()
    val apodRemoteKeysDao = database.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        if (apodDao.getAllApods().isEmpty()) {
            return InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            return InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ApodPhotoItemDb>
    ): MediatorResult {
        return try {
//            Log.d(TAG, "Inside Remote mediator, Before loadType $loadType")
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()?.date ?: return MediatorResult.Success(endOfPaginationReached = false)
                }
            }

            Log.d(TAG, "Inside Remote mediator, Load key is $loadKey")

            // Adding page numbers to apod items
            val remoteKey = if (loadType == LoadType.REFRESH) {
                ApodRemoteKeys(pageNumber = 0, prevKey = null, nextKey = 1)
            } else {
                val lastRemoteKey = apodRemoteKeysDao.getRemoteKeys().last()
                lastRemoteKey.nextKey?.let {
                    ApodRemoteKeys(
                        pageNumber = it,
                        prevKey = lastRemoteKey.pageNumber,
                        nextKey = lastRemoteKey.nextKey + 1
                    )
                }
            }

            // Fetching next batch of data
            val endDate = if (loadKey == null) {
                val currentDatePhoto = networkService.fetchCurrentDatePhoto()
                currentDatePhoto.date
            } else {
                loadKey.minusDays(1L)
            }

            val loadSize = if (loadType == LoadType.REFRESH) {
                state.config.pageSize.toLong()
            } else {
                state.config.pageSize.toLong()
            }
            val startDateStr = endDate?.minusDays(loadSize).toString()

            val endDateStr = endDate.toString()
            val apodPhotoItemsDb = networkService.fetchPhotos(startDateStr, endDateStr).reversed().map {
                it.copy(pageNumber = remoteKey?.pageNumber)
            }

            // Saving into database
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    apodDao.clearAll()
                    apodRemoteKeysDao.clearAll()
                }

                Log.d(TAG, "Inside Remote mediator, Inserting data ${apodPhotoItemsDb.size}")

                apodDao.insertAll(apodPhotoItemsDb)
                remoteKey?.let {
                    apodRemoteKeysDao.insert(it)
                }
            }

            MediatorResult.Success(
                endOfPaginationReached = apodPhotoItemsDb.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}
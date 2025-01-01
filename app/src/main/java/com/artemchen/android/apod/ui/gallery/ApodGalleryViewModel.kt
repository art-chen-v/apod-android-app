package com.artemchen.android.apod.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.artemchen.android.apod.api.ApodDbNetworkRepository
import com.artemchen.android.apod.api.PhotoRepository
import com.artemchen.android.apod.database.ApodDao
import com.artemchen.android.apod.database.ApodDbRepository

private const val TAG = "ApodGalleryViewModel"

class ApodGalleryViewModel : ViewModel() {

    val apodItems = ApodDbNetworkRepository().getPagedItems()
}
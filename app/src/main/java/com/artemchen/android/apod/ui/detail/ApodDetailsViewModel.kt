package com.artemchen.android.apod.ui.detail

import androidx.lifecycle.ViewModel
import com.artemchen.android.apod.database.ApodPhotoItemDb
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "ApodDetailsViewModel"

class ApodDetailsViewModel : ViewModel() {

    private val _apodItem: MutableStateFlow<ApodPhotoItemDb?> = MutableStateFlow(null)
    val apodItem: StateFlow<ApodPhotoItemDb?> = _apodItem.asStateFlow()

    fun onApodItemClicked(apodPhotoItem: ApodPhotoItemDb) {
        _apodItem.value = apodPhotoItem
    }
}


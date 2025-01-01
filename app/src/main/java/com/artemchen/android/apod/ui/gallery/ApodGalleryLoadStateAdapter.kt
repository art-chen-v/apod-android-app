package com.artemchen.android.apod.ui.gallery

import android.view.ViewGroup
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ApodGalleryLoadStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<ApodGalleryLoadStateViewHolder>() {

    override fun onBindViewHolder(
        holder: ApodGalleryLoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = ApodGalleryLoadStateViewHolder(parent, retry)

}

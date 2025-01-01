package com.artemchen.android.apod.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.artemchen.android.apod.database.ApodPhotoItemDb
import com.artemchen.android.apod.databinding.GridItemGalleryBinding

class ApodPagingAdapter(private val onApodItemClicked: (apodPhotoItem: ApodPhotoItemDb) -> Unit): PagingDataAdapter<ApodPhotoItemDb, ApodGalleryItemViewHolder>(PhotoComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodGalleryItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = GridItemGalleryBinding.inflate(inflater, parent, false)
        return ApodGalleryItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApodGalleryItemViewHolder, position: Int) {
        val item = getItem(position)

        item?.let {
            holder.bind(it, onApodItemClicked)
        }
    }
}

object PhotoComparator: DiffUtil.ItemCallback<ApodPhotoItemDb>() {
    override fun areItemsTheSame(oldItem: ApodPhotoItemDb, newItem: ApodPhotoItemDb): Boolean {
        // Id is unique.
        return oldItem.mediaUrl == newItem.mediaUrl
    }

    override fun areContentsTheSame(oldItem: ApodPhotoItemDb, newItem: ApodPhotoItemDb): Boolean {
        return oldItem == newItem
    }
}
package com.artemchen.android.apod.ui.gallery

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.artemchen.android.apod.database.ApodPhotoItemDb
import com.artemchen.android.apod.database.getImageUrl
import com.artemchen.android.apod.databinding.GridItemGalleryBinding

class ApodGalleryItemViewHolder(private val binding: GridItemGalleryBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(apodItem: ApodPhotoItemDb, onApodItemClicked: (apodItem: ApodPhotoItemDb) -> Unit) {

        binding.apply {
            itemImageView.load(apodItem.getImageUrl())
            itemTitle.text = apodItem.title
            itemDate.text = apodItem.date.toString()

            root.setOnClickListener {
                onApodItemClicked(apodItem)
            }
        }
    }
}


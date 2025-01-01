package com.artemchen.android.apod.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.JsonQualifier
import java.time.LocalDate

@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class ApodLocalDate

@Entity(tableName = "apods")
@JsonClass(generateAdapter = true)
data class ApodPhotoItemDb(
    @PrimaryKey(autoGenerate = true)
    @Json(ignore = true)
    val id: Int = 0,
    @ApodLocalDate val date: LocalDate?,
    var copyright: String?,
    val explanation: String,
    @Json(name = "media_type")
    val mediaType: String,
    @Json(name = "service_version")
    val serviceVersion: String,
    val title: String,
    @Json(name = "url")
    val mediaUrl: String? = null,
    @Json(ignore = true)
    var pageNumber: Int? = null
)

fun ApodPhotoItemDb.getImageUrl(): String {
    return if (mediaUrl != null) {
        if (this.mediaType == "video") {
            val videoId = this.mediaUrl.split("/").last().split("?").first()
            "https://img.youtube.com/vi/$videoId/default.jpg"
        } else {
            this.mediaUrl
        }
    } else {
        ""
    }
}
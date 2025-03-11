package com.rahullohra.instagram.media

import kotlinx.serialization.Serializable

@Serializable
data class MediaItem(
    val mediaType: String, // "photo" or "video"
    val mimeType: String, //jpeg/ png/ mp4, ....
    val fileName: String, // URL of the media
    val thumbnailUrl: String? = null, // Optional thumbnail for videos
    val order: Int // Order of the media in the post
)

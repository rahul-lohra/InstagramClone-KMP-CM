package com.rahullohra.instagram.feed

import com.rahullohra.instagram.media.MediaItem
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime

@Serializable
data class FeedResponse(
    val feedResponseItems: List<FeedResponseItem>,
    val nextCursor: LocalDateTime?
)

@Serializable
data class FeedResponseItem(
    val postId: String,
    val media: List<MediaItem>,
    val createdAt: String,
    val userName: String,
    val postLocation: String,
    val isUserVerified: Boolean,
    val likesCount: String,
    val postDescription: String,
    val tags: String,
    val caption: String,
)


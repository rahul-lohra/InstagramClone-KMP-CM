package com.rahullohra.instagram.media

import com.rahullohra.instagram.post.Post
import com.rahullohra.instagram.MAX_VARCHAR_LENGTH
import com.rahullohra.instagram.feed.FeedsTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import java.util.UUID

class Media(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Media>(MediaTable)

    var mimeType by MediaTable.mimeType
    var mediaType by MediaTable.mediaType
    var url by MediaTable.url
    var postId by MediaTable.postId

    var post by Post referencedOn FeedsTable.postId // Reference to the Post
}

object MediaTable: UUIDTable("media") {
    val mimeType = varchar("mimetype", MAX_VARCHAR_LENGTH) //jpeg/ png/ mp4, ....
    val mediaType = varchar("mediaType", MAX_VARCHAR_LENGTH) //photo or video
    val url = varchar("url", MAX_VARCHAR_LENGTH)
    val postId = integer("postId")
}

@Serializable
data class MediaItem(
    val mediaType: String, // "photo" or "video"
    val url: String, // URL of the media
    val thumbnailUrl: String? = null, // Optional thumbnail for videos
    val order: Int // Order of the media in the post
)
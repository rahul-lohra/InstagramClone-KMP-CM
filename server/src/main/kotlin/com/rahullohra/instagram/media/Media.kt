package com.rahullohra.instagram.media

import com.rahullohra.instagram.MAX_VARCHAR_LENGTH
import com.rahullohra.instagram.post.Post
import com.rahullohra.instagram.post.PostsTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

class Media(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Media>(MediaTable)

    var mimeType by MediaTable.mimeType
    var mediaType by MediaTable.mediaType
    var fileName by MediaTable.fileName
    var mediaUrl by MediaTable.mediaUrl
    var post by Post optionalReferencedOn MediaTable.postId
    var createdAt by MediaTable.createdAt
}

object MediaTable: UUIDTable("media") {
    val mimeType = varchar("mimetype", MAX_VARCHAR_LENGTH) //jpeg/ png/ mp4, ....
    val mediaType = enumerationByName("mediaType", 10, MediaType::class)
    val fileName = varchar("fileName", MAX_VARCHAR_LENGTH)
    val mediaUrl = varchar("media_url", MAX_VARCHAR_LENGTH)
    val postId = reference("post_id", PostsTable).nullable() // FK to Post table
    val createdAt = datetime("created_at")
}

enum class MediaType {
    IMAGE, VIDEO
}

data class MediaUploadResponse(
    val mediaIds: List<String>
)
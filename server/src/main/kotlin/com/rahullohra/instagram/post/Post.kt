package com.rahullohra.instagram.post

import com.rahullohra.instagram.user.User
import com.rahullohra.instagram.user.UsersTable
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

class Post(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Post>(PostsTable)

    var user by User referencedOn PostsTable.userId // Reference to the User who created the post
    var caption by PostsTable.caption // Caption for the post
    var tags by PostsTable.tags // Tags associated with the post
    var createdAt by PostsTable.createdAt // Timestamp of when the post was created
    var visibility by PostsTable.visibility // ENUM for visibility (public, private)
    var isActive by PostsTable.isActive // Soft delete flag
    var location by PostsTable.location
    var postDescription by PostsTable.postDescription
}

object PostsTable : UUIDTable("posts") {
    val userId = reference("user_id", UsersTable) // FK to Users table
    val caption = text("caption").nullable() // Caption
    val tags = text("tags").nullable() // JSON/Array of tags
    val createdAt = datetime("created_at") // Timestamp
    val visibility = enumerationByName("visibility", 10, Visibility::class) // ENUM (public, private)
    val isActive = bool("is_active").default(true) // Soft delete flag
    val location = varchar("location", 255).nullable()
    val postDescription = text("post_description").nullable()
}

enum class ContentType {
    IMAGE, VIDEO, REEL
}

enum class Visibility {
    PUBLIC, PRIVATE
}

@Serializable
data class CreatePostRequest(
    val userId: String, // User ID creating the post
    val caption: String? = null,
    val location: String? = null,
    val mediaIds: List<String>, // List of media items
    val tags: List<String>, // List of media items
    val visibility: Visibility, // List of media items
    val description: String? = null
)

@Serializable
data class CreatePostResponse(
    val postId: String,
    val message: String,
)

// Simulate a function to save the post to the database
fun createPostInDatabase(request: CreatePostRequest, userId: String): String {
    // Generate a new post ID (in a real app, you'd use a database auto-generated ID)
    val postId = "post-${System.currentTimeMillis()}"

    Post.new(UUID.fromString(postId)) {
        user = User.findById(UUID.fromString(userId))!!
//                contentType = ContentType.valueOf(row["content_type"]!!)
//                contentUrl = row["content_url"]!!
        caption = request.caption
        tags = request.tags.joinToString(",").removeSuffix(",")
        createdAt = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        visibility = request.visibility
        isActive = false // will be active when media is also uploaded
        location = "New Delhi, India"
        postDescription = request.description
    }
    return postId
}
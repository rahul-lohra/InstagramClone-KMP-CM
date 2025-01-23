package com.rahullohra.instagram.comment

import com.rahullohra.instagram.post.Post
import com.rahullohra.instagram.post.PostsTable
import com.rahullohra.instagram.user.User
import com.rahullohra.instagram.user.UsersTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

class Comment(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Comment>(CommentsTable)

    var post by Post referencedOn CommentsTable.postId // Post the comment is associated with
    var user by User referencedOn CommentsTable.userId // User who made the comment
    var commentText by CommentsTable.commentText // Content of the comment
    var createdAt by CommentsTable.createdAt // Creation timestamp
}

object CommentsTable : UUIDTable("comments") {
    val postId = reference("post_id", PostsTable) // FK to Posts table
    val userId = reference("user_id", UsersTable) // FK to Users table
    val commentText = text("comment_text") // Content of the comment
    val createdAt = datetime("created_at") // Timestamp
}
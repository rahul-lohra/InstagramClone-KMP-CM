package com.rahullohra.instagram.like

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

class Like(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Like>(LikesTable)

    var user by User referencedOn LikesTable.userId // User who liked the post
    var post by Post referencedOn LikesTable.postId // Post that was liked
    var createdAt by LikesTable.createdAt // Creation timestamp
}

object LikesTable : UUIDTable("likes") {
    val userId = reference("user_id", UsersTable) // FK to Users table
    val postId = reference("post_id", PostsTable) // FK to Posts table
    val createdAt = datetime("created_at") // Timestamp
}
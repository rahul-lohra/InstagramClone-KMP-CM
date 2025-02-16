package com.rahullohra.instagram.feed

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

class Feed(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Feed>(FeedsTable)

    var user by User referencedOn FeedsTable.userId // Reference to the User
    var post by Post referencedOn FeedsTable.postId // Reference to the Post
    var rankingScore by FeedsTable.rankingScore // Ranking score for the feed
    var createdAt by FeedsTable.createdAt // Creation timestamp
}

object FeedsTable : UUIDTable("feeds") {
    val userId = reference("user_id", UsersTable) // FK to Users table
    val postId = reference("post_id", PostsTable) // FK to Posts table
    val rankingScore = float("ranking_score").default(0.0f) // Ranking score
    val createdAt = datetime("created_at") // Timestamp

    init {
        index(false, createdAt) // Index for pagination
        index(false, userId) // Index for filtering by user
        index(false, userId, createdAt) // Best index for filtering + pagination
    }
}







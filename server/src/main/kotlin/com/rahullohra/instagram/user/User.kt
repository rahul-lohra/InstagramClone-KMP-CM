package com.rahullohra.instagram.user

import com.rahullohra.instagram.follower.Follower
import com.rahullohra.instagram.follower.FollowersTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(UsersTable)

    var username by UsersTable.username
    var email by UsersTable.email
    var createdAt by UsersTable.createdAt

    // Relationship: List of followers for this user
    val followers by Follower referrersOn FollowersTable.userId

    // Relationship: List of users this user is following
    val following by Follower referrersOn FollowersTable.followerUserId
}

object UsersTable : UUIDTable("users") {
    val username = varchar("username", 50).uniqueIndex() // Username
    val email = varchar("email", 255).uniqueIndex() // Email
    val createdAt = datetime("created_at") // Timestamp
}
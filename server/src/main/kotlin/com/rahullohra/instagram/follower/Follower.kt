package com.rahullohra.instagram.follower

import com.rahullohra.instagram.user.User
import com.rahullohra.instagram.user.UsersTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

object FollowersTable : UUIDTable("followers") {
    val userId = reference("user_id", UsersTable) // User being followed
    val followerUserId = reference("follower_user_id", UsersTable) // User who is following
    val createdAt = datetime("created_at") // Timestamp
}
class Follower(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Follower>(FollowersTable)

    var user by User referencedOn FollowersTable.userId // The user being followed
    var follower by User referencedOn FollowersTable.followerUserId // The user who is following
    var createdAt by FollowersTable.createdAt
}

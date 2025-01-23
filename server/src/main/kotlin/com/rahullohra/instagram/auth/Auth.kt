package com.rahullohra.instagram.auth

import com.rahullohra.instagram.follower.FollowersTable
import com.rahullohra.instagram.user.User
import com.rahullohra.instagram.user.UsersTable
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class Auth(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Auth>(AuthTable)

    var user by User referencedOn AuthTable.userId // The user being followed
    var token by AuthTable.token
    var refreshToken by AuthTable.refreshToken
    var createdAt by AuthTable.createdAt
    var expiryAt by AuthTable.expiryAt
}

object AuthTable : UUIDTable("auth") {
    val userId = reference("user_id", UsersTable) // User being followed
    val token = varchar("token", 255).uniqueIndex() // Email
    val refreshToken = varchar("refresh_token", 255).uniqueIndex() // Email
    val createdAt = datetime("created_at") // Timestamp
    val expiryAt = datetime("expiry_at") // Timestamp
}

object AuthUtils{
    fun getUserIdFromToken(token: String): String {
        return transaction {
            Auth.find { AuthTable.token eq token }.singleOrNull()?.user?.id.toString()
        }
    }
}

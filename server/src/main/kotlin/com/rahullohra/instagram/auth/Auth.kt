package com.rahullohra.instagram.auth

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
//    var refreshTokenExpiry by AuthTable.refreshTokenExpiry
    var createdAt by AuthTable.createdAt
}

/**
 * TODO Instead of saving raw token and refresh token save their hashes
 */
object AuthTable : UUIDTable("auth") {
    val userId = reference("user_id", UsersTable) // User being followed
    val token = text("token").uniqueIndex() // Email
    val refreshToken = text("refresh_token").uniqueIndex()
//    val refreshTokenExpiry = datetime("refresh_token_expiry") // Timestamp
    val createdAt = datetime("created_at") // Timestamp
}

object AuthUtils{
    fun getUserIdFromToken(token: String): String {
        return transaction {
            Auth.find { AuthTable.token eq token }.singleOrNull()?.user?.id.toString()
        }
    }
}

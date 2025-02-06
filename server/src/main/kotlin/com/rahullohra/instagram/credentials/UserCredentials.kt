package com.rahullohra.instagram.credentials

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

class UserCredentials(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<UserCredentials>(UserCredentialsTable)

    var username by UserCredentialsTable.username
    var hashedPassword by UserCredentialsTable.hashedPassword
    var createdAt by UserCredentialsTable.createdAt
}

object UserCredentialsTable : UUIDTable("credentials") {
    val username = varchar("username", 255).uniqueIndex()
    val hashedPassword = varchar("hashedPassword", 255).uniqueIndex()
    val createdAt = datetime("created_at")
}
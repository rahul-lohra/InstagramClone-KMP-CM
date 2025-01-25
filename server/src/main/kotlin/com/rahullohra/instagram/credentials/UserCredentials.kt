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
    var password by UserCredentialsTable.password
    var createdAt by UserCredentialsTable.createdAt
}

object UserCredentialsTable : UUIDTable("credentials") {
    val username = varchar("token", 255).uniqueIndex() // Email
    val password = varchar("token", 255).uniqueIndex() // Email
    val createdAt = datetime("created_at")
}
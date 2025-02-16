package com.rahullohra.instagram.auth.data.local

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import com.rahullohra.instagram.auth.data.UserAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

class AuthStore (private val produceFilePath: () -> String) {

    private val db = DataStoreFactory.create(

        storage = OkioStorage<UserAuth>(
            fileSystem = FileSystem.SYSTEM,
            serializer = AuthJsonSerializer,
            producePath = {
                getFilePath(produceFilePath()).toPath()
            },
        ),
    )

    suspend fun storeCredentials(userAuth: UserAuth){
        db.updateData {
            userAuth
        }
    }

    fun getCredentials(): Flow<UserAuth> {
        return db.data
    }
}

internal object AuthJsonSerializer : OkioSerializer<UserAuth> {
    override val defaultValue: UserAuth = UserAuth("","")

    override suspend fun readFrom(source: BufferedSource): UserAuth {
        return try {
            Json.decodeFromString(source.buffer.readUtf8())
        } catch (e: Exception) {
            defaultValue // Return default values if there's an issue
        }
    }
    override suspend fun writeTo(t: UserAuth, sink: BufferedSink) {
        sink.buffer.writeUtf8(Json.encodeToString(t))
    }
}
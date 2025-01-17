package com.rahullohra.instagram

import com.rahullohra.instagram.feed.Feed
import com.rahullohra.instagram.feed.FeedsTable
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

fun main() {

    IgDatabase.setup()

    embeddedServer(Netty, port = SERVER_PORT, host = "0.0.0.0", module = Application::module)
        .start(wait = true)

}

fun Application.module() {
    install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) {
        json()
    }

    routing {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
        get("/stories") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
        get("/feeds/{userId}") {
            val userId = call.parameters["userId"]
            if (userId == null) {
                call.respond(mapOf("error" to "User ID is required"))
                return@get
            }

            // Fetch feeds for the user
            val feeds = getFeedsForUser(IgDatabase.database, userId)
            call.respond(feeds)
        }
        get("/feed") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
    }
}

fun getFeedsForUser(database: Database, userId: String): List<FeedResponse> {
    return transaction(database) {
        // Fetch feeds for the user
        Feed.find { FeedsTable.userId eq UUID.fromString(userId) }.map { feed ->
            FeedResponse(
                postId = feed.post.id.value.toString(),
                contentUrl = feed.post.contentUrl,
                caption = feed.post.caption ?: "",
                tags = feed.post.tags ?: "[]",
                createdAt = feed.post.createdAt.toString()
            )
        }
    }
}

@Serializable
data class FeedResponse(
    val postId: String,
    val contentUrl: String,
    val caption: String,
    val tags: String,
    val createdAt: String
)
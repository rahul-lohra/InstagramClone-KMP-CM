package com.rahullohra.instagram.feed

import com.rahullohra.instagram.ErrorResponse
import com.rahullohra.instagram.IgDatabase
import com.rahullohra.instagram.SuccessResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID


fun Routing.feedRoutes() {

    get("/feeds/{userId}") {
        val userId = call.parameters["userId"]
        if (userId == null) {
            call.respond(
                status = HttpStatusCode.NotFound,
                ErrorResponse(HttpStatusCode.NotFound.value, "User ID is required")
            )
            return@get
        }

        val feeds = getFeedsForUser(IgDatabase.database, userId)
        call.respond(status = HttpStatusCode.OK, feeds)
    }

    get("/feed") {
        call.respond(status = HttpStatusCode.OK, message = SuccessResponse(
            "Hello instagram feed"
        ))
    }
}

fun getFeedsForUser(database: Database, userId: String): List<FeedResponse> {
    return transaction(database) {
        // Fetch feeds for the user
        Feed.find { FeedsTable.userId eq UUID.fromString(userId) }.map { feed ->
            FeedResponse(
                postId = feed.post.id.value.toString(),
//                contentUrl = feed.post.contentUrl,
                caption = feed.post.caption ?: "",
                tags = feed.post.tags ?: "[]",
                createdAt = feed.post.createdAt.toString()
            )
        }
    }
}
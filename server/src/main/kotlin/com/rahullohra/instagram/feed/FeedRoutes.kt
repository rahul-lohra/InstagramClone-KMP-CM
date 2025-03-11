package com.rahullohra.instagram.feed


import com.rahullohra.instagram.like.Like
import com.rahullohra.instagram.like.LikesTable
import com.rahullohra.instagram.media.Media
import com.rahullohra.instagram.media.MediaItem
import com.rahullohra.instagram.media.MediaTable
import com.rahullohra.instagram.models.ApiResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID


fun Routing.feedRoutes() {

//    get("/feeds/{userId}") {
//        val userId = call.parameters["userId"]
//        if (userId == null) {
//            call.respond(
//                status = HttpStatusCode.NotFound,
//                ApiResponse(
//                    code = HttpStatusCode.NotFound.value,
//                    true,
//                    "User ID is required",
//                    Unit
//                )
//            )
//            return@get
//        }

//        val feeds = getPaginatedFeeds(IgDatabase.database, userId)
//        call.respond(status = HttpStatusCode.OK, "Sorry")
//    }

    get("/feed") {
        val cursorParam = call.request.queryParameters["cursor"]
        val userId = call.request.queryParameters["userId"]
        val limit = call.request.queryParameters["limit"]?.toIntOrNull() ?: 10

        val cursor = cursorParam?.let {
            Instant.parse(it).toLocalDateTime(kotlinx.datetime.TimeZone.UTC)
        }
        val feeds = getPaginatedFeeds(cursor, limit, userId)
        val nextCursor = feeds.lastOrNull()?.createdAt

        val feedResponseItems = transaction {
            feeds.map { feed ->

                val likesCount = Like.find {
                  LikesTable.postId eq feed.post.id
                }.count().toString()

                val mediaItems = Media.find { MediaTable.postId eq feed.post.id }.map { MediaItem(
                    mediaType = it.mediaType.name,
                    mimeType = it.mimeType,
                    fileName = it.mediaUrl,
                    thumbnailUrl = it.mediaUrl,
                    order = 1
                ) }
                FeedResponseItem(
                    postId = feed.post.id.value.toString(),
                    media = mediaItems,
                    createdAt = feed.createdAt.toString(),
                    userName = feed.post.user.username,
                    postLocation = feed.post.location ?: "",
                    postDescription = feed.post.postDescription ?: "",
                    isUserVerified = feed.user.isVerified,
                    likesCount = likesCount,
                    tags = feed.post.tags ?: "",
                    caption = feed.post.caption ?: ""
                )
            }
        }


        call.respond(
            status = HttpStatusCode.OK, message = ApiResponse(
                code = HttpStatusCode.OK.value,
                success = true,
                "Feeds",
                data = FeedResponse(feedResponseItems, nextCursor)
            )
        )
    }
}

fun getPaginatedFeeds(cursor: LocalDateTime?, limit: Int, userId: String? = null): List<Feed> {
    return transaction {
        val query = Feed.find {
            var condition: Op<Boolean> = Op.TRUE

            // Apply cursor-based pagination
            if (cursor != null) {
                condition = condition and (FeedsTable.createdAt less cursor)
            }

            // Filter by userId if provided
            if (userId != null) {
                condition = condition and (FeedsTable.userId eq UUID.fromString(userId))
            }
            condition
        }
            .orderBy(FeedsTable.createdAt to SortOrder.DESC)
            .limit(limit)

        query.toList()
    }
}

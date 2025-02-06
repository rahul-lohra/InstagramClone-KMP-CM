package com.rahullohra.instagram.post

import com.rahullohra.instagram.TimeUtil
import com.rahullohra.instagram.feed.Feed
import com.rahullohra.instagram.getUserId
import com.rahullohra.instagram.models.ApiResponse
import com.rahullohra.instagram.user.User
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import java.util.UUID


fun Routing.postRoutes() {

    post("/post/") {
        try {
            val request = call.receive<CreatePostRequest>()
            val userId = call.getUserId()

            val postId = createPostInDatabase(request, userId)
            val user = User.findById(UUID.fromString(userId))!!

            val post = Post.new {
                this.user = user
                this.caption = request.caption
                this.createdAt = TimeUtil.getCurrentTime()
                this.visibility = Visibility.PUBLIC
                this.isActive = true
            }
            val feed = Feed.new {
                this.post = post
                this.user = user
                this.rankingScore = 0.0f
                this.createdAt = TimeUtil.getCurrentTime()
            }

            // Respond with success
            call.respond(
                HttpStatusCode.Created,
                CreatePostResponse(
                    postId = postId,
                    message = "Post created successfully!",
                )
            )
        } catch (ex: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to ex.message)
            )
        }
    }

    get("/post") {
        call.respond(
            status = HttpStatusCode.OK,
            message = ApiResponse(HttpStatusCode.OK.value, true, "Hello instagram post", Unit)
        )
    }
}
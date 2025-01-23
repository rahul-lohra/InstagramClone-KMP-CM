package com.rahullohra.instagram.post

import com.rahullohra.instagram.ErrorResponse
import com.rahullohra.instagram.IgDatabase
import com.rahullohra.instagram.SuccessResponse
import com.rahullohra.instagram.auth.AuthUtils
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post


fun Routing.postRoutes() {

    post("/post/") {
        try {
            val request = call.receive<CreatePostRequest>()

            val customHeader = call.request.headers["Custom-Header"]
            val authHeader = call.request.headers["Authorization"]
            val token = authHeader?.removePrefix("Bearer ")

            if (token.isNullOrEmpty())
                throw Exception("No token sent")

            val userId = AuthUtils.getUserIdFromToken(token)
            //First upload media and then upload post

            // Simulate creating a post in the database
            val postId = createPostInDatabase(request, userId)


            // Respond with success
            call.respond(
                HttpStatusCode.Created,
                CreatePostResponse(
                    postId = postId,
                    message = "Post created successfully!"
                )
            )
            call.respond(status = HttpStatusCode.OK, "feeds")
        } catch (ex: Exception) {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf("error" to ex.message)
            )
        }

    }
//
    get("/post") {
        call.respond(
            status = HttpStatusCode.OK, message = SuccessResponse(
                "Hello instagram post"
            )
        )
    }
}
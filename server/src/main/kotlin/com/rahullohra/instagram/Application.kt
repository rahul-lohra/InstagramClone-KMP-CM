package com.rahullohra.instagram

import com.rahullohra.instagram.auth.AuthUtils
import com.rahullohra.instagram.auth.authRoutes
import com.rahullohra.instagram.feed.feedRoutes
import com.rahullohra.instagram.media.mediaRoutes
import com.rahullohra.instagram.post.postRoutes
import com.rahullohra.instagram.sampledata.MediaDownloader
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.runBlocking

fun main() {

    IgDatabase.setup()
//    val v = getFeedsForUser(IgDatabase.database, "fe7c1599-fe1c-47c5-a4cd-381fe5700f51")
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

        feedRoutes()
        mediaRoutes()
        postRoutes()
        authRoutes()

    }
}

fun ApplicationCall.getUserId(): String {
    val authHeader = request.headers["Authorization"]
    val token = authHeader?.removePrefix("Bearer ")

    if (token.isNullOrEmpty())
        throw Exception("No token sent")
    val userId = AuthUtils.getUserIdFromToken(token)
    return userId
}

package com.rahullohra.instagram

import com.rahullohra.instagram.auth.AuthUtils
import com.rahullohra.instagram.auth.authRoutes
import com.rahullohra.instagram.feed.feedRoutes
import com.rahullohra.instagram.media.mediaRoutes
import com.rahullohra.instagram.post.postRoutes
import io.ktor.network.tls.certificates.buildKeyStore
import io.ktor.network.tls.certificates.saveToFile
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.applicationEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.sslConnector
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.security.KeyStore


fun main() {

    IgDatabase.setup()
//    val v = getFeedsForUser(IgDatabase.database, "fe7c1599-fe1c-47c5-a4cd-381fe5700f51")
    embeddedServer(
        Netty,
        applicationEnvironment { log = LoggerFactory.getLogger("ktor.application") },
        {
            envConfig()
        },
        module = Application::module
    ).start(wait = true)


}

private fun ApplicationEngine.Configuration.envConfig(createNew: Boolean = false) {

    val keyStore: KeyStore
    val keyStoreFile = File("ssl/ssl_keystore.jks")
    if (!keyStoreFile.exists()) {
        keyStore = buildKeyStore {
            certificate("sampleAlias") {
                password = "foobar"
                domains = listOf("127.0.0.1", "0.0.0.0", "localhost")
            }
        }
        keyStore.saveToFile(keyStoreFile, "123456")
    } else {
        keyStore = KeyStore.getInstance("JKS").apply {
            FileInputStream(keyStoreFile).use { load(it, "123456".toCharArray()) }
        }
//        keyStore = buildKeyStore {
//            certificate("sampleAlias") {
//                password = "foobar"
//                domains = listOf("127.0.0.1", "0.0.0.0", "localhost")
//            }
//        }
    }


    connector {
        port = SERVER_PORT
    }
    sslConnector(
        keyStore = keyStore,
        keyAlias = "sampleAlias",
        keyStorePassword = { "123456".toCharArray() },
        privateKeyPassword = { "foobar".toCharArray() }) {
        port = 8443
        keyStorePath = keyStoreFile
    }
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


private fun getResource(fileName: String) = object {}.javaClass.getResource("/$fileName")
    ?: throw IllegalArgumentException("Resource not found: /$fileName")

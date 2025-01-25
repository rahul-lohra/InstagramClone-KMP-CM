package com.rahullohra.instagram.sampledata

import com.rahullohra.instagram.media.Media
import com.rahullohra.instagram.media.MediaType
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.transactions.transaction

class MediaDownloader {

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    suspend fun fetchImageLinks(totalImages: Int = 100): List<String> {
        val imageLinks = mutableListOf<String>()
        val perPage = 100  // Number of images per page (maximum allowed by the API)
        var page = 1

        while (imageLinks.size < totalImages) {
            val response: List<LoremIpsumImage> = client.get("https://picsum.photos/v2/list") {
                parameter("page", page)
                parameter("limit", perPage)
            }.body()

            response.forEach { image ->
                imageLinks.add(image.download_url)
                if (imageLinks.size >= totalImages) return@forEach
            }
            page++
        }

        return imageLinks
    }

    suspend fun downloadImages() {
        val imageLinks = fetchImageLinks(100)
        imageLinks.forEach { link ->
            transaction {
                Media.new {
                    this.fileName = link
                    this.mimeType = "image/jpeg"
                    this.mediaType = MediaType.IMAGE
                    this.mediaUrl = link
                }
            }
            println(link)
        }
        client.close()
    }

}

@Serializable
data class LoremIpsumImage(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val download_url: String
)

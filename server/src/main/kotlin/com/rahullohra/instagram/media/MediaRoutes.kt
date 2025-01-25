package com.rahullohra.instagram.media

import com.rahullohra.instagram.ErrorResponse
import com.rahullohra.instagram.MAX_VARCHAR_LENGTH
import com.rahullohra.instagram.SuccessResponse
import com.rahullohra.instagram.SuccessResponseObject
import com.rahullohra.instagram.comment.Comment
import com.rahullohra.instagram.getUserId
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.server.request.receiveMultipart
import io.ktor.server.response.respond
import io.ktor.server.response.respondFile
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import org.jetbrains.exposed.sql.insert
import java.io.File
import java.util.UUID

const val parentDirPath = "/Users/rahullohra/IdeaProjects/InstagramClone/uploads"

fun Routing.mediaRoutes() {
    // Upload Route
    post("/upload") {
        try {
            val userId = call.getUserId()
            val multipartData = call.receiveMultipart()

            val dir = File(parentDirPath)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val uploadedMediaList = mutableListOf<Media>()
            multipartData.forEachPart { part ->
                if (part is PartData.FileItem) {
                    val fileName = UUID.randomUUID().toString() + "." + part.originalFileName
                    val fileBytes = part.streamProvider().readBytes()
                    val file = File("$parentDirPath/$fileName")
                    file.writeBytes(fileBytes)
                    println("")
                    val uploadedMedia = Media.new {
                        this.mimeType = part.contentType.toString()
                        this.fileName = fileName
                    }
                    uploadedMediaList.add(uploadedMedia)
                    call.respond(
                        status = HttpStatusCode.OK, message = SuccessResponseObject(
                            "File uploaded successfully: /uploads/$fileName",
                            uploadedMediaList
                        )
                    )
                }
                part.dispose()
            }
        }catch (ex: Exception){
            println("")
            call.respond(
                status = HttpStatusCode.BadRequest, message = ErrorResponse(HttpStatusCode.BadRequest.value,
                    "File uploaded failed because ${ex.message}"
                )
            )
        }

    }

    // Download Route
    get("/download/{fileName}") {
        val fileName = call.parameters["fileName"]
        if (fileName == null) {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ErrorResponse(
                    status = HttpStatusCode.BadRequest.value,
                    message = "File name not provided"
                )
            )
            return@get
        }

        val file = File("$parentDirPath/$fileName")
        if (!file.exists()) {
            call.respond(
                status = HttpStatusCode.NotFound,
                message = ErrorResponse(
                    status = HttpStatusCode.NotFound.value,
                    message = "File not found"
                )
            )
            return@get
        }

        call.response.headers.append(HttpHeaders.ContentDisposition, "attachment; filename=\"$fileName\"")
        call.respondFile(file)
    }
}
package com.rahullohra.instagram.media

import com.rahullohra.instagram.getUserId
import com.rahullohra.instagram.models.ApiResponse
import com.rahullohra.instagram.models.SuccessResponseObject
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
                        status = HttpStatusCode.OK, ApiResponse(
                            code = HttpStatusCode.OK.value, true, "", SuccessResponseObject(
                                "File uploaded successfully: /uploads/$fileName",
                                uploadedMediaList
                            )
                        )
                    )
                }
                part.dispose()
            }
        } catch (ex: Exception) {
            println("")
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ApiResponse(
                    code = HttpStatusCode.BadRequest.value,
                    false,
                    "File uploaded failed because ${ex.message}",
                    Unit
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
                message = ApiResponse(
                    code = HttpStatusCode.BadRequest.value,
                    success = false,
                    "File name not provided",
                    Unit
                )
            )
            return@get
        }

        val file = File("$parentDirPath/$fileName")
        if (!file.exists()) {
            call.respond(
                status = HttpStatusCode.NotFound,
                message = ApiResponse(
                    code = HttpStatusCode.NotFound.value,
                    false,
                    message = "File not found",
                    Unit
                )
            )
            return@get
        }

        call.response.headers.append(
            HttpHeaders.ContentDisposition,
            "attachment; filename=\"$fileName\""
        )
        call.respondFile(file)
    }
}
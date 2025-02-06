package com.rahullohra.instagram.network

import com.rahullohra.instagram.auth.data.remote.createAuthApiService
import com.rahullohra.instagram.getPlatform
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


//const val BASE_URL = "https://0.0.0.0:8443/"
const val BASE_URL = "https://10.0.2.2:8443/"
//const val BASE_URL = "https://10.0.2.2:8080/"

private val ktorfit1 by lazy {

    if(getPlatform().name.startsWith("Android")){
        Ktorfit.Builder()
            .httpClient {
                SslSettings.configureHttpClientConfig(this)
                install(ContentNegotiation) {
                    json(Json { isLenient = true; ignoreUnknownKeys = true })
                }
                install(DefaultRequest) {
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
            }
            .baseUrl(BASE_URL)
            .build()
    }else {
        Ktorfit.Builder()
            .httpClient {
                SslSettings.configureHttpClientConfig(this)
                install(ContentNegotiation) {
                    json(Json { isLenient = true; ignoreUnknownKeys = true })
                }
                install(DefaultRequest) {
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                }
            }
            .baseUrl(BASE_URL)
            .build()
    }
}
val ktorHttpClient = SslSettings.createHttpClient()

val ktorfit = Ktorfit.Builder()
    .baseUrl(BASE_URL)
    .httpClient(ktorHttpClient)
    .build()

val authApiService = ktorfit.createAuthApiService()
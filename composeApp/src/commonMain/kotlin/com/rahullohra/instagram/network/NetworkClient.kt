package com.rahullohra.instagram.network

import com.rahullohra.instagram.auth.data.remote.createAuthApiService
import com.rahullohra.instagram.feed.data.remote.createFeedApiService
import de.jensklingenberg.ktorfit.Ktorfit


//const val BASE_URL = "https://0.0.0.0:8443/"
const val BASE_URL = "https://10.0.2.2:8443/"
//const val BASE_URL = "https://10.0.2.2:8080/"

val ktorHttpClient = SslSettings.createHttpClient()

val ktorfit = Ktorfit.Builder()
    .baseUrl(BASE_URL)
    .httpClient(ktorHttpClient)
    .build()

val authApiService = ktorfit.createAuthApiService()
val feedApiService = ktorfit.createFeedApiService()

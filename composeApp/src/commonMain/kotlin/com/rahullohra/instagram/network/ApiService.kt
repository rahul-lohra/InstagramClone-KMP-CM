package com.rahullohra.instagram.network

import de.jensklingenberg.ktorfit.http.GET

interface ApiService {

    @GET("people/1/")
    suspend fun getPerson(): String

}
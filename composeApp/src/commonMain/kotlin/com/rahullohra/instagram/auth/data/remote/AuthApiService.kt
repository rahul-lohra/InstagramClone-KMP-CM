package com.rahullohra.instagram.auth.data.remote

import com.rahullohra.instagram.auth.data.SignUpResponse
import com.rahullohra.instagram.models.ApiResponse
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.Field
import de.jensklingenberg.ktorfit.http.FormUrlEncoded
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.POST
import kotlinx.serialization.Serializable

interface AuthApiService {

    @POST("/auth/register")
    @FormUrlEncoded
    suspend fun signup(
        @Field("username")
        username: String,
        @Field("password")
        password: String): ApiResponse<SignUpResponse>


    @POST("/auth/login")
    @Headers("Accept: application/json","Content-Type: application/json")
    suspend fun login(@Body loginRequest: LoginRequest): ApiResponse<SignUpResponse>

    @FormUrlEncoded
    @POST("/auth/refresh_token")
    suspend fun refreshToken(
        @Field("refreshToken")
        refreshToken: String): ApiResponse<SignUpResponse>
}


@Serializable
data class LoginRequest(val username: String, val password: String)
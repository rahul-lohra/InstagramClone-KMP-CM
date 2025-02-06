package com.rahullohra.instagram.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(val userId: String, val accessToken: String, val refreshToken: String)

@Serializable
data class RefreshTokenRequest(val refreshToken: String)

@Serializable
data class RefreshTokenResponse(val accessToken: String, val refreshToken: String)

@Serializable
data class RegisterRequest(val username: String, val password: String)

@Serializable
data class LoginRequest(val username: String, val password: String)

@Serializable
data class LoginResponse(
    val username: String,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiry: Long,  // Expiry timestamp in UNIX time
    val refreshTokenExpiry: Long  // Expiry timestamp in UNIX time
)
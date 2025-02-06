package com.rahullohra.instagram.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(val userId: String, val accessToken: String, val refreshToken: String)
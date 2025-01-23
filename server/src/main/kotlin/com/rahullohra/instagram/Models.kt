package com.rahullohra.instagram


import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val status: Int,
    val message: String
)

@Serializable
data class SuccessResponse(
    val message: String
)

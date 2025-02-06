package com.rahul.serevermodels


import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val status: Int,
    val message: String
)

@Serializable
class SuccessResponseObject<T>(
    val message: String,
    val data: T? = null
) : SuccessResponse

@Serializable
class SuccessResponseMessage(val message: String) : SuccessResponse

interface SuccessResponse

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)

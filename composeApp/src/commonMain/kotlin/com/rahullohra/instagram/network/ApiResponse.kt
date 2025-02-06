package com.rahullohra.instagram.network

import kotlinx.serialization.Serializable

@Serializable
sealed class DataLayerResponse<T> {
    class Success<T>(val data: T): DataLayerResponse<T>()
    class Error<T>(val message: String): DataLayerResponse<T>()
}

//@Serializable
//data class ServerApiResponse<T>(
//    val success: Boolean,
//    val message: String,
//    val data: T? = null
//)
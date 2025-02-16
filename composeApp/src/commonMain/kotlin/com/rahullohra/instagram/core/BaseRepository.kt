package com.rahullohra.instagram.core

import com.rahullohra.instagram.models.ApiResponse
import com.rahullohra.instagram.network.DataLayerResponse

open class BaseRepository {
    public suspend fun <T> safeApiCall(apiCall: suspend () -> ApiResponse<T>): DataLayerResponse<T> {
        return try {
            val response = apiCall()
            if(response.data!=null){
                DataLayerResponse.Success(response.data!!) // Wrap successful response
            }else {
                DataLayerResponse.Error<T>(response.message)
            }
        } catch (e: Exception) {
            DataLayerResponse.Error(e.message ?: "Unknown error") // Handle errors
        }
    }
}
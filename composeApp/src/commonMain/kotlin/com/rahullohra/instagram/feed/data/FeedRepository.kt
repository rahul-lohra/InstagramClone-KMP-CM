package com.rahullohra.instagram.feed.data

import com.rahullohra.instagram.core.BaseRepository
import com.rahullohra.instagram.feed.FeedResponse
import com.rahullohra.instagram.feed.data.remote.FeedApiService
import com.rahullohra.instagram.network.DataLayerResponse

class FeedRepository(val feedApiService: FeedApiService) : BaseRepository() {

    suspend fun getFeeds(
        cursor: String,
        userId: String,
        limit: Int
    ): DataLayerResponse<FeedResponse> {
        try {

            val response = safeApiCall {
                feedApiService.getFeeds(cursor, userId, limit)
            }
            return when (response) {
                is DataLayerResponse.Success -> {
                    DataLayerResponse.Success(response.data)
                }

                is DataLayerResponse.Error -> {
                    DataLayerResponse.Error(response.message)
                }
            }

        } catch (ex: Exception) {
            return DataLayerResponse.Error(ex.message ?: "Unknown error")
        }
    }

}
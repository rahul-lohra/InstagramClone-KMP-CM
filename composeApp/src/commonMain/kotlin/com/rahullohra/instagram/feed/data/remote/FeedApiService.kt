package com.rahullohra.instagram.feed.data.remote

import com.rahullohra.instagram.feed.FeedResponse
import com.rahullohra.instagram.models.ApiResponse
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import kotlinx.serialization.Serializable


interface FeedApiService {

    @GET("/feed")
    suspend fun getFeeds(
        @Query("cursor") cursor: String, @Query("userId") userId: String,
        @Query("limit") limit: Int): ApiResponse<FeedResponse>

}
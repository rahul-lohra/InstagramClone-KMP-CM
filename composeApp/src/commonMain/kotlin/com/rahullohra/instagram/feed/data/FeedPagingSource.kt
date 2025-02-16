package com.rahullohra.instagram.feed.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rahullohra.instagram.feed.FeedResponseItem
import com.rahullohra.instagram.network.DataLayerResponse

class FeedPagingSource(private val repository: FeedRepository) : PagingSource<Int, FeedResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeedResponseItem> {
        val cursor = params.key ?: "" // Start with an empty cursor
        return try {
            val response = repository.getFeeds(cursor.toString(), "userId", params.loadSize)


            when(response){
                is DataLayerResponse.Success->{

                    LoadResult.Page(
                        data = response.data.feedResponseItems,
                        prevKey = null,
                        nextKey = if (response.data.feedResponseItems.isEmpty()) null else {
                            response.data.nextCursor
                            1
                        }
                    )
                }
                is DataLayerResponse.Error->{
                    LoadResult.Page(
                        data = emptyList(),
                        prevKey = null,
                        nextKey = 1 //TODO Rahul use next cursor properly
                    )
                }
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FeedResponseItem>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey?.plus(1) }
    }
}

package com.dicoding.submission1storyapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dicoding.submission1storyapp.data.remote.api.ApiService
import com.dicoding.submission1storyapp.data.remote.response.ListUserStoriesItem

class StoryPagingSource(private val apiService: ApiService, private val userToken: String) : PagingSource<Int, ListUserStoriesItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListUserStoriesItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getAllUserStories("Bearer $userToken", page, params.loadSize)

            LoadResult.Page(
                data = responseData.listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.listStory.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ListUserStoriesItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
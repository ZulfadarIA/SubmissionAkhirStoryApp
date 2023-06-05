package com.dicoding.submission1storyapp.ui.maintest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.dicoding.submission1storyapp.data.StoryPagingSource
import com.dicoding.submission1storyapp.data.remote.response.ListUserStoriesItem
import com.dicoding.submission1storyapp.data.remote.response.UserStoryRepository
import com.dicoding.submission1storyapp.ui.adapter.StoriesAdapter
import com.dicoding.submission1storyapp.ui.main.MainActivityViewModel
import com.dicoding.submission1storyapp.utils.Dummy
import com.dicoding.submission1storyapp.utils.MainDispatcherRules
import com.dicoding.submission1storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRules()

    @Mock
    private lateinit var userStoryRepository: UserStoryRepository

    @Test
    fun `when Get Stories Should Not Null and Return Data's`() = runTest {
        val storiesDummy = Dummy.generateDataDummyStories()
        val datas: PagingData<ListUserStoriesItem> = com.dicoding.submission1storyapp.ui.maintest.StoryPagingSource.snapShot(storiesDummy)
        val expectedQuote = MutableLiveData<PagingData<ListUserStoriesItem>>()
        expectedQuote.value = datas
        Mockito.`when`(userStoryRepository.getAllUserStories()).thenReturn(expectedQuote)

        val mainActivityViewModel = MainActivityViewModel(userStoryRepository)
        val actualQuote: PagingData<ListUserStoriesItem> = mainActivityViewModel.userStoires.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.CALLBACK_DIFF,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actualQuote)
        assertNotNull(differ.snapshot())
        assertEquals(storiesDummy.size, differ.snapshot().size)
        assertEquals(storiesDummy[0], differ.snapshot()[0])
    }

    @Test
    fun `when Get Quote Empty Should Return No Datas` () = runTest {
        val datas: PagingData<ListUserStoriesItem> = PagingData.from(emptyList())
        val quoteExpected = MutableLiveData<PagingData<ListUserStoriesItem>>()
        quoteExpected.value = datas
        Mockito.`when`(userStoryRepository.getAllUserStories()).thenReturn(quoteExpected)
        val mainActivityViewModel = MainActivityViewModel(userStoryRepository)
        val actualQuote: PagingData<ListUserStoriesItem> = mainActivityViewModel.userStoires.getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.CALLBACK_DIFF,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actualQuote)
        assertEquals(0, differ.snapshot().size)
    }
}

class StoryPagingSource: PagingSource<Int, LiveData<List<ListUserStoriesItem>>>() {
    companion object {
        fun snapShot(item: List<ListUserStoriesItem>): PagingData<ListUserStoriesItem> {
            return PagingData.from(item)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<ListUserStoriesItem>>>): Int? {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<ListUserStoriesItem>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onChanged(position: Int, count: Int, payload: Any?) {

    }

    override fun onInserted(position: Int, count: Int) {

    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {

    }

    override fun onRemoved(position: Int, count: Int) {

    }
}
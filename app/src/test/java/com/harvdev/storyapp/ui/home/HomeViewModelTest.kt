package com.harvdev.storyapp.ui.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.harvdev.storyapp.DataDummy
import com.harvdev.storyapp.MainDispatcherRule
import com.harvdev.storyapp.adapter.StoriesAdapter
import com.harvdev.storyapp.data.HomeRepository
import com.harvdev.storyapp.getOrAwaitValue
import com.harvdev.storyapp.model.Story
import com.harvdev.storyapp.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var homeRepository: HomeRepository
    private lateinit var homeViewModel: HomeViewModel
    private var dummyUser = UserModel()
    private lateinit var context: Context
    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(homeRepository)
        context = Mockito.mock(Context::class.java)
    }

    @Test
    fun `when Get Story Should Not Null and Return Success`() = runTest {
        val dummyStory = DataDummy.generateDummyStoryEntity()
        val data: PagingData<Story> = StoryPagingSource.snapshot(dummyStory)
        val expectedQuote = MutableLiveData<PagingData<Story>>()
        expectedQuote.value = data
        Mockito.`when`(homeRepository.getStory()).thenReturn(expectedQuote)

        val homeViewModel = HomeViewModel(homeRepository)
        val actualQuote: PagingData<Story> = homeViewModel.getAllStories().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory, differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStory[0].name, differ.snapshot()[0]?.name)

    }

    @Test
    fun `get Profile test`(){
        dummyUser = homeRepository.getProfile()
        Assert.assertNull(dummyUser)
    }

    @Test
    fun `logout test`(){
        homeViewModel.logout { isError, message ->
            Assert.assertFalse(isError == false)
            Assert.assertNotNull(message)
        }
    }
}

class StoryPagingSource : PagingSource<Int, LiveData<List<Story>>>() {
    companion object {
        fun snapshot(items: List<Story>): PagingData<Story> {
            return PagingData.from(items)
        }
    }
    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>): Int {
        return 0
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}
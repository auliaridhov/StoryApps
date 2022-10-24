package com.harvdev.storyapp.data

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import com.harvdev.storyapp.DataDummy
import com.harvdev.storyapp.MainDispatcherRule
import com.harvdev.storyapp.adapter.StoriesAdapter
import com.harvdev.storyapp.getOrAwaitValue
import com.harvdev.storyapp.model.Story
import com.harvdev.storyapp.model.UserModel
import com.harvdev.storyapp.ui.home.HomeViewModel
import com.harvdev.storyapp.ui.home.StoryPagingSource
import kotlinx.coroutines.Dispatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var homeRepository: HomeRepository
    private var dummyUser = UserModel()
    private lateinit var context: Context
    @Before
    fun setUp() {
        context = Mockito.mock(Context::class.java)
    }

    @Test
    fun `when Get Story Should Not Null and Return Success`() = kotlinx.coroutines.test.runTest {
        val dummyStory = DataDummy.generateDummyStoryEntity()
        val data: PagingData<Story> = StoryPagingSource.snapshot(dummyStory)
        val expectedQuote = MutableLiveData<PagingData<Story>>()
        expectedQuote.value = data
        Mockito.`when`(homeRepository.getStory()).thenReturn(expectedQuote)

        val homeViewModel = HomeViewModel(homeRepository)
        val actualQuote: PagingData<Story> = homeViewModel.getAllStories().getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = com.harvdev.storyapp.ui.home.noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualQuote)

        Assert.assertNotNull(differ.snapshot())
        Assert.assertEquals(dummyStory, differ.snapshot())
        Assert.assertEquals(dummyStory.size, differ.snapshot().size)
        Assert.assertEquals(dummyStory[0].name, differ.snapshot()[0]?.name)

    }

    @Test
    fun `get Profile Not Login test`(){
        homeRepository.getProfile {
            Assert.assertNull(dummyUser)
        }
    }

    @Test
    fun `logout test`(){
        homeRepository.logout { isError, message ->
            Assert.assertFalse(isError == false)
            Assert.assertNotNull(message)
        }
    }
}
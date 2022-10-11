package com.harvdev.storyapp.ui.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.harvdev.storyapp.DataDummy
import com.harvdev.storyapp.data.MapsRepository
import com.harvdev.storyapp.model.Story
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import androidx.lifecycle.Observer

@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @Mock
    private lateinit var mapsRepository: MapsRepository
    private lateinit var mapsViewModel: MapsViewModel
    private val dummyStory = DataDummy.generateDummyStoryEntity()

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(mapsRepository)
    }

    @Test
    fun `when Get Story Should Not Null and Return Success`() {
        val observer = Observer<List<Story>> {}
        try {
            val expectedNews = MutableLiveData<List<Story>>()
            expectedNews.value = dummyStory
            `when`(mapsRepository.getStories()).thenReturn(expectedNews)

            val actualNews = mapsViewModel.getStories().observeForever(observer)

            Mockito.verify(mapsRepository).getStories()
            Assert.assertNotNull(actualNews)
        } finally {
            mapsViewModel.getStories().removeObserver(observer)
        }
    }
}
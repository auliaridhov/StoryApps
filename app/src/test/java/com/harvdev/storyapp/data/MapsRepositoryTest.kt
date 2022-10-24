package com.harvdev.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.harvdev.storyapp.DataDummy
import com.harvdev.storyapp.model.Story
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MapsRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mapsRepository: MapsRepository
    private val dummyStory = DataDummy.generateDummyStoryEntity()

    @Before
    fun setUp() {

    }

    @Test
    fun `when Get Story Should Not Null and Return Success`() {
        val observer = Observer<List<Story>> {}
        try {
            val expectedNews = MutableLiveData<List<Story>>()
            expectedNews.value = dummyStory
            Mockito.`when`(mapsRepository.getStories()).thenReturn(expectedNews)

            val actualNews = mapsRepository.getStories().observeForever(observer)

            Mockito.verify(mapsRepository).getStories()
            Assert.assertNotNull(actualNews)
        } finally {
            mapsRepository.getStories().removeObserver(observer)
        }
    }
}
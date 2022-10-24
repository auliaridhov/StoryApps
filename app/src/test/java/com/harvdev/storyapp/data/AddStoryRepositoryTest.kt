package com.harvdev.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var addStoryRepository: AddStoryRepository


    private val dummyContent = "testtt"
    private var getFile: File? = null

    @Before
    fun setUp() {

    }

    @Test
    fun `Add Story Test`() {
        addStoryRepository.uploadImage(getFile, dummyContent) { isError, message ->
            Assert.assertTrue(isError == true)
            Assert.assertNotNull(message)
        }

    }
}
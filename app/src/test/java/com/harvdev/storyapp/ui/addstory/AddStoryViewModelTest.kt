package com.harvdev.storyapp.ui.addstory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harvdev.storyapp.DataDummy
import com.harvdev.storyapp.data.AddStoryRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.File


@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var addStoryRepository: AddStoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(addStoryRepository)

    }
    @Test
    fun `Add Story Failed Test`() {
        addStoryViewModel.uploadImage(DataDummy.generateDummyMultipartFile(), DataDummy.generateDummyRequestBody()) { isError, message ->
            Assert.assertTrue(isError == true)
            Assert.assertNotNull(message)
        }

    }

}
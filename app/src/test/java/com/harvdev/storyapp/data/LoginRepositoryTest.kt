package com.harvdev.storyapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginRepository: LoginRepository

    private val dummyName = "Yusuf"
    private val dummyEmail = "yeah@mail.com"
    private val dummyPassword = "yeaahh"

    @Before
    fun setUp() {

    }

    @Test
    fun `Register successfully`() {
        loginRepository.register(dummyName, dummyEmail, dummyPassword) { isError, message, ->
            Assert.assertFalse(isError == false)
            Assert.assertNotNull(message)
        }
    }

    @Test
    fun `Login successfully`() {
        loginRepository.login(dummyEmail, dummyPassword) { isError, message, ->
            Assert.assertFalse(isError == false)
            Assert.assertNotNull(message)
        }
    }

    @Test
    fun `Check Is Login when not login`() {
        Assert.assertFalse(loginRepository.isLogin())
    }
}
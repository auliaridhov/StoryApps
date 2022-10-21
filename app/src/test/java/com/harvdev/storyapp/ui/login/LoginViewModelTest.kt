package com.harvdev.storyapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.harvdev.storyapp.DataDummy
import kotlinx.coroutines.flow.flow
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.harvdev.storyapp.data.LoginRepository

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var loginRepository: LoginRepository
    private lateinit var loginViewModel: LoginViewModel

    private val dummyName = "Yusuf"
    private val dummyEmail = "yeah@mail.com"
    private val dummyPassword = "yeaahh"

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(loginRepository)

    }

    @Test
    fun `Register successfully`() {
        loginViewModel.register(dummyName, dummyEmail, dummyPassword) { isError, message, ->

            Assert.assertFalse(isError == false)
            Assert.assertNotNull(message)
        }

    }

    @Test
    fun `Login successfully`() {
        loginViewModel.login(dummyEmail, dummyPassword) { isError, message, ->
            Assert.assertFalse(isError == false)
            Assert.assertNotNull(message)
        }

    }

    @Test
    fun `Check Is Login`() {
        Assert.assertFalse(loginViewModel.isLogin())
    }

}
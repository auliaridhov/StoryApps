package com.harvdev.storyapp.ui.login

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.harvdev.storyapp.data.LoginRepository
import com.harvdev.storyapp.data.UserPreference
import com.harvdev.storyapp.di.Injection
import com.harvdev.storyapp.model.ResponseLogin
import com.harvdev.storyapp.model.ResponseRegister
import com.harvdev.storyapp.model.UserModel
import com.harvdev.storyapp.ui.home.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class LoginViewModel (private val loginRepository: LoginRepository) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String, callback: (error: Boolean?, message: String?) -> Unit){
        _isLoading.value = true
        loginRepository.login(email, password){ isError, message ->
            callback(isError, message)
            _isLoading.value = false
        }
    }

    fun register(name: String, email: String, password: String, callback: (error: Boolean?, message: String?) -> Unit){
        _isLoading.value = true
        loginRepository.register(name, email, password){ isError, message ->
            callback(isError, message)
            _isLoading.value = false
        }
    }


    fun isLogin() : Boolean {
        return loginRepository.isLogin()
    }
}
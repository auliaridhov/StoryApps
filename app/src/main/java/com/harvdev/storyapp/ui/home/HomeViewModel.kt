package com.harvdev.storyapp.ui.home

import android.content.Context
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.harvdev.storyapp.data.HomeRepository
import com.harvdev.storyapp.di.Injection
import com.harvdev.storyapp.model.Story
import com.harvdev.storyapp.model.UserModel
import com.harvdev.storyapp.ui.login.LoginViewModel

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _profile = MutableLiveData<UserModel>().apply {
        value = UserModel()
    }
    val profile: LiveData<UserModel> = _profile

    fun getAllStories(): LiveData<PagingData<Story>> =
        homeRepository.getStory()

    fun getProfile() {
        _profile.value = homeRepository.getProfile()
    }

    fun logout() {
        homeRepository.logout()
    }
}


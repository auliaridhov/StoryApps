package com.harvdev.storyapp.ui.home

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.harvdev.storyapp.data.HomeRepository
import com.harvdev.storyapp.data.UserPreference
import com.harvdev.storyapp.di.Injection
import com.harvdev.storyapp.model.ResponseStories
import com.harvdev.storyapp.model.Story
import com.harvdev.storyapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _profile = MutableLiveData<UserModel>().apply {
        value = UserModel()
    }
    val profile: LiveData<UserModel> = _profile

    val listStories: LiveData<PagingData<Story>> =
        homeRepository.getStory().cachedIn(viewModelScope)

    fun getAllStories(): LiveData<PagingData<Story>> =
        homeRepository.getStory().cachedIn(viewModelScope)

    fun getProfile() {
        _profile.value = homeRepository.getProfile()
    }

    fun logout() {
        homeRepository.logout()
    }
}

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
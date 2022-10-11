package com.harvdev.storyapp.ui.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.harvdev.storyapp.di.Injection
import com.harvdev.storyapp.ui.home.HomeViewModel
import com.harvdev.storyapp.ui.login.LoginViewModel
import com.harvdev.storyapp.ui.maps.MapsViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(Injection.provideRepository(context)) as T
        }
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(Injection.provideLoginRepository(context)) as T
        }
        if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MapsViewModel(Injection.provideMapsRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
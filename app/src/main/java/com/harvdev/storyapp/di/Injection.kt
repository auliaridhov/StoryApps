package com.harvdev.storyapp.di

import android.content.Context
import com.harvdev.storyapp.data.HomeRepository
import com.harvdev.storyapp.data.LoginRepository
import com.harvdev.storyapp.data.MapsRepository
import com.harvdev.storyapp.database.StoryDatabase

object Injection {
    fun provideRepository(context: Context): HomeRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return HomeRepository(database, apiService, context)
    }

    fun provideLoginRepository(context: Context): LoginRepository {
        val apiService = ApiConfig.getApiService()
        return LoginRepository(apiService, context)
    }

    fun provideMapsRepository(context: Context): MapsRepository {
        return MapsRepository(context)
    }
}
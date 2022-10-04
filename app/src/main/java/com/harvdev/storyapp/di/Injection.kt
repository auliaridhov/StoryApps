package com.harvdev.storyapp.di

import android.content.Context
import com.harvdev.storyapp.data.HomeRepository
import com.harvdev.storyapp.database.StoryDatabase

object Injection {
    fun provideRepository(context: Context): HomeRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return HomeRepository(database, apiService, context)
    }
}
package com.harvdev.storyapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.harvdev.storyapp.database.StoryDatabase
import com.harvdev.storyapp.model.Story
import com.harvdev.storyapp.model.UserModel
import com.harvdev.storyapp.network.ApiService

class HomeRepository(private val storyDatabase: StoryDatabase, private val apiService: ApiService, private val context: Context) {

    private val token = "Bearer ${UserPreference(context).getUser().token}"

    fun getStory(): LiveData<PagingData<Story>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, token),
            pagingSourceFactory = {
//                StoryPagingSource(token, apiService)
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    fun getProfile(): UserModel {
        return UserPreference(context).getUser()
    }

    fun logout() {
        val userPreference = UserPreference(context)
        userPreference.removeUser()
    }
}
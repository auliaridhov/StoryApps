package com.harvdev.storyapp

import com.harvdev.storyapp.model.LoginResult
import com.harvdev.storyapp.model.ResponseLogin
import com.harvdev.storyapp.model.Story
import com.harvdev.storyapp.model.UserModel
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

object DataDummy {
    fun generateDummyStoryEntity(): List<Story> {
        val newsList = ArrayList<Story>()
        for (i in 0..10) {
            val news = Story(
                "story-NulgY-EKAbG9Fi7Z",
                "pikachu",
                "awwww",
                "https://story-api.dicoding.dev/images/stories/photos-1664975420298_dvOgzVcG.jpg",
                "2022-10-05T13:10:20.299Z",
                null,
                null
            )
            newsList.add(news)
        }
        return newsList
    }

    fun generateDummyLoginResponse(): ResponseLogin {
        return ResponseLogin(
            false,
            "success",
            LoginResult(
                "user-z4H1i9K1JHUDffl_",
                "dadang",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXo0SDFpOUsxSkhVRGZmbF8iLCJpYXQiOjE2NjQ5NzcyNzB9.qJvEARVz2k4qTbI5YbgpVMBoz_RNbiNOGbMzlXX3EPo"
            )
        )
    }

    fun generateDummyUser(): UserModel {
        return UserModel(
            "user-z4H1i9K1JHUDffl_",
            "dadang",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXo0SDFpOUsxSkhVRGZmbF8iLCJpYXQiOjE2NjYzNjgzMjh9.VicxuSby4QgMbz9ajTgvNF6GovPHTs6bQROZqEegC2g"
        )
    }

    fun generateDummyMultipartFile(): MultipartBody.Part {
        val dummyText = "text"
        return MultipartBody.Part.create(dummyText.toRequestBody())
    }
}
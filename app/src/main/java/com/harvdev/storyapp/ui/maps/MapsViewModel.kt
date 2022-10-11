package com.harvdev.storyapp.ui.maps

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harvdev.storyapp.data.LoginRepository
import com.harvdev.storyapp.data.MapsRepository
import com.harvdev.storyapp.data.UserPreference
import com.harvdev.storyapp.model.ResponseRegister
import com.harvdev.storyapp.model.ResponseStories
import com.harvdev.storyapp.model.Story
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsViewModel(private val mapsRepository: MapsRepository) : ViewModel() {

    fun getStories() : LiveData<List<Story>> = mapsRepository.getStories()

}
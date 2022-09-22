package com.harvdev.storyapp.ui.home

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harvdev.storyapp.data.UserPreference
import com.harvdev.storyapp.model.ResponseStories
import com.harvdev.storyapp.model.Story
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listStories = MutableLiveData<List<Story>>().apply {
        value = emptyList()
    }
    val listStories: LiveData<List<Story>> = _listStories

    fun getStories(){
        Log.d("storyy-called", "callleddd")
        val userPreference = UserPreference(context)
        val token = "Bearer ${userPreference.getUser().token}"
        _isLoading.value = true
        val client =
            userPreference.getUser().token?.let { ApiConfig.getApiService().getStories(token, 1, 1, 20) }
        client?.enqueue(object : Callback<ResponseStories> {
            override fun onResponse(
                call: Call<ResponseStories>,
                response: Response<ResponseStories>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listStories.value = response.body()?.listStory
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseStories>, t: Throwable) {
                _isLoading.value = false
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun logout(){
        val userPreference = UserPreference(context)
        userPreference.removeUser()
    }
}
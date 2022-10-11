package com.harvdev.storyapp.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.liveData
import com.harvdev.storyapp.model.ResponseLogin
import com.harvdev.storyapp.model.ResponseStories
import com.harvdev.storyapp.model.Story
import com.harvdev.storyapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsRepository (private val context: Context) {

    private val token = "Bearer ${UserPreference(context).getUser().token}"

    fun getStories() : MutableLiveData<List<Story>> {

        val _listStories = MutableLiveData<List<Story>>().apply {
            value = emptyList()
        }
        val listStories: MutableLiveData<List<Story>> = _listStories
        val client = token.let { ApiConfig.getApiService().getStoriesMaps(token, 1, 1, 20) }
        client.enqueue(object : Callback<ResponseStories> {
            override fun onResponse(
                call: Call<ResponseStories>,
                response: Response<ResponseStories>
            ) {
                if (response.isSuccessful) {
                    response.body()?.listStory?.let {
                        _listStories.value = it
                    }
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseStories>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })

        return listStories
    }
}
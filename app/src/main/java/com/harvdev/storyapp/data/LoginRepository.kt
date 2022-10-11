package com.harvdev.storyapp.data

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.harvdev.storyapp.database.StoryDatabase
import com.harvdev.storyapp.model.ResponseLogin
import com.harvdev.storyapp.model.ResponseRegister
import com.harvdev.storyapp.model.Story
import com.harvdev.storyapp.model.UserModel
import com.harvdev.storyapp.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository (private val apiService: ApiService, private val context: Context) {

    fun login(email: String, password: String, callback: (error: Boolean?, message: String?) -> Unit){
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(
                call: Call<ResponseLogin>,
                response: Response<ResponseLogin>
            ) {
                if (response.isSuccessful) {
                    saveUser(response.body()?.loginResult?.userId , response.body()?.loginResult?.name, response.body()?.loginResult?.token)
                    callback(response.body()?.error, response.body()?.message)
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    callback(true, response.message())
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
                callback(true, t.message.toString())
            }
        })
    }

    fun register(name: String, email: String, password: String, callback: (error: Boolean?, message: String?) -> Unit){
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                if (response.isSuccessful && response.body()?.error == false) {
                    callback(response.body()?.error, response.body()?.message)
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    callback(true, response.message())
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
                callback(true, t.message.toString())
            }
        })
    }

    private fun saveUser(userId: String?, name: String?,  token: String?) {
        val userModel = UserModel()
        val userPreference = UserPreference(context)
        userModel.userId = userId
        userModel.name = name
        userModel.token = token
        userPreference.setUser(userModel)
    }

    fun isLogin() : Boolean {
        val userPreference = UserPreference(context)
        return userPreference.getUser().token != ""
    }
}
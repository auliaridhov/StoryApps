package com.harvdev.storyapp.ui.login

import android.app.Application
import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.harvdev.storyapp.data.UserPreference
import com.harvdev.storyapp.model.ResponseLogin
import com.harvdev.storyapp.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (application: Application) : AndroidViewModel(application){

    private val context = getApplication<Application>().applicationContext

    private val _text = MutableLiveData<String>().apply {
        value = "This is login Fragment"
    }
    val text: LiveData<String> = _text

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String, callback: (error: Boolean?, message: String?) -> Unit){
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<ResponseLogin> {
            override fun onResponse(
                call: Call<ResponseLogin>,
                response: Response<ResponseLogin>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    saveUser(response.body()?.loginResult?.userId , response.body()?.loginResult?.name, response.body()?.loginResult?.token)
                    callback(response.body()?.error, response.body()?.message)
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                    callback(true, response.message())
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                _isLoading.value = false
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
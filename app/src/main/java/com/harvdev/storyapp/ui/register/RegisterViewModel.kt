package com.harvdev.storyapp.ui.register

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harvdev.storyapp.model.ResponseRegister
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>().apply {
        value = false
    }
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String, email: String, password: String, callback: (error: Boolean?, message: String?) -> Unit){
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    callback(response.body()?.error, response.body()?.message)
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                    callback(response.body()?.error, response.message())
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                callback(true, t.message.toString())
            }
        })
    }

}
package com.harvdev.storyapp.data

import android.content.Context
import com.harvdev.storyapp.model.ResponseRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryRepository(private val context: Context) {

    fun uploadImage(
        imageMultipart: MultipartBody.Part,
        desc: RequestBody,
        callback: (Boolean?, String?) -> Unit
    ) {
        val userPreference = UserPreference(context)
        val token = "Bearer ${userPreference.getUser().token}"

        val service = ApiConfig.getApiService().uploadImage(token, imageMultipart, desc)

        service.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.error == false) {
                        callback(false, responseBody.message)
                    }
                } else {
                    callback(true, response.message())
                }
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                callback(true, "Gagal instance Retrofit")
            }
        })

    }
}
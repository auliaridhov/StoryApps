package com.harvdev.storyapp.data

import android.content.Context
import com.harvdev.storyapp.model.ResponseRegister
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class AddStoryRepository (private val context: Context) {

    fun uploadImage(file: File?, desc: String, callback: (Boolean?, String?) -> Unit){

        if (file == null) {
            callback(true, "file is null")
        } else {
            val description = desc.toRequestBody("text/plain".toMediaType())

            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile
            )

            val userPreference = UserPreference(context)
            val token = "Bearer ${userPreference.getUser().token}"

            val service = ApiConfig.getApiService().uploadImage(token, imageMultipart, description)

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
}
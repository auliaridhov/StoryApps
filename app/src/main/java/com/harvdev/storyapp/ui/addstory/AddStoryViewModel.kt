package com.harvdev.storyapp.ui.addstory

import androidx.lifecycle.ViewModel
import com.harvdev.storyapp.data.AddStoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class AddStoryViewModel(private val addStoryRepository: AddStoryRepository) : ViewModel(){

    fun uploadImage(file: MultipartBody.Part, desc: RequestBody, callback: (Boolean?, String?) -> Unit){
        addStoryRepository.uploadImage(file, desc){ isError, message ->
            callback(isError, message)
        }
    }
}
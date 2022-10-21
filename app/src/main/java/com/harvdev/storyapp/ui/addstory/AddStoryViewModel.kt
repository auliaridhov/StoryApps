package com.harvdev.storyapp.ui.addstory

import androidx.lifecycle.ViewModel
import com.harvdev.storyapp.data.AddStoryRepository
import java.io.File

class AddStoryViewModel(private val addStoryRepository: AddStoryRepository) : ViewModel(){

    fun uploadImage(file: File?, desc: String, callback: (Boolean?, String?) -> Unit){

        if (file == null) callback(true, "file is null")
        else addStoryRepository.uploadImage(file, desc){ isError, message ->
            callback(isError, message)
        }
    }
}
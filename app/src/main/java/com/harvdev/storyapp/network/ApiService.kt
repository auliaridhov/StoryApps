package com.harvdev.storyapp.network

import com.harvdev.storyapp.model.ResponseLogin
import com.harvdev.storyapp.model.ResponseRegister
import com.harvdev.storyapp.model.ResponseStories
import com.harvdev.storyapp.model.Story
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseRegister>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<ResponseLogin>


    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
        @Query("location") location: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): ResponseStories

    @GET("stories")
    fun getStoriesMaps(
        @Header("Authorization") token: String,
        @Query("location") location: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): Call<ResponseStories>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<ResponseRegister>

}
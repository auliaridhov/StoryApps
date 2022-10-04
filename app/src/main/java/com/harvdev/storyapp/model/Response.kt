package com.harvdev.storyapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class ResponseRegister(
    @SerializedName("error") var error: Boolean? = false,
    @SerializedName("message") var message: String? = "",
)

data class ResponseLogin(
    @SerializedName("error") var error: Boolean? = false,
    @SerializedName("message") var message: String? = "",
    @SerializedName("loginResult") var loginResult: LoginResult
)

data class LoginResult(
    @SerializedName("userId") var userId: String? = "",
    @SerializedName("name") var name: String? = "",
    @SerializedName("token") var token: String? = "",
)

data class ResponseStories(
    @SerializedName("error") var error: Boolean? = false,
    @SerializedName("message") var message: String? = "",
    @SerializedName("listStory") var listStory: List<Story>? = emptyList()
)

data class Story(
    @SerializedName("id") var id: String? = "",
    @SerializedName("name") var name: String? = "",
    @SerializedName("description") var description: String? = "",
    @SerializedName("photoUrl") var photoUrl: String? = "",
    @SerializedName("createdAt") var createdAt: String? = "",
    @SerializedName("lat") var lat: Double? = 0.0,
    @SerializedName("lon") var lon: Double? = 0.0,
) : Serializable

@Parcelize
data class UserModel (
    var userId: String? = null,
    var name: String? = null,
    var token: String? = null,
) : Parcelable

@Parcelize
data class TourismPlace (
    var title: String,
    var latitude: Double,
    var longitude: Double,
) : Parcelable

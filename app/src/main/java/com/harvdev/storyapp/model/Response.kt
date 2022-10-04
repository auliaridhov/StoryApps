package com.harvdev.storyapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity(tableName = "story")
data class Story(
    @PrimaryKey
    @field:SerializedName("id") var id : String,

    @field:SerializedName("name") var name: String? = "",
    @field:SerializedName("description") var description: String? = "",
    @field:SerializedName("photoUrl") var photoUrl: String? = "",
    @field:SerializedName("createdAt") var createdAt: String? = "",
    @field:SerializedName("lat") var lat: Double? = 0.0,
    @field:SerializedName("lon") var lon: Double? = 0.0,
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

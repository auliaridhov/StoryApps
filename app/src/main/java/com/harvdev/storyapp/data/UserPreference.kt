package com.harvdev.storyapp.data

import android.content.Context
import com.harvdev.storyapp.model.UserModel

internal class UserPreference(context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setUser(value: UserModel) {
        val editor = preferences.edit()
        editor.putString(USER_ID, value.userId)
        editor.putString(NAME, value.name)
        editor.putString(TOKEN, value.token)
        editor.apply()
    }
    fun getUser(): UserModel {
        val model = UserModel()
        model.userId = preferences.getString(USER_ID, "")
        model.name = preferences.getString(NAME, "")
        model.token = preferences.getString(TOKEN, "")
        return model
    }
    fun removeUser(){
        val editor = preferences.edit()
        editor.putString(USER_ID, "")
        editor.putString(NAME, "")
        editor.putString(TOKEN, "")
        editor.apply()
    }
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val USER_ID = "email"
        private const val TOKEN = "token"
    }
}
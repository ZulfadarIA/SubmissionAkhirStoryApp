package com.dicoding.submission1storyapp.data

import android.content.Context

class UserPreference(context: Context) {
    companion object {
        const val PREFERENCE_NAME = "login_pref"
        const val TOKEN = "token"
    }

    private val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE )

    fun setUserToken(token: String) {
        val edit = preference.edit()
        edit.putString(TOKEN, token)
        edit.apply()
    }

    fun getUserToken(): String? {
        return preference.getString(TOKEN, null)
    }

    fun clearUserToken() {
        val edit = preference.edit().remove(TOKEN)
        edit.apply()
    }
}
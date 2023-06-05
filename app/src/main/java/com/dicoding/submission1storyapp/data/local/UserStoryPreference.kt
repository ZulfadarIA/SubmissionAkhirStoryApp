package com.dicoding.submission1storyapp.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserStoryPreference private constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        @Volatile
        private var INSTANCE: UserStoryPreference? = null
        private var TOKENUSER = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>) : UserStoryPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserStoryPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    fun getTokenKey(): Flow<String> {
        return  dataStore.data.map { preference ->
            preference[TOKENUSER] ?: ""
        }
    }

    suspend fun saveTokenKey(tokenUser: String) {
        dataStore.edit { preference ->
            preference[TOKENUSER] = tokenUser
        }
    }
}
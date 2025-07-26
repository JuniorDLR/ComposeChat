package com.example.composechat.data.network.database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.composechat.domain.DatabaseService
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DatabaseServiceImpl @Inject constructor(private val context: Context) : DatabaseService {

    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user"
    )

    companion object {
        private val NICKNAME = stringPreferencesKey(name = "username")
        private val IS_REGISTERED = booleanPreferencesKey(name = "is_registered")
        private val DEVICE_ID = stringPreferencesKey(name = "device_id")
    }

    override suspend fun saveNickname(nickname: String) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[NICKNAME] = nickname
            preferences[IS_REGISTERED] = true
        }
    }

    override suspend fun getUserName(): Flow<String> {
        return context.userPreferencesDataStore.data.map { preferences ->
            preferences[NICKNAME] ?: ""
        }
    }

    override suspend fun isUserRegistered(): Flow<Boolean> {
        return context.userPreferencesDataStore.data.map { preferences ->
            preferences[IS_REGISTERED] ?: false
        }
    }

    override suspend fun clearUserData() {
        context.userPreferencesDataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override suspend fun saveDeviceId(deviceId: String) {
        context.userPreferencesDataStore.edit { preferences ->
            preferences[DEVICE_ID] = deviceId
        }
    }

    override suspend fun getDeviceId(): Flow<String> {
        return context.userPreferencesDataStore.data.map { preferences ->
            preferences[DEVICE_ID] ?: ""
        }
    }
}
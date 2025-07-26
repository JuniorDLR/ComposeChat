package com.example.composechat.domain

import kotlinx.coroutines.flow.Flow

interface DatabaseService{
    suspend fun saveNickname(nickname: String)
    suspend fun getUserName(): Flow<String>
    suspend fun isUserRegistered(): Flow<Boolean>
    suspend fun clearUserData()
    suspend fun saveDeviceId(deviceId: String)
    suspend fun getDeviceId(): Flow<String>
}
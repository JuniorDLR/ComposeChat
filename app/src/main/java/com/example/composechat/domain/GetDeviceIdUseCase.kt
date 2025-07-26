package com.example.composechat.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDeviceIdUseCase @Inject constructor(private val databaseService: DatabaseService) {
    suspend operator fun invoke(): Flow<String> = databaseService.getDeviceId()
} 
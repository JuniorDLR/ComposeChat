package com.example.composechat.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsUserRegisteredUseCase @Inject constructor(private val databaseService: DatabaseService) {
    suspend operator fun invoke(): Flow<Boolean> = databaseService.isUserRegistered()
} 
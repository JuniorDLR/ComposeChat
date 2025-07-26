package com.example.composechat.domain

import javax.inject.Inject

class ClearUserDataUseCase @Inject constructor(private val databaseService: DatabaseService) {
    suspend operator fun invoke() {
        databaseService.clearUserData()
    }
} 
package com.example.composechat.domain

import javax.inject.Inject

class SaveDeviceIdUseCase @Inject constructor(private val databaseService: DatabaseService) {
    suspend operator fun invoke(deviceId: String) {
        databaseService.saveDeviceId(deviceId)
    }
} 
package com.example.composechat.domain.dto

data class UserConfig(
    val userId: String,
    val userName: String,
    val deviceId: String = "",
    val isAdmin: Boolean = false
) {
    companion object {
        fun createUserConfig(userName: String, deviceId: String = ""): UserConfig {
            return UserConfig(
                userId = "${userName}_${System.currentTimeMillis()}",
                userName = userName,
                deviceId = deviceId,
                isAdmin = false
            )
        }
    }
} 
package com.example.composechat.domain

import javax.inject.Inject

class SaveUserNameUseCase @Inject constructor(private val databaseService: DatabaseService) {

    suspend operator fun invoke(userName: String) {
        val userName = userName.trim()
        databaseService.saveNickname(userName)
    }

}
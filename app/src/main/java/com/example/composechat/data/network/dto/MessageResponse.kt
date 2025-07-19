package com.example.composechat.data.network.dto

import com.example.composechat.domain.dto.MessageResponseModel
import com.example.composechat.domain.dto.UserResponseModel


data class MessageResponse(
    val message: String? = null,
    val dateTime: String? = null,
    val user: UserResponse? = null
)

data class UserResponse(
    val userName: String? = null, val admin: Boolean? = null
)


fun MessageResponse.toDomain(): MessageResponseModel {
    return MessageResponseModel(
        message = this.message.orEmpty(),
        dateTime = this.dateTime ?: "Fecha no valida",
        user = UserResponseModel(
            userName = this.user?.userName ?: "Guess",
            admin = this.user?.admin ?: false
        )
    )
}
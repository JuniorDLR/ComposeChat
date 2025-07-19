package com.example.composechat.domain.dto

data class MessageResponseModel(
    val message: String,
    val dateTime: String,
    val user: UserResponseModel
)

data class UserResponseModel(
    val userName: String, val admin: Boolean
)

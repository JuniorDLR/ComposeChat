package com.example.composechat.data.network.dto

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MessageDTO(
    val message: String = "",
    val dateTime: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),
    val user: UserDTO
)

data class UserDTO(
    val userName: String, val admin: Boolean
)
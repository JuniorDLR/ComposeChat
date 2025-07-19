package com.example.composechat.domain.dto

import com.example.composechat.data.network.dto.MessageDTO
import com.example.composechat.data.network.dto.UserDTO
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class MessageDomain(
    val message: String = "",
    val dateTime: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),
    val user: UserDomain
)
data class UserDomain(
    val userName: String, val admin: Boolean
)

fun MessageDomain.mapToMessageDomain(): MessageDTO {
    return MessageDTO(
        message = this.message,
        dateTime = this.dateTime,
        user = UserDTO(
            userName = this.user.userName,
            admin = this.user.admin
        )
    )
}

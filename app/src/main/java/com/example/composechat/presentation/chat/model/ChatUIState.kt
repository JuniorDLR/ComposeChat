package com.example.composechat.presentation.chat.model

import com.example.composechat.domain.dto.MessageResponseModel

data class ChatUIState(
    val messageState: String = "",
    val messageResponse: List<MessageResponseModel> = emptyList()
)


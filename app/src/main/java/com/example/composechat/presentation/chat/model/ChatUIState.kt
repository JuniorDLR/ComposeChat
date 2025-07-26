package com.example.composechat.presentation.chat.model

import com.example.composechat.domain.dto.MessageResponseModel

data class MainUIState(
    val nickNameUIState: NicknameUIState = NicknameUIState(),
    val chatUIState: ChatUIState = ChatUIState(),
    val authUiState: AuthUiState = AuthUiState()
)

data class NicknameUIState(
    val nickName: String = "",
    val getName: String = "",
    val isRegistered: Boolean = false
)

data class ChatUIState(
    val messageState: String = "",
    val messageResponse: List<MessageResponseModel> = emptyList(),
    val currentUser: String = ""
)


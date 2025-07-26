package com.example.composechat.presentation.chat.model

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRegistered: Boolean = false,
    val currentUser: String = "",
    val isAuthenticated: Boolean = false
)
package com.example.composechat.presentation.viewmodel

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composechat.domain.GetMessageUseCase
import com.example.composechat.domain.GetUserNameUseCase
import com.example.composechat.domain.SaveUserNameUseCase
import com.example.composechat.domain.SendMessageUseCase
import com.example.composechat.domain.IsUserRegisteredUseCase
import com.example.composechat.domain.ClearUserDataUseCase
import com.example.composechat.domain.GetDeviceIdUseCase
import com.example.composechat.domain.SaveDeviceIdUseCase
import com.example.composechat.domain.dto.MessageDomain
import com.example.composechat.presentation.chat.model.MainUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessageUseCase: GetMessageUseCase,
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private val getUserNameUseCase: GetUserNameUseCase,
    private val isUserRegisteredUseCase: IsUserRegisteredUseCase,
    private val clearUserDataUseCase: ClearUserDataUseCase,
    private val getDeviceIdUseCase: GetDeviceIdUseCase,
    private val saveDeviceIdUseCase: SaveDeviceIdUseCase
) : ViewModel() {

    private val _mainUIState = MutableStateFlow(MainUIState())
    val mainUIState: StateFlow<MainUIState> = _mainUIState.asStateFlow()

    init {
        checkUserRegistration()
        loadMessage()
        initializeDeviceId()
    }

    private fun initializeDeviceId() {
        viewModelScope.launch {
            getDeviceIdUseCase().collect { deviceId ->
                if (deviceId.isEmpty()) {
                    val newDeviceId = generateDeviceId()
                    saveDeviceIdUseCase(newDeviceId)
                }
            }
        }
    }

    private fun generateDeviceId(): String {
        return "${Build.MANUFACTURER}_${Build.MODEL}_${System.currentTimeMillis()}"
    }

    private fun checkUserRegistration() {
        viewModelScope.launch {
            isUserRegisteredUseCase().collect { isRegistered ->
                if (isRegistered) {
                    getUserName()
                }
                _mainUIState.update { mainUIState ->
                    mainUIState.copy(
                        nickNameUIState = mainUIState.nickNameUIState.copy(
                            isRegistered = isRegistered
                        ),
                        authUiState = mainUIState.authUiState.copy(
                            isRegistered = isRegistered,
                            isAuthenticated = isRegistered
                        )
                    )
                }
            }
        }
    }

    private fun getUserName() {
        viewModelScope.launch {
            val userName = getUserNameUseCase()
            _mainUIState.update { mainUIState ->
                mainUIState.copy(
                    nickNameUIState = mainUIState.nickNameUIState.copy(
                        getName = userName,
                        nickName = userName
                    ),
                    chatUIState = mainUIState.chatUIState.copy(
                        currentUser = userName
                    ),
                    authUiState = mainUIState.authUiState.copy(
                        currentUser = userName
                    )
                )
            }

        }
    }

    fun sendMessage(messageDomain: MessageDomain) {
        sendMessageUseCase.invoke(messageDTO = messageDomain)
    }

    fun updateMessageObserved(newValue: String) {
        _mainUIState.update { mainUIState ->
            mainUIState.copy(
                chatUIState = mainUIState.chatUIState.copy(
                    messageState = newValue
                )
            )
        }
    }

    fun loadMessage() {
        viewModelScope.launch {
            getMessageUseCase.invoke().collect { newResponse ->
                _mainUIState.update { mainUIState ->
                    mainUIState.copy(
                        chatUIState = mainUIState.chatUIState.copy(
                            messageResponse = newResponse
                        )
                    )
                }
            }
        }
    }

    fun observedNickName(nickname: String) {
        _mainUIState.update { mainUIState ->
            mainUIState.copy(nickNameUIState = mainUIState.nickNameUIState.copy(nickName = nickname))
        }
    }

    private fun saveNickname(nickname: String) {
        viewModelScope.launch(context = Dispatchers.IO) {
            saveUserNameUseCase.invoke(nickname)
        }
    }

    fun validateUser() {
        val nickname = _mainUIState.value.nickNameUIState.nickName
        if (nickname.isNotEmpty()) {
            saveNickname(nickname = nickname)
            _mainUIState.update { mainUIState ->
                mainUIState.copy(
                    authUiState = mainUIState.authUiState.copy(
                        isLoading = true
                    )
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch(context = Dispatchers.IO) {
            clearUserDataUseCase()
            _mainUIState.update { mainUIState ->
                mainUIState.copy(
                    nickNameUIState = mainUIState.nickNameUIState.copy(
                        nickName = "",
                        getName = "",
                        isRegistered = false
                    ),
                    authUiState = mainUIState.authUiState.copy(
                        isRegistered = false,
                        isAuthenticated = false,
                        currentUser = ""
                    )
                )
            }
        }
    }
}

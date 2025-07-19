package com.example.composechat.presentation.chat.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composechat.domain.GetMessageUseCase
import com.example.composechat.domain.dto.MessageDomain
import com.example.composechat.domain.dto.MessageResponseModel
import com.example.composechat.domain.dto.UserResponseModel
import com.example.composechat.domain.SendMessageUseCase
import com.example.composechat.presentation.chat.model.ChatUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val sendMessageUseCase: SendMessageUseCase,
    private val getMessageUseCase: GetMessageUseCase
) : ViewModel() {

    private val _chatUIState = MutableStateFlow(value = ChatUIState())
    val chatUIState: StateFlow<ChatUIState> = _chatUIState

    init {
        getMessageFromFirebase()
    }


    private fun formatTime(dateTime: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val date = inputFormat.parse(dateTime)
            date?.let { outputFormat.format(it) } ?: dateTime
        } catch (e: Exception) {
            dateTime
        }
    }


    private fun processMessagesWithFormattedTime(messages: List<MessageResponseModel>): List<MessageResponseModel> {
        return messages.map { message ->
            message.copy(
                dateTime = formatTime(message.dateTime)
            )
        }
    }

    fun updateMessageObserved(newValue: String) {
        _chatUIState.update { chatUIState ->
            chatUIState.copy(
                messageState = newValue
            )
        }
    }

    fun sendMessage(messageDomain: MessageDomain) {
        sendMessageUseCase(messageDTO = messageDomain)
        clearMessageState()

    }

    fun clearMessageState() {
        _chatUIState.update { chatUIState ->
            chatUIState.copy(
                messageState = ""
            )
        }
    }

    fun getMessageFromFirebase() {
        viewModelScope.launch {
            getMessageUseCase().collect { newModel ->
                val messagesWithFormattedTime = processMessagesWithFormattedTime(newModel)
                _chatUIState.update { chatUIState ->
                    chatUIState.copy(
                        messageResponse = messagesWithFormattedTime
                    )
                }

            }
        }
    }

}
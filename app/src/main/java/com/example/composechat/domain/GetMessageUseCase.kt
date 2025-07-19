package com.example.composechat.domain

import com.example.composechat.data.network.FirebaseChatService
import com.example.composechat.domain.dto.MessageResponseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessageUseCase @Inject constructor(
    private val firebaseChatService: FirebaseChatService
) {
    operator fun invoke(): Flow<List<MessageResponseModel>> {
        return firebaseChatService.getMessageFromFirebase()
    }
}
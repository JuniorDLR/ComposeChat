package com.example.composechat.domain

import com.example.composechat.data.network.FirebaseChatService
import com.example.composechat.domain.dto.MessageDomain
import com.example.composechat.domain.dto.mapToMessageDomain
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val firebaseChatService: FirebaseChatService
) {
    operator fun invoke(messageDTO: MessageDomain) {
        firebaseChatService.sendMessage(messageDTO = messageDTO.mapToMessageDomain())
    }

}
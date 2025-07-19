package com.example.composechat.data.network

import com.example.composechat.data.network.dto.MessageDTO
import com.example.composechat.data.network.dto.MessageResponse
import com.example.composechat.data.network.dto.toDomain
import com.example.composechat.domain.dto.MessageResponseModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FirebaseChatService @Inject constructor(
    private val reference: DatabaseReference
) {

    companion object {
        private const val PATH = "messages"
    }

    fun sendMessage(messageDTO: MessageDTO) {
        reference.child(PATH).push().setValue(messageDTO)
    }

    fun getMessageFromFirebase(): Flow<List<MessageResponseModel>> {
        return reference.child(PATH).snapshots.map { snapshot ->
            snapshot.children.mapNotNull { dataSnapshot ->
                dataSnapshot.getValue(MessageResponse::class.java)?.toDomain()
            }
        }
    }


}
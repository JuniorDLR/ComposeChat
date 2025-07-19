package com.example.composechat.presentation.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composechat.domain.dto.MessageDomain
import com.example.composechat.domain.dto.MessageResponseModel
import com.example.composechat.domain.dto.UserDomain
import com.example.composechat.presentation.chat.viewmodel.ChatViewModel


@Composable
fun ChatMain(
    onBackPressed: () -> Unit = {},
    chatViewModel: ChatViewModel
) {
    val messageObserve by chatViewModel.chatUIState.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppChat(onBackPressed = onBackPressed) },
        bottomBar = {
            MessageInputBar(
                value = messageObserve.messageState,
                chatViewModel = chatViewModel,
                onSendMessage = { message ->
                    val messageDomain = MessageDomain(
                        message = message,
                        user = UserDomain(
                            userName = "junior",
                            admin = true
                        )
                    )
                    chatViewModel.sendMessage(messageDomain = messageDomain)
                })
        },
        containerColor = Color.White,
        contentColor = Color.Black,
    ) { paddingValues ->
        BodyChat(
            paddingValues = paddingValues,
            messages = messageObserve.messageResponse,
        )
    }
}

@Composable
fun BodyChat(
    paddingValues: PaddingValues,
    messages: List<MessageResponseModel>
) {

    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .padding(paddingValues)
            .imePadding()
    ) {
        items(items = messages) { message ->
            ChatMessageBubbleResponse(
                message = message,
            )
        }
    }
    LaunchedEffect(key1 = messages.size) {
        listState.animateScrollToItem(index = messages.size - 1)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppChat(onBackPressed: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = "Bienvenido Junior") },
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier.clickable(onClick = onBackPressed)
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInputBar(
    onSendMessage: (String) -> Unit,
    chatViewModel: ChatViewModel,
    value: String
) {


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    chatViewModel.updateMessageObserved(newValue = newValue)
                },
                placeholder = { Text("Escribe un mensaje...") },
                modifier = Modifier.weight(1f),
                maxLines = 3
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    if (value.isNotBlank()) {
                        onSendMessage(value)
                    }
                },
                enabled = value.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Enviar mensaje",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
} 
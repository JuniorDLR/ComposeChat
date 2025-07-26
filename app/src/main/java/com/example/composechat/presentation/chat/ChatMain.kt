package com.example.composechat.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composechat.domain.dto.MessageDomain
import com.example.composechat.domain.dto.UserDomain
import com.example.composechat.presentation.viewmodel.MainViewModel

@Composable
fun ChatMain(
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val mainUIState by mainViewModel.mainUIState.collectAsState()
    val listState = rememberLazyListState()
    
    // Auto-scroll to bottom when new messages arrive
    LaunchedEffect(mainUIState.chatUIState.messageResponse.size) {
        if (mainUIState.chatUIState.messageResponse.isNotEmpty()) {
            listState.animateScrollToItem(mainUIState.chatUIState.messageResponse.size - 1)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppChat(
                username = mainUIState.nickNameUIState.getName,
                onBackPressed = {
                    navController.popBackStack()
                },
                onLogout = {
                    mainViewModel.logout()
                    navController.popBackStack()
                }
            )
        },
        bottomBar = {
            MessageInputBar(
                value = mainUIState.chatUIState.messageState,
                mainViewModel = mainViewModel,
                onSendMessage = { message ->
                    val messageDomain = MessageDomain(
                        message = message,
                        user = UserDomain(
                            userName = mainUIState.nickNameUIState.getName,
                            admin = false
                        )
                    )
                    mainViewModel.sendMessage(messageDomain = messageDomain)
                }
            )
        },
        containerColor = Color(0xFFF5F5F5),
        contentColor = Color.Black,
    ) { paddingValues ->
        BodyChat(
            paddingValues = paddingValues,
            messages = mainUIState.chatUIState.messageResponse,
            currentUser = mainUIState.chatUIState.currentUser,
            listState = listState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppChat(
    username: String,
    onBackPressed: () -> Unit,
    onLogout: () -> Unit
) {
    TopAppBar(
        title = {
            Column {
                Text(
                    text = "ComposeChat",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "Conectado como: $username",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
            }
        },
        actions = {
            IconButton(onClick = onLogout) {
                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Cerrar sesión")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black,
            actionIconContentColor = Color.Black
        )
    )
}

@Composable
fun BodyChat(
    paddingValues: PaddingValues,
    messages: List<com.example.composechat.domain.dto.MessageResponseModel>,
    currentUser: String,
    listState: androidx.compose.foundation.lazy.LazyListState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 8.dp)
            .imePadding() ,
        state = listState,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if (messages.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "¡Inicia la conversación!",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            items(messages) { message ->
                ChatMessageBubbleResponse(
                    message = message,
                    currentUser = currentUser
                )
            }
        }
    }
}

@Composable
fun MessageInputBar(
    value: String,
    mainViewModel: MainViewModel,
    onSendMessage: (String) -> Unit
) {
    var messageText by remember { mutableStateOf(value) }
    
    LaunchedEffect(value) {
        messageText = value
    }

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { 
                    messageText = it
                    mainViewModel.updateMessageObserved(it)
                },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Escribe un mensaje...") },
                maxLines = 4,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Blue,
                    unfocusedBorderColor = Color.Gray
                )
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FloatingActionButton(
                onClick = {
                    if (messageText.isNotEmpty()) {
                        onSendMessage(messageText)
                        messageText = ""
                        mainViewModel.updateMessageObserved("")
                    }
                },
                containerColor = Color.Blue,
                contentColor = Color.White,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Enviar")
            }
        }
    }
} 
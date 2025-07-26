package com.example.composechat.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.composechat.R
import com.example.composechat.presentation.route.Chat
import com.example.composechat.presentation.viewmodel.MainViewModel

@Composable
fun ScreenMain(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val mainUIState by viewModel.mainUIState.collectAsState()

    LaunchedEffect(mainUIState.authUiState.isAuthenticated) {
        if (mainUIState.authUiState.isAuthenticated && mainUIState.authUiState.currentUser.isNotEmpty()) {
            navController.navigate(Chat) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(all = 24.dp),
        verticalArrangement = Arrangement.spacedBy(space = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        ImageLogo()
        HeaderMain()
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                
                ObservedNickName(
                    value = mainUIState.nickNameUIState.nickName,
                    onValueChange = {
                        viewModel.observedNickName(nickname = it)
                    }
                )
                
                ButtonMain(
                    onClick = {
                        if (mainUIState.nickNameUIState.nickName.isNotEmpty()) {
                            viewModel.validateUser()
                            navController.navigate(Chat)
                        }
                    },
                    isLoading = mainUIState.authUiState.isLoading,
                    enabled = mainUIState.nickNameUIState.nickName.isNotEmpty()
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ObservedNickName(value: String = "", onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        label = { Text(text = "Escribe tu nombre") },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Blue,
            unfocusedBorderColor = Color.Gray
        ),
        singleLine = true
    )
}

@Composable
fun ButtonMain(
    onClick: () -> Unit,
    isLoading: Boolean = false,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled && !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White,
            disabledContainerColor = Color.Gray
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = "Iniciar conversación",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun HeaderMain() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido a ComposeChat",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "El mejor chat del mundo mundial",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Gray
        )
    }
}

@Composable
fun ImageLogo() {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo",
        modifier = Modifier.size(120.dp)
    )
}
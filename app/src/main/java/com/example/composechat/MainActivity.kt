package com.example.composechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composechat.presentation.chat.ChatMain
import com.example.composechat.presentation.chat.ScreenMain
import com.example.composechat.presentation.route.Chat
import com.example.composechat.presentation.route.Home
import com.example.composechat.presentation.viewmodel.MainViewModel
import com.example.composechat.ui.theme.ComposeChatTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeChatTheme {
                val navController = rememberNavController()
                val mainViewModel: MainViewModel = hiltViewModel()

                NavHost(
                    navController = navController,
                    startDestination = Home
                ) {
                    composable<Home> {
                        ScreenMain(navController = navController, viewModel = mainViewModel)
                    }
                    composable<Chat> {
                        ChatMain(navController = navController, mainViewModel = mainViewModel)
                    }
                }
            }
        }
    }
}
package com.example.composechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composechat.presentation.chat.ChatMain
import com.example.composechat.presentation.chat.ScreenMain
import com.example.composechat.presentation.route.Chat
import com.example.composechat.presentation.route.Home
import com.example.composechat.ui.theme.ComposeChatTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ComposeChatTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Home
                ) {
                    composable<Home> {
                        ScreenMain(
                            onClick = {
                                navController.navigate(Chat)
                            }
                        )
                    }
                    composable<Chat> {
                        ChatMain(
                            onBackPressed = {
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}
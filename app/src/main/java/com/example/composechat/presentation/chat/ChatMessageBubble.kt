package com.example.composechat.presentation.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.composechat.domain.dto.MessageResponseModel

@Composable
fun ChatMessageBubbleResponse(
    message: MessageResponseModel,
    currentUser: String,
    modifier: Modifier = Modifier
) {
    val isMe = message.user.userName == currentUser
    val bubbleColor = if (isMe) Color(0xFFDCF8C6) else Color(0xFFF0F0F0)
    val shape = if (isMe) RoundedCornerShape(16.dp, 16.dp, 4.dp, 16.dp)
                else RoundedCornerShape(16.dp, 16.dp, 16.dp, 4.dp)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
        ) {
            if (!isMe) {
                Text(
                    text = message.user.userName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 8.dp, bottom = 2.dp)
                )
            }
        }
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
        ) {
            Surface(
                color = bubbleColor,
                shape = shape,
                shadowElevation = 2.dp,
                modifier = Modifier.widthIn(max = 280.dp)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp)
                ) {
                    Text(
                        text = message.message,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = if (isMe) TextAlign.End else TextAlign.Start,
                        color = Color.Black,
                        lineHeight = 20.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
                    ) {
                        Text(
                            text = formatTime(message.dateTime),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }
    }
}

private fun formatTime(dateTime: String): String {
    return try {
        if (dateTime.contains(" ")) {
            dateTime.split(" ")[1].substring(0, 5) // HH:mm
        } else {
            dateTime
        }
    } catch (e: Exception) {
        dateTime
    }
}



package com.example.composechat.presentation.chat


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.example.composechat.domain.dto.MessageResponseModel

@Composable
fun ChatMessageBubbleResponse(
    message: MessageResponseModel,
    modifier: Modifier = Modifier
) {
    val isMe = message.user.admin
    val bubbleColor = if (isMe) Color(0xFFDCF8C6) else Color(0xFFFFFFFF)
    val shape = if (isMe) RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp)
                else RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = bubbleColor,
            shape = shape,
            shadowElevation = 3.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = message.message,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = if (isMe) TextAlign.End else TextAlign.Start,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.dateTime,
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = if (isMe) TextAlign.End else TextAlign.Start,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}



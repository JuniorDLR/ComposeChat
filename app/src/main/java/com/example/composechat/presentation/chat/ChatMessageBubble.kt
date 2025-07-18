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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Modelo de mensaje
 data class Message(
    val id: String,
    val text: String,
    val senderId: String,
    val timestamp: Long = System.currentTimeMillis()
)

/**
 * Burbuja de mensaje tipo WhatsApp.
 * @param message El mensaje a mostrar
 * @param currentUserId El id del usuario actual
 */
@Composable
fun ChatMessageBubble(
    message: Message,
    currentUserId: String,
    modifier: Modifier = Modifier
) {
    val isMe = message.senderId == currentUserId
    val bubbleColor = if (isMe) Color(0xFFDCF8C6) else Color(0xFFFFFFFF)
    val shape = if (isMe) RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp)
                else RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = bubbleColor,
            shape = shape,
            shadowElevation = 2.dp,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(12.dp),
                textAlign = if (isMe) TextAlign.End else TextAlign.Start,
                color = Color.Black
            )
        }
    }
}


@Preview
@Composable
fun PreviewChatMessageBubble() {
    Column {
        ChatMessageBubble(
            message = Message(id = "1", text = "Hola, ¿cómo estás?", senderId = "me"),
            currentUserId = "me"
        )
        ChatMessageBubble(
            message = Message(id = "2", text = "¡Hola! Todo bien, ¿y tú?", senderId = "guest"),
            currentUserId = "me"
        )
    }
}

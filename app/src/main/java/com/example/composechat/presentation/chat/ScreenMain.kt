package com.example.composechat.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composechat.R

@Composable
fun ScreenMain(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImageLogo()
        HeaderMain()
        ButtonMain(onClick = onClick)
    }


}

@Composable
fun ButtonMain(onClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White
        ),
        onClick = onClick
    ) {
        Text(text = "Iniciar conversación")
    }
}

@Composable
fun HeaderMain() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido a ComposeChat", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(height = 10.dp))
        Text(
            text = "El mejor chat del mundo mundial",
            fontSize = 16.sp,
            fontWeight = FontWeight.W300
        )

    }
}

@Composable
fun ImageLogo() {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = "Logo",
        modifier = Modifier.size(size = 200.dp)
    )
}
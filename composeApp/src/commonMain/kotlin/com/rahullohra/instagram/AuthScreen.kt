package com.rahullohra.instagram

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import instagramclone.composeapp.generated.resources.Res
import instagramclone.composeapp.generated.resources.instagram_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun AuthScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Divider(Modifier.height(0.5.dp))
            Spacer(Modifier.height(18.dp))
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)
                .clickable {

                }) {
                Text("Don't have an account?", fontSize = 12.sp, color = Color(0x66000000))
                Spacer(Modifier.width(4.dp))
                Text(
                    " Sign up",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp, color = Color(0xFF262626)
                )
            }
            Spacer(Modifier.height(18.dp))
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IgLogo()
            Spacer(Modifier.height(52.dp))
            IgProfileIcon()
            Spacer(Modifier.height(12.dp))
            IgFilledButton(Modifier.padding(horizontal = 34.dp), "Log in")
            Spacer(Modifier.height(30.dp))
            IgTextButton("Switch accounts")
        }
    }
}

@Composable
private fun IgLogo() {
    Image(painterResource(Res.drawable.instagram_logo), null)
}

@Composable
private fun IgProfileIcon() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = loadImageResource("auth_image.webp"),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 13.dp)
        )
        Text(
            "Jacob", fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color(0xFF262626), textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun IgFilledButton(modifier: Modifier, text: String) {
    Button(modifier = modifier.fillMaxWidth()
        .height(44.dp),
        onClick = { }) {
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

@Composable
private fun IgTextButton(text: String) {
    TextButton(modifier = Modifier.offset(y = (-4).dp), onClick = {}) {
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

@Composable
expect fun loadImageResource(name: String): Painter
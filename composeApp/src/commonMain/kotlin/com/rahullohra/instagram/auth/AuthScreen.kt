package com.rahullohra.instagram.auth

import IgLogo
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahullohra.instagram.IgFilledButton
import com.rahullohra.instagram.IgTextButton
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun AuthScreen(onSwitchAccountClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Divider(Modifier.height(0.5.dp))
            Spacer(Modifier.height(18.dp))
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)
                .clickable {

                }) {
                Text(
                    "Don't have an account?", fontSize = 12.sp,
                    color = MaterialTheme.colors.onSurface
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    " Sign up",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colors.onBackground
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
            IgTextButton("Switch accounts", onSwitchAccountClick = onSwitchAccountClick)
        }
    }
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
            color = MaterialTheme.colors.onBackground, textAlign = TextAlign.Center
        )
    }
}



@Composable
expect fun loadImageResource(name: String): Painter
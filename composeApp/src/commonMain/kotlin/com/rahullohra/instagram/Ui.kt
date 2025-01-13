package com.rahullohra.instagram

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IgFilledButton(modifier: Modifier, text: String) {
    Button(modifier = modifier.fillMaxWidth()
        .height(44.dp),
        onClick = { }) {
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}

@Composable
fun IgTextButton(text: String, modifier: Modifier = Modifier, onSwitchAccountClick: () -> Unit) {
    TextButton(modifier = modifier.offset(y = (-4).dp), onClick = onSwitchAccountClick) {
        Text(text, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}
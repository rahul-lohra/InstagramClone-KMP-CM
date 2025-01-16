package com.rahullohra.instagram

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import instagramclone.composeapp.generated.resources.Res
import instagramclone.composeapp.generated.resources.man_user_circle_icon
import org.jetbrains.compose.resources.painterResource

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

@Composable
fun ColouredCircle(dimen: Dp){
    Canvas(modifier = Modifier.size(dimen)) {
        drawCircle(
            brush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFFE20337),
                    Color(0xFFC60188),
                    Color(0xFF7700C3)
                ),
                start = Offset(80.718f, 37.319f),
                end = Offset(38.589f, -3.87f)
            ),
            radius = size.minDimension / 2,
            style = Stroke(width = 2.dp.toPx())
        )
    }
}

@Composable
fun UserIcon(modifier: Modifier) {
    Icon(
        painterResource(Res.drawable.man_user_circle_icon), null,
        modifier = modifier
    )
}
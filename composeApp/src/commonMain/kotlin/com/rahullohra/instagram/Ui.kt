package com.rahullohra.instagram

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import instagramclone.composeapp.generated.resources.Res
import instagramclone.composeapp.generated.resources.man_user_circle_icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun IgFilledButton(modifier: Modifier, text: String, onClick: () -> Unit = { }) {
    Button(modifier = modifier.fillMaxWidth()
        .height(44.dp),
        onClick = onClick) {
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


@Composable
fun CircularLoadingAnimation() {
    val infiniteTransition = rememberInfiniteTransition()

    // Rotation animation
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Number of bars in the circle
    val bars = 12
    val angleStep = 360f / bars

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.size(100.dp)) {
            Canvas(modifier = Modifier.fillMaxSize().rotate(rotation)) {
                val radius = size.minDimension / 2.5f
                for (i in 0 until bars) {
                    val alpha = (i.toFloat() / bars).coerceIn(0.3f, 1f) // Adjust opacity per bar
                    rotate(angleStep * i) {
                        drawLine(
                            color = Color(0xFF4CAF50).copy(alpha = alpha),
                            start = Offset(size.width / 2, size.height / 2 - radius),
                            end = Offset(size.width / 2, size.height / 2 - radius + 15),
                            strokeWidth = 8f,
                            cap = StrokeCap.Round
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Searching", color = Color.Gray)
    }
}

@Composable
fun LoadingButton(modifier: Modifier, text: String, isLoading:Boolean,
                  onClick: () -> Unit = { }) {
    Button(modifier = modifier.fillMaxWidth()
        .height(44.dp),
        onClick = onClick) {
        if(isLoading){
            CircularLoadingAnimation()
        }else {
            Text(text, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        }
    }
}

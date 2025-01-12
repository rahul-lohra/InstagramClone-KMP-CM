package com.rahullohra.instagram

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

@Composable
actual fun loadImageResource(name: String): Painter {
    return painterResource(id = R.mipmap.auth_image)
}
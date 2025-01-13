package com.rahullohra.instagram

import IgLogo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun UsernamePasswordScreen () {
    Box{
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IgLogo()
            Spacer(Modifier.height(39.dp))
            LoginForm()
        }
    }
}

@Composable
private fun LoginForm() {
    var userNameText by remember { mutableStateOf(TextFieldValue("")) }
    var passwordText by remember { mutableStateOf(TextFieldValue("")) }

    Row {
        TextField(placeholder = {
            Text("username or email")
        }, value = userNameText, onValueChange = {
            userNameText = it
        })
        Spacer(Modifier.height(12.dp))
        TextField(placeholder = {
            Text("password")
        }, value = passwordText, onValueChange = {
            userNameText = it
        })
    }
}
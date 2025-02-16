package com.rahullohra.instagram.auth.ui

import IgLogo
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.rahullohra.instagram.IgTextButton
import com.rahullohra.instagram.InstagramScreens
import com.rahullohra.instagram.LoadingButton
import com.rahullohra.instagram.auth.data.AuthRepository
import com.rahullohra.instagram.auth.data.local.AuthStore
import com.rahullohra.instagram.network.authApiService
import com.rahullohra.instagram.theme.md_theme_dark_navigation
import com.rahullohra.instagram.theme.md_theme_dark_placeholder
import com.rahullohra.instagram.theme.md_theme_light_navigation
import com.rahullohra.instagram.theme.md_theme_light_placeholder
import instagramclone.composeapp.generated.resources.Res
import instagramclone.composeapp.generated.resources.arrow_back_ios
import instagramclone.composeapp.generated.resources.eye_password_hide
import instagramclone.composeapp.generated.resources.eye_password_show
import instagramclone.composeapp.generated.resources.fb_logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun UsernamePasswordScreen(navController: NavHostController) {
    val authStore =  remember { AuthStore { "auth" }  }
    val viewModel: UsernamePasswordViewmodel = viewModel(
        key = UsernamePasswordViewmodel.KEY,
        factory = viewModelFactory {
            initializer {
                UsernamePasswordViewmodel(AuthRepository(authApiService, authStore))
            }
        }
    )
    val authState = authStore.getCredentials().collectAsState(null)
    if(authState.value != null){
        navController.navigate(InstagramScreens.Feed.title) {
            popUpTo(0) { inclusive = true }
        }
    }else{
        Scaffold(topBar = {
            TopAppBar(
                title = {},
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }, content = {
                        val isLight = MaterialTheme.colors.isLight
                        val iconColor = if (isLight) {
                            md_theme_light_navigation
                        } else {
                            md_theme_dark_navigation
                        }
                        Icon(painterResource(Res.drawable.arrow_back_ios), null, tint = iconColor)
                    })
                })
        }) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier.align(Alignment.Center)) {
                    Column(
                        modifier = Modifier.align(Alignment.Center).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        IgLogo()
                        Spacer(Modifier.height(39.dp))
                        LoginForm(Modifier.padding(horizontal = 16.dp), navController)
                        OrDivider(Modifier.padding(top = 41.5.dp, start = 16.dp, end = 16.dp))
                        SignUp(Modifier.padding(top = 41.5.dp))
                    }
                }
                Column(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Divider(Modifier.height(0.4.dp))
                    Spacer(modifier = Modifier.height(32.5.dp))
                    Text("Instagram from Facebook", color = MaterialTheme.colors.onSurface)
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }




}

@Composable
private fun LoginForm(modifier: Modifier, navController: NavHostController) {

    val viewModel: UsernamePasswordViewmodel = viewModel(key = UsernamePasswordViewmodel.KEY)

    val loginUiState by viewModel.loginState.collectAsState()
    if (loginUiState is LoginUiState.Success) {
        navController.navigate(InstagramScreens.Feed.title) {
            popUpTo(0) { inclusive = true }
        }
    }

    var userNameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf(("")) }

    Column(modifier) {
        UserNameField(userNameText, "Username or Email", onValueChange = {
            userNameText = it
        })
        Spacer(Modifier.height(12.dp))
        PasswordField(passwordText, "Password", onValueChange = {
            passwordText = it
        })
        Spacer(Modifier.height(19.dp))
        IgTextButton("Forgot password?", Modifier.align(Alignment.End), {})
        Spacer(Modifier.height(30.dp))
        ErrorUI(loginUiState)
        LoadingButton(modifier, "Login", loginUiState == LoginUiState.Loading, onClick = {
            viewModel.onLogin(userNameText, passwordText)
        })
        Spacer(Modifier.height(37.dp))
        LoginWithFacebook(Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun ErrorUI(loginUiState: LoginUiState) {
    if (loginUiState is LoginUiState.Fail) {
        Text(
            loginUiState.message,
            fontSize = 14.sp,
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 10.dp).fillMaxWidth()
        )
    }
}

@Composable
fun PasswordField(
    text: String,
    placeholderText: String,
    onValueChange: (String) -> Unit
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    val visualTransformation =
        if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    val keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)

    val trailingIcon = @Composable {
        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = if (isPasswordVisible) painterResource(Res.drawable.eye_password_hide) else painterResource(
                    Res.drawable.eye_password_show
                ),
                contentDescription = "Toggle Password Visibility"
            )
        }
    }
    UserNameField(
        text,
        placeholderText,
        visualTransformation,
        keyboardOptions,
        trailingIcon,
        onValueChange
    )
}

@Composable
fun UserNameField(
    text: String,
    placeholderText: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {

    val isLight = MaterialTheme.colors.isLight
    val roundShape = RoundedCornerShape(5.dp)
    val borderColor = if (isLight) {
        Color(0x1A000000)
    } else {
        Color(0x33FFFFFF)
    }

    val bgColor = if (isLight) {
        Color(0xFFFAFAFA)
    } else {
        Color(0xFF121212)
    }

    val placeholderColor = if (isLight) {
        md_theme_light_placeholder
    } else {
        md_theme_dark_placeholder
    }

    TextField(
        modifier = Modifier.fillMaxWidth()
            .clip(roundShape)
            .border(
                width = (0.5).dp,
                color = borderColor,
                shape = roundShape
            ),
        shape = TextFieldDefaults.OutlinedTextFieldShape,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            backgroundColor = bgColor
        ),
        singleLine = true,
        placeholder = {
            Text(text = placeholderText, color = placeholderColor)
        },
        value = text, onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon
    )
}

@Composable
fun LoginWithFacebook(modifier: Modifier) {
    TextButton(onClick = {}, content = {
        Icon(painterResource(Res.drawable.fb_logo), null)
        Spacer(Modifier.width(10.dp))
        Text("Log in with Facebook", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }, modifier = modifier)
}

@Composable
private fun OrDivider(modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Divider(Modifier.weight(1f).height(0.33.dp))
        Text(
            "OR",
            color = MaterialTheme.colors.onSurface,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier.padding(horizontal = 30.5.dp)
        )
        Divider(Modifier.weight(1f).height(0.33.dp))
    }
}

@Composable
private fun SignUp(modifier: Modifier) {
    Row(modifier = modifier
        .clickable {

        }) {
        Text(
            "Don't have an account?", fontSize = 14.sp,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(Modifier.width(4.dp))
        Text(
            " Sign up",
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            color = MaterialTheme.colors.primary
        )
    }
}


package com.rahullohra.instagram

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.svg.SvgDecoder
import com.rahullohra.instagram.auth.ui.AuthScreen
import com.rahullohra.instagram.auth.ui.UsernamePasswordScreen
import com.rahullohra.instagram.feed.FeedScreen
import com.rahullohra.instagram.theme.IgTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import instagramclone.composeapp.generated.resources.Res
import instagramclone.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    /**
     * Image Configuration
     */
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
            }
            .build()
    }

    IgTheme {
        Scaffold() {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = InstagramScreens.UserNamePassword.title){
                composable(route = InstagramScreens.Auth.title) {
                    AuthScreen(onSwitchAccountClick = {
                        navController.navigate(InstagramScreens.UserNamePassword.title)
                    })
                }
                composable(route = InstagramScreens.UserNamePassword.title) {
                    UsernamePasswordScreen(navController)
                }
                composable(route = InstagramScreens.Feed.title) {
                    FeedScreen(navController)
                }
            }
        }
    }
}

@Composable
fun Sample() {
    var showContent by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { showContent = !showContent }) {
            Text("Click me!")
        }
        AnimatedVisibility(showContent) {
            val greeting = remember { Greeting().greet() }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Image(painterResource(Res.drawable.compose_multiplatform), null)
                Text("Compose: $greeting")
            }
        }
    }
}

enum class InstagramScreens(val title: String) {
    Auth(title = "auth"),
    UserNamePassword(title = "username_password"),
    Feed(title = "feed"),
}

package com.rahullohra.instagram.feed

import IgLogo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rahullohra.instagram.feed.post.PostListUi
import instagramclone.composeapp.generated.resources.Res
import instagramclone.composeapp.generated.resources.camera
import instagramclone.composeapp.generated.resources.heart
import instagramclone.composeapp.generated.resources.home_filled
import instagramclone.composeapp.generated.resources.igtv
import instagramclone.composeapp.generated.resources.messanger
import instagramclone.composeapp.generated.resources.search
import instagramclone.composeapp.generated.resources.story
import org.jetbrains.compose.resources.painterResource

@Composable
fun FeedScreen(navHostController: NavHostController) {
    Scaffold(topBar = { FeedAppBar() }, bottomBar = { FeedBottomBar() }) {
        val lazyColumnListState = rememberLazyListState()
        val itemsList = (0..5).toList()
        val itemsIndexedList = listOf("Your Story", "karenne", "zackjohn", "kieron_d", "crag_love", "John", "Wick")
        LazyColumn (state = lazyColumnListState, modifier = Modifier.padding(bottom = 56.dp)) {
            itemsIndexed(itemsIndexedList) { index, item ->
                if(index == 0){
                    StoryUi()
                }else {
                    PostListUi()
                    Spacer(Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
fun FeedAppBar() {
    TopAppBar(title = {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            IgLogo()
        }
    }, elevation = 0.dp,
        backgroundColor = Color.Transparent,
        navigationIcon = {
        IconButton(onClick = {}) {
            Icon(painterResource(Res.drawable.camera), null)
        }
    }, actions = {
        Row {
            IconButton(onClick = {}) {
                Icon(painterResource(Res.drawable.igtv), null)
            }
            IconButton(onClick = {}) {
                Icon(painterResource(Res.drawable.messanger), null)
            }

        }
    })

}

@Composable
fun FeedBottomBar() {
    BottomNavigation(backgroundColor = Color.Transparent, elevation = 0.dp) {
        BottomNavigationItem(selected = true, icon = {
            Icon(painterResource(Res.drawable.home_filled), null)
        }, onClick = {})
        BottomNavigationItem(selected = false, icon = {
            Icon(painterResource(Res.drawable.search), null)
        }, onClick = {})
        BottomNavigationItem(selected = false, icon = {
            Icon(painterResource(Res.drawable.story), null)
        }, onClick = {})
        BottomNavigationItem(selected = false, icon = {
            Icon(painterResource(Res.drawable.heart), null)
        }, onClick = {})
    }
}
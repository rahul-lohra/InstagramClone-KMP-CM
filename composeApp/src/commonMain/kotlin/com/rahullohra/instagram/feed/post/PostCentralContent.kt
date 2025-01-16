package com.rahullohra.instagram.feed.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import instagramclone.composeapp.generated.resources.Res
import instagramclone.composeapp.generated.resources.comment
import instagramclone.composeapp.generated.resources.heart
import instagramclone.composeapp.generated.resources.messanger
import instagramclone.composeapp.generated.resources.round_rectangle
import instagramclone.composeapp.generated.resources.save
import org.jetbrains.compose.resources.painterResource

@Composable
fun PostCentralContent() {
    Column {
        val pagerState = rememberPagerState(pageCount = {
            4
        })
        Box {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.height(375.dp)
                    .fillMaxWidth()
            ) { page ->
                AsyncImage(model = "https://picsum.photos/200/300", "hello",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.FillBounds)
            }

            PostIndexIndicator(
                Modifier
                    .wrapContentHeight()
                    .align(Alignment.TopEnd)
                    .padding(14.dp), pagerState
            )
        }

        Box {
            BottomIndicator(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 8.dp), pagerState
            )

            LikesCommentRow(Modifier.align(Alignment.BottomCenter))
        }

    }
}

@Composable
private fun PostIndexIndicator(modifier: Modifier, pagerState: PagerState) {
    val index = pagerState.currentPage + 1
    val total = pagerState.pageCount
    Box(modifier) {
        Icon(
            painterResource(Res.drawable.round_rectangle), null,
            modifier = Modifier.width(36.dp).height(24.dp),
            tint = Color(0xB3121212)
        )
        Text(
            "$index/$total", fontSize = 12.sp,
            lineHeight = 14.sp,
            color = Color(0xFFF9F9F9),
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}

@Composable
private fun BottomIndicator(modifier: Modifier, pagerState: PagerState) {
    Row(modifier, horizontalArrangement = Arrangement.Center) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) {
                Color(0xFF3897F0)
            } else {
                if (MaterialTheme.colors.isLight) {
                    Color(0x26F000000)
                } else {
                    Color(0x54FFFFFF)
                }

            }

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(6.dp)
            )
        }
    }
}

@Composable
private fun LikesCommentRow(modifier: Modifier) {
    Box(modifier.fillMaxWidth().padding(start = 14.dp, end = 14.dp, top = 13.dp)) {
        Row {
            Icon(
                painterResource(Res.drawable.heart),
                null,
                modifier = Modifier.width(24.dp).height(21.dp)
            )
            Spacer(Modifier.padding(end = 17.dp))
            Icon(
                painterResource(Res.drawable.comment), null,
                modifier = Modifier.width(22.dp).height(23.dp)
            )
            Spacer(Modifier.padding(end = 17.dp))
            Icon(
                painterResource(Res.drawable.messanger), null,
                modifier = Modifier.width(23.dp).height(20.dp)
            )
        }
        Icon(
            painterResource(Res.drawable.save),
            null,
            modifier = Modifier.align(Alignment.CenterEnd).width(21.dp).height(24.dp)
        )
    }

}


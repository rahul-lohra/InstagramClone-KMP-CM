package com.rahullohra.instagram.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahullohra.instagram.ColouredCircle
import com.rahullohra.instagram.UserIcon
import instagramclone.composeapp.generated.resources.Res
import instagramclone.composeapp.generated.resources.man_user_circle_icon
import instagramclone.composeapp.generated.resources.rectangle
import org.jetbrains.compose.resources.painterResource

@Composable
fun StoryUi() {
    Column {
        Divider()
        val lazyColumnListState = rememberLazyListState()
        val itemsList = (0..5).toList()
        val itemsIndexedList = listOf("Your Story", "karenne", "zackjohn", "kieron_d", "crag_love", "John", "Wick")
        LazyRow(state = lazyColumnListState) {
            itemsIndexed(itemsIndexedList) { index, item ->
                StoryListUi(index, item)
            }
        }
        Divider()
    }
}

@Composable
fun StoryListUi(index:Int, item:String) {
    val circleDimension = 63.dp
    val innerCircleDimension = circleDimension - 4.dp
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 9.dp, horizontal = 10.dp)) {
        Box(modifier = Modifier.width(circleDimension).height(circleDimension),
            contentAlignment = Alignment.Center) {
            ColouredCircle(circleDimension)
            UserIcon(Modifier.width(innerCircleDimension).height(innerCircleDimension))

            if(index == 1)
                LiveBadge(Modifier.offset(y = (30).dp))
        }

        Text(item, fontSize = 12.sp , modifier = Modifier.padding(top = 4.dp))
    }
}

@Composable
fun LiveBadge(modifier: Modifier) {
    Box(modifier) {
        Image(painterResource(Res.drawable.rectangle), null)
        Text(
            "LIVE", fontSize = 8.sp,
            color = Color(0xFFFEFEFE),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.offset(x = 6.dp, y = 4.dp),
            style = TextStyle(),
        )
    }
}
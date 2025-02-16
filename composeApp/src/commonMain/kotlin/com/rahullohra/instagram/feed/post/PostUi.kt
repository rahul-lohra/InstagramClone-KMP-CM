package com.rahullohra.instagram.feed.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahullohra.instagram.UserIcon
import com.rahullohra.instagram.feed.FeedResponseItem
import instagramclone.composeapp.generated.resources.Res
import instagramclone.composeapp.generated.resources.more_icon
import instagramclone.composeapp.generated.resources.official_icon
import org.jetbrains.compose.resources.painterResource

@Composable
fun PostListUi(feedResponseItem: FeedResponseItem) {
    with(feedResponseItem) {
        Column {
            Box(modifier = Modifier.padding(start = 10.dp, top = 11.dp, bottom = 11.dp, end = 15.dp)) {
                PostTopBar(userName, postLocation, isUserVerified)
            }
            PostCentralContent()
            PostBottomBar(Modifier.padding(start = 15.dp, end = 15.dp, top = 14.dp),
                likesCount, userName, postDescription)
        }
    }
}

@Composable
fun PostTopBar(username: String, location: String, isVerified: Boolean) {
    Box(Modifier.fillMaxWidth()) {
        Row() {
            UserIcon(Modifier.size(32.dp))
            Column(Modifier.padding(start = 10.dp)) {
                Row (verticalAlignment = Alignment.CenterVertically){
                    UserNameText(username)
                    if (isVerified) {
                        Icon(
                            painterResource(Res.drawable.official_icon),
                            null,
                            tint = Color(0xFF3897F0),
                            modifier = Modifier.width(18.dp)
                                .padding(start = 4.dp)

                        )
                    }
                }
                Text(
                    style = TextStyle(lineHeight = 13.sp),
                    text = location, fontSize = 13.sp
                )
            }
        }
        Icon(
            painterResource(Res.drawable.more_icon),
            null,
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier.align(Alignment.CenterEnd)

        )
    }
}

@Composable
private fun UserNameText(username: String){
    Text(username,
        style = TextStyle(lineHeight = 18.sp),
        fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
}

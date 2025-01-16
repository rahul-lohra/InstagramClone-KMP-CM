package com.rahullohra.instagram.feed.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rahullohra.instagram.UserIcon


@Composable
fun PostBottomBar(modifier: Modifier, othersLikeCountText: String, username: String, comment: String) {
    Column(modifier) {
        Row {
            UserIcon(Modifier.size(17.dp).align(Alignment.CenterVertically))
            Spacer(Modifier.width(6.dp))
            LikedByText(username, othersLikeCountText)
        }
        Spacer(Modifier.height(5.dp))
        Comment(username, comment)
    }


}

@Composable
private fun LikedByText(username: String, othersLikeCountText: String) {
    Row {

        Text(text = buildAnnotatedString {

            withStyle(SpanStyle(
                fontSize = 13.sp)){
                append("Liked by ")
            }

            withStyle(SpanStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold)){
                append(username)
            }

            withStyle(SpanStyle(fontSize = 13.sp,)){
                append(" and")
            }

            withStyle(SpanStyle(fontSize = 13.sp,fontWeight = FontWeight.SemiBold)){
                append(" $othersLikeCountText others")
            }
        })
    }
}

@Composable
private fun Comment(username:String, comment:String) {
    Row {
        Text(text = buildAnnotatedString {
            withStyle(SpanStyle(
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold)){
                append(username)
            }

            withStyle(SpanStyle(fontSize = 13.sp,)){
                append(" $comment")
            }
        })
    }
}

@Composable
private fun UserNameText(username: String){
    Text(username,
        style = TextStyle(lineHeight = 18.sp),
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp)
}
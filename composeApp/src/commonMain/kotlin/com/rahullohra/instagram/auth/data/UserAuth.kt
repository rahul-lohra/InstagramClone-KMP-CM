package com.rahullohra.instagram.auth.data

import kotlinx.serialization.Serializable

@Serializable
data class UserAuth (val token:String, val refreshToken:String)
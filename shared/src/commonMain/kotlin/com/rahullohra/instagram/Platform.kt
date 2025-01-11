package com.rahullohra.instagram

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
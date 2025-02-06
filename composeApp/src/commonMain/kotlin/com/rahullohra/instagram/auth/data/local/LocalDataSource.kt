package com.rahullohra.instagram.auth.data.local

interface LocalDataSource {
    fun storeCredentials(token: String)
}
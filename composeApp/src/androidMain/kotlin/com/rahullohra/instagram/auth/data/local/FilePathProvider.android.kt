package com.rahullohra.instagram.auth.data.local

import com.rahullohra.instagram.IgAndroidApp

actual fun getFilePath(fileName:String): String {
    return IgAndroidApp.INSTANCE
        .filesDir.resolve(fileName).absolutePath
}
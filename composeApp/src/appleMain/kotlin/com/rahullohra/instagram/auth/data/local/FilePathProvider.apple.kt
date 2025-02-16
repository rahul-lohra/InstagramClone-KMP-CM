package com.rahullohra.instagram.auth.data.local

import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual fun getFilePath(fileName:String): String {
    val paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true)
    val directory = paths.firstOrNull() as? String ?: throw IllegalStateException("Unable to find document directory")
    return "$directory/$fileName.json" // Append "auth.json" to the directory path
}

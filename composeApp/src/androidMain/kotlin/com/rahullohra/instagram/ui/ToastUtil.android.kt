package com.rahullohra.instagram.ui

import android.widget.Toast
import com.rahullohra.instagram.IgAndroidApp

actual fun showToast(message: String) {
    Toast.makeText(IgAndroidApp.INSTANCE, message, Toast.LENGTH_SHORT).show()
}
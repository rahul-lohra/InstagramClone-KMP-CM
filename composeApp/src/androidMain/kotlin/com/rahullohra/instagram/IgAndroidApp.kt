package com.rahullohra.instagram

import android.app.Application

class IgAndroidApp: Application() {

    companion object {
        lateinit var INSTANCE: IgAndroidApp
    }
    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}
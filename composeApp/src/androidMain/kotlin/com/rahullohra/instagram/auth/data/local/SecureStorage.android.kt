package com.rahullohra.instagram.auth.data.local

import android.content.Context
import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.CoroutineScope
import java.io.File

//actual fun dataStorePreferences(
//    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>?,
//    coroutineScope: CoroutineScope,
//    migrations: List<DataMigration<Preferences>>,
//    context: Any?,
//): DataStore<Preferences> = createDataStore(
//    corruptionHandler = corruptionHandler,
//    coroutineScope = coroutineScope,
//    migrations = migrations,
//    path = { mCtx ->
//        if(mCtx == null)
//            throw IllegalStateException("You must provide context for Android")
//        else File((mCtx as Context).filesDir, "datastore/${AUTH_PREFERENCE_DATASTORE}").path
//    }
//)

fun createDataStore(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(AUTH_PREFERENCE_DATASTORE).absolutePath }
)
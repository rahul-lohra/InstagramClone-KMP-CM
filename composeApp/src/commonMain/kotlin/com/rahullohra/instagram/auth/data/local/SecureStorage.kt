package com.rahullohra.instagram.auth.data.local

import androidx.datastore.core.DataMigration
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import okio.Path.Companion.toPath

suspend fun hello(){
     val EXAMPLE_KEY = stringPreferencesKey("example_key")

    val ds = createDataStore { "" }
    ds.edit { it->
        it[EXAMPLE_KEY] = "1"
    }

}

fun createDataStore(producePath: () -> String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { producePath().toPath() }
    )

const val AUTH_PREFERENCE_DATASTORE = "auth.preferences_pb"

internal fun createDataStore(
    corruptionHandler: ReplaceFileCorruptionHandler<Preferences>? = null,
    coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
    migrations: List<DataMigration<Preferences>>,
    context: Any? = null,
    path: (context: Any?) -> String,
) = PreferenceDataStoreFactory.createWithPath(
    corruptionHandler = corruptionHandler,
    scope = coroutineScope,
    migrations = migrations,
    produceFile = {
        path(context).toPath()
    }
)

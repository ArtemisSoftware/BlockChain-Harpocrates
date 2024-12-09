package com.artemissoftware.blockchainharpocrates.util.extensions

import android.content.Context
import androidx.datastore.dataStore
import com.artemissoftware.blockchainharpocrates.datastore.serializer.AppSettingsStoreSerializer

val Context.appSettingsStore by dataStore(
    fileName = "app-settings-store.json",
    serializer = AppSettingsStoreSerializer(),
)
package com.artemissoftware.blockchainharpocrates.datastore.serializer

import androidx.datastore.core.Serializer
import com.artemissoftware.blockchainharpocrates.datastore.models.AppSettingsStore
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class AppSettingsStoreSerializer : Serializer<AppSettingsStore> {

    override val defaultValue: AppSettingsStore
        get() = AppSettingsStore()

    override suspend fun readFrom(input: InputStream): AppSettingsStore {
        return try {
            Json.decodeFromString(
                deserializer = AppSettingsStore.serializer(),
                string = input.readBytes().decodeToString(),
            )
        } catch (e: SerializationException) {
            defaultValue
        }
    }

    override suspend fun writeTo(t: AppSettingsStore, output: OutputStream) {
        val bytes = Json.encodeToString(
            serializer = AppSettingsStore.serializer(),
            value = t,
        )

        output.write(bytes.encodeToByteArray())
    }
}
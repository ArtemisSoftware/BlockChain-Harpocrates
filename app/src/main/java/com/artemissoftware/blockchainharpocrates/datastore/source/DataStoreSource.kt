package com.artemissoftware.blockchainharpocrates.datastore.source

import android.content.Context
import com.artemissoftware.blockchainharpocrates.datastore.models.AppSettingsStore
import com.artemissoftware.blockchainharpocrates.util.extensions.appSettingsStore
import kotlinx.coroutines.flow.Flow

class DataStoreSource (private val context: Context) {

    fun getAppSettings(): Flow<AppSettingsStore> {
        return context.appSettingsStore.data
    }

    suspend fun setProofOfWork(proofOfWork: Int) {
        context.appSettingsStore.updateData {
            it.copy(proofOfWork = proofOfWork)
        }
    }
}
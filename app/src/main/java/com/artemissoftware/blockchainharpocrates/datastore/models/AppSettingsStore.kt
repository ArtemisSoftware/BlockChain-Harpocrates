package com.artemissoftware.blockchainharpocrates.datastore.models

import kotlinx.serialization.Serializable

@Serializable
data class AppSettingsStore(
    val proofOfWork: Int = 2
)

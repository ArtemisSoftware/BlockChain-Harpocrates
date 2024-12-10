package com.artemissoftware.blockchainharpocrates

import com.artemissoftware.blockchainharpocrates.models.Block

data class BlockchainState(
    val difficulty: Int = 0,
    val blocks: List<Block> = emptyList(),
    val showDialog: Boolean = false,
    val isLoading: Boolean = false,
    val message: String = "",
    val pow: Int = 0
)

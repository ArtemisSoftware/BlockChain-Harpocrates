package com.artemissoftware.blockchainharpocrates

sealed class BlockchainEvent {
    data object CloseDialog : BlockchainEvent()
    data object OpenDialog : BlockchainEvent()
    data class UpdatePoW(val value: String) : BlockchainEvent()
    data object SavePoW : BlockchainEvent()
    data class UpdateMessage(val value: String) : BlockchainEvent()
    data object UpdateBlockChain : BlockchainEvent()
}
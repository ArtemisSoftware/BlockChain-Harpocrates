package com.artemissoftware.blockchainharpocrates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.artemissoftware.blockchainharpocrates.repository.BlockChainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BlockchainViewModel(
    private val repository: BlockChainRepository
): ViewModel() {

    private val _state = MutableStateFlow(BlockchainState())
    val state: StateFlow<BlockchainState> = _state.asStateFlow()

    init {
        startChain()
        getChain()
    }

    private fun getChain() = with(_state){
        viewModelScope.launch {
            repository.blockChainFlow.collectLatest { blocks ->
                update {
                    it.copy(blocks = blocks.reversed())
                }
            }
        }
    }

    private fun startChain() = with(_state){
        viewModelScope.launch {
            repository.startChain()
        }
    }

    fun onTriggerEvent(event: BlockchainEvent){
        when(event){
            BlockchainEvent.CloseDialog -> TODO()
            BlockchainEvent.OpenDialog -> openDialog()
            BlockchainEvent.SavePoW -> savePow()
            BlockchainEvent.UpdateBlockChain -> updateBlockChain()
            is BlockchainEvent.UpdateMessage -> updateMessage(event.value)
            is BlockchainEvent.UpdatePoW -> updatePow(event.value)
        }
    }

    private fun updateBlockChain() = with(_state.value){
        loading(true)
        viewModelScope.launch {
            repository.updateBlockChain(message)
            loading(false)
        }
    }

    private fun updateMessage(value: String) = with(_state){
        update {
            it.copy(message = value)
        }
    }

    private fun savePow() = with(_state.value){
        viewModelScope.launch {
            repository.updateDifficulty(pow.toInt())
            _state.update {
                it.copy(showDialog = false)
            }
        }
    }

    private fun updatePow(value: String) = with(_state){
        update {
            it.copy(pow = value)
        }
    }

    private fun openDialog() = with(_state){
        update {
            it.copy(showDialog = true)
        }
    }

    private fun loading(isLoading: Boolean) = with(_state){
        update {
            it.copy(isLoading = isLoading)
        }
    }
}
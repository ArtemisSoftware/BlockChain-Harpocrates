package com.artemissoftware.blockchainharpocrates.repository

import com.artemissoftware.blockchainharpocrates.datastore.source.DataStoreSource
import com.artemissoftware.blockchainharpocrates.models.Block
import com.artemissoftware.blockchainharpocrates.util.HashUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext

class BlockChainRepository (
    private val dataSource: DataStoreSource
) {

    private val blocks: MutableList<Block> = mutableListOf()
    private var difficulty = 0;
    private val _blockChainFlow = MutableStateFlow<List<Block>>(emptyList())
    val blockChainFlow: StateFlow<List<Block>> get() = _blockChainFlow.asStateFlow()


    suspend fun startChain(){
        dataSource.getAppSettings().collectLatest {
            difficulty = it.proofOfWork
            if(blocks.isEmpty()) {
                val block = Block(data = "Genesis Block")
                block.mineBlock(difficulty)
                blocks.add(block)
                _blockChainFlow.value = blocks.toList()
            }
        }
    }

    suspend fun updateBlockChain(message: String) = withContext(Dispatchers.IO) {
        addBlock(message)
        if(isBlockChainValid) {
            _blockChainFlow.value = blocks.toList()
        }
    }


    suspend fun updateDifficulty(pow: Int) {
        dataSource.setProofOfWork(pow)
    }

    private fun addBlock(data: String) = with(blocks.last()) {
        val block = Block(
            index = index + 1,
            previousHash = hash,
            data = data
        )
        block.mineBlock(difficulty)
        blocks.add(block)
    }

    private val isBlockChainValid: Boolean
        get() {
            if (!isFirstBlockValid) {
                return false
            }
            for (i in 1 until blocks.size) {
                val currentBlock = blocks[i]
                val previousBlock = blocks[i - 1]
                if (!isValidNewBlock(currentBlock, previousBlock)) return false
            }
            return true
        }

    private val isFirstBlockValid: Boolean
        get() {
            val firstBlock = blocks[0]
            return when {
                (firstBlock.index != 0) -> {
                    return false
                }
                (firstBlock.previousHash != null) -> {
                    return false
                }
                else -> firstBlock.hash != null && HashUtil.calculate(text = firstBlock.str()) == firstBlock.hash
            }
        }

    private fun isValidNewBlock(newBlock: Block, previousBlock: Block): Boolean {
        return when{
            (previousBlock.index + 1 != newBlock.index) -> {
                false
            }
            (newBlock.previousHash == null) -> {
                false
            }
            else -> newBlock.hash != null && HashUtil.calculate(text = newBlock.str()) == newBlock.hash
        }
    }

}
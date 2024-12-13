package com.artemissoftware.blockchainharpocrates.models

import com.artemissoftware.blockchainharpocrates.util.HashUtil

data class Block(
    val index: Int = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val previousHash: String? = null,
    val data: String
) {
    private var nonce: Int = 0
    var hash: String? = null

    init {
        hash = HashUtil.calculate(text = str())
    }

    fun str(): String {
        return (index + timestamp).toString() + previousHash + data + nonce
    }

    fun mineBlock(difficulty: Int) {
        nonce = 0
        while (hash!!.substring(0, difficulty) != addZeros(difficulty)) {
            nonce++
            hash = HashUtil.calculate(text = str())
        }
    }

    private fun addZeros(difficulty: Int): String {
        val builder = StringBuilder()
        for (i in 0 until difficulty) {
            builder.append('0')
        }
        return builder.toString()
    }
}

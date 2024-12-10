package com.artemissoftware.blockchainharpocrates.models

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

data class Block(
    val index: Int,
    val timeStamp: Long,
    val previousHash: String? = null,
    val data: String
){
    private var nonce: Int
    var hash: String?

    init {
        nonce = 0
        hash = calculateHash(this)
    }

    private fun str() = index.toString() + timeStamp.toString() + previousHash + data + nonce

    fun mineBlock(difficulty: Int){
        hash?.let {
            nonce = 0
            while (!it.substring(0, difficulty).equals(addZeros(difficulty))){
                ++nonce
                hash = calculateHash(this)
            }
        }
    }

    private fun addZeros(difficulty: Int): String{
        val builder = StringBuilder()
        repeat(difficulty) {
            builder.append('0')
        }
        return builder.toString()
    }

    private companion object {
        fun calculateHash(block: Block? = null): String?{
            block?.let {

                try{
                    val messageDigest = MessageDigest.getInstance("SHA-256")
                    val bytes = messageDigest.digest(it.str().toByteArray())
                    val builder = StringBuilder()

                    bytes.forEach { byte ->
                        val hex = Integer.toHexString(0xff and byte.toInt())
                        if(hex.length == 1){
                            builder.append('0')
                        }
                        builder.append(hex)
                    }
                    return builder.toString()
                } catch (ex: NoSuchAlgorithmException){
                    return null
                }
            } ?: run {
                return null
            }
        }
    }
}

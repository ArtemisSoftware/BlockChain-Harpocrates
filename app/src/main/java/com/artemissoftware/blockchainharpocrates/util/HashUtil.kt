package com.artemissoftware.blockchainharpocrates.util

import java.security.MessageDigest

object HashUtil {

    const val SHA_256 = "SHA-256"

    fun calculate(
        algorithm: String = SHA_256,
        text: String
    ): String {

        val builder = StringBuilder()

        val messageDigest: MessageDigest = MessageDigest.getInstance(algorithm)
        val bytes = messageDigest.digest(text.toByteArray())

        for (b in bytes) {
            val hex = Integer.toHexString(0xff and b.toInt())
            if (hex.length == 1) {
                builder.append('0')
            }
            builder.append(hex)
        }
        return builder.toString()
    }
}

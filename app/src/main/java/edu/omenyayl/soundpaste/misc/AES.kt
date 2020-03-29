package edu.omenyayl.soundpaste.misc

import android.util.Base64
import javax.crypto.*
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AES {
    companion object {
        private val KEY_SIZE = 128

        /**
         * @param data The data to encrypt
         * @return Pair<A base64 representation of the IV + encrypted data><Raw key>
         */
        fun encrypt (data: String): Pair<String, ByteArray> {
            val keyGenerator = KeyGenerator.getInstance("AES")
            keyGenerator.init(KEY_SIZE)
            val key = keyGenerator.generateKey()
            val plaintext: ByteArray = data.toByteArray()
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val cipherText = cipher.doFinal(plaintext)
            val ivCipherText = ByteArray(cipher.iv.size + cipherText.size
            ) { i -> if (i < cipher.iv.size) cipher.iv[i] else cipherText[i - cipher.iv.size] }
            return Pair(Base64.encodeToString(ivCipherText, Base64.DEFAULT), key.encoded)
        }

        @ExperimentalStdlibApi
        fun decrypt (cipherText: ByteArray, keyBytes: ByteArray, ivBytes: ByteArray): String {
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            val key = SecretKeySpec(
                keyBytes,
                "AES"
            )
            val iv = IvParameterSpec(ivBytes)
            cipher.init(Cipher.DECRYPT_MODE, key, iv)
            val plaintextBytes = cipher.doFinal(cipherText)
            return plaintextBytes.decodeToString()
        }

    }
}
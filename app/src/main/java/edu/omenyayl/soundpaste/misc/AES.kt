package edu.omenyayl.soundpaste.misc

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class AES {
    companion object {

        /**
         * @param data The data to encrypt
         * @return A base64 representation of the encrypted data (utf-8)
         */
        fun encrypt (data: String, key: SecretKey): String {
            val plaintext: ByteArray = data.toByteArray(Charsets.UTF_8)
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, key)
            val cipherText = cipher.doFinal(plaintext)
            return Base64.encodeToString(cipherText, Base64.DEFAULT)
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
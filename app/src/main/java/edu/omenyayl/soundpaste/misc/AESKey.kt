package edu.omenyayl.soundpaste.misc

import android.util.Base64
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class AESKey {
    lateinit var key: SecretKey

    constructor() {
        val keygen = KeyGenerator.getInstance("AES")
        this.key = keygen.generateKey()
    }

    fun getKeyBase64(): String {
        return Base64.encodeToString(this.key.encoded, Base64.DEFAULT)
    }

}
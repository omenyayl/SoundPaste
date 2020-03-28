package edu.omenyayl.soundpaste.misc

import android.util.Base64
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.SecretKeySpec

@ExperimentalStdlibApi
class AESTest {

    @Before
    fun setUp() {
    }

    @Test
    fun testDecrypt1() {
        val cipherTextBytes = Base64.decode("oDqbhyLaLCJRnfah1mdR9A==", Base64.DEFAULT)
        val keyBytes = Base64.decode("aSUmZDquFkdiWd+uC4GBeg==", Base64.DEFAULT)
        val ivBytes =  Base64.decode("YMZblNuDOkyujlTxyKmbcg==", Base64.DEFAULT)
        val plaintext = AES.decrypt(cipherTextBytes, keyBytes, ivBytes)
        assertEquals("hello", plaintext)
    }

    @Test
    fun testDecrypt2() {
        val ivCipherTextBytes = Base64.decode("Aq1upDOsolcVqKwfBJGobsyWRnraxvI1fTDZFIAh5cQ=", Base64.DEFAULT)
        val iv = ivCipherTextBytes.slice(0..15).toByteArray()
        val cipherText = ivCipherTextBytes.slice(IntRange(16, ivCipherTextBytes.size - 1)).toByteArray()
        val keyBytes = Base64.decode("kWnufE2UP9xopB4cbuN9lQ==", Base64.DEFAULT)
        val plaintext = AES.decrypt(cipherText, keyBytes, iv)
        assertEquals("hello", plaintext)
    }
}
package edu.omenyayl.soundpaste.models

/**
 * Model that represents the text snippet to transfer via sound
 */
class Snippet(private val text: String) {
    fun getText(): String {
        return this.text
    }
}
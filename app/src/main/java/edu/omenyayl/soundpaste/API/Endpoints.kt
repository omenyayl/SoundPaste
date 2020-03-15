package edu.omenyayl.soundpaste.API

object Endpoints {
    const val baseURL = "https://olegs-tech.space:4201"

    fun postSnippetEndpoint(): String {
        return "${baseURL}/snippets"
//        return "http://192.168.1.3:8080/snippets"
    }

    fun getSnippetEndpoint(id: Long): String {
        return "${baseURL}/snippets/${id}"
//        return "http://192.168.1.3:8080/snippets"
    }
}
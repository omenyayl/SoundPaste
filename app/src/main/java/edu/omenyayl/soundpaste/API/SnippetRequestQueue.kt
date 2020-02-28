package edu.omenyayl.soundpaste.API

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object SnippetRequestQueue {

    private lateinit var requestQueue: RequestQueue

    fun getRequestQueue(context: Context): RequestQueue {
        if (this::requestQueue.isInitialized) {
            return requestQueue
        } else {
            this.requestQueue = Volley.newRequestQueue(context)
        }
        return this.requestQueue
    }
}
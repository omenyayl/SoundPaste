package edu.omenyayl.soundpaste.API

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class SnippetAPI {
    companion object {
        /**
         * @param result The result will be posted to this object
         * @param context Android context
         * @param snippet The snippet to post
         */
        fun postSnippet(result: MutableLiveData<String>,
                        context: Context,
                        snippet: String) {
            val requestQueue = SnippetRequestQueue.getRequestQueue(context)
            val url = Endpoints.postSnippetEndpoint()

            val onResultListener = Response.Listener<String> {
                Log.d(::SnippetAPI.name, "Response: $it")
                result.postValue(it)
            }

            val onErrorListener = Response.ErrorListener {
                if (it != null ) {
                    Log.e(::SnippetAPI.name, "Error: $it")
                }
            }

            val stringRequest = object: StringRequest(
                Method.POST,
                url,
                onResultListener,
                onErrorListener
            ) {
                override fun getBodyContentType(): String {
                    return "text/plain"
                }

                override fun getBody(): ByteArray {
                    return snippet.toByteArray(Charsets.UTF_8)
                }
            }
            requestQueue.add(stringRequest)
        }
    }
}
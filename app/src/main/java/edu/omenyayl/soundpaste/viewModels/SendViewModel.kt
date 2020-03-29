package edu.omenyayl.soundpaste.viewModels

import android.content.Context
import androidx.lifecycle.*
import edu.omenyayl.soundpaste.API.SnippetAPI
import edu.omenyayl.soundpaste.repositories.SnippetRepository

/**
 * ViewModel that handles sending messages
 */
class SendViewModel : ViewModel() {

    /**
     * @return observable for the snippet text
     */
    fun getSnippetText(): LiveData<String> {
        return SnippetRepository.snippetText
    }

    /**
     * Call this when the snippet text is changed
     */
    fun onSnippetTextChanged(text: String) {
        SnippetRepository.snippetText.postValue(text)
    }

    fun uploadData(snippet: String, context: Context, lifecycleOwner: LifecycleOwner): LiveData<Int> {
        val liveDataID = MutableLiveData<Int>()
        val liveDataStringID = MutableLiveData<String>()
        SnippetAPI.postSnippet(
            liveDataStringID,
            context,
            snippet
        )
        liveDataStringID.observe(lifecycleOwner, Observer {
            if (it != null) {
                liveDataID.postValue(it.toInt())
            }
        })
        return liveDataID
    }

}

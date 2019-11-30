package edu.omenyayl.soundpaste.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
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

}

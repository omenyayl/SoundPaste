package edu.omenyayl.soundpaste.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import edu.omenyayl.soundpaste.repositories.SnippetRepository

/**
 * ViewModel that handles sending messages
 */
class SendViewModel : ViewModel() {

    fun getSnippetText(): LiveData<String> {
        return SnippetRepository.snippetText
    }

    fun onSnippetTextChanged(text: String) {
        SnippetRepository.snippetText.postValue(text)
    }

}

package edu.omenyayl.soundpaste.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import edu.omenyayl.soundpaste.models.Snippet
import edu.omenyayl.soundpaste.repositories.SnippetRepository

/**
 * ViewModel that handles receiving messages
 */
class ReceiveViewModel : ViewModel() {

    fun getSnippetList(): LiveData<MutableList<Snippet>> {
        return SnippetRepository.snippetList
    }

    fun onReceiveMessage(message: String) {
        val snippets = SnippetRepository.snippetList.value
        snippets?.add(Snippet(message))
        SnippetRepository.snippetList.postValue(snippets)
    }

}

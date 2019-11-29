package edu.omenyayl.soundpaste.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.omenyayl.soundpaste.models.Snippet
import edu.omenyayl.soundpaste.repositories.SnippetRepository

/**
 * ViewModel that handles receiving messages
 */
class ReceiveViewModel : ViewModel() {
    private val liveSnippetList: MutableLiveData<List<Snippet>> = MutableLiveData()

    init {
        this.liveSnippetList.postValue(SnippetRepository.snippetList)
    }


    fun getSnippetList(): LiveData<List<Snippet>> {
        return this.liveSnippetList
    }

}

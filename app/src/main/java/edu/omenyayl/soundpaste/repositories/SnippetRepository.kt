package edu.omenyayl.soundpaste.repositories

import androidx.lifecycle.MutableLiveData
import edu.omenyayl.soundpaste.models.Snippet

object SnippetRepository {
    val snippetList: MutableLiveData<MutableList<Snippet>> = MutableLiveData()
    var snippetText: MutableLiveData<String> = MutableLiveData()

    init {
        val snippets = ArrayList<Snippet>()
        snippetList.value = snippets
    }

}
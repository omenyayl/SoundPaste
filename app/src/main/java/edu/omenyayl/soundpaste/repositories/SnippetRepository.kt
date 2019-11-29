package edu.omenyayl.soundpaste.repositories

import androidx.lifecycle.MutableLiveData
import edu.omenyayl.soundpaste.models.Snippet

object SnippetRepository {
    val snippetList: MutableLiveData<List<Snippet>> = MutableLiveData()
    var snippetText: MutableLiveData<String> = MutableLiveData()

    init {
        val snippets = ArrayList<Snippet>()
        for (i in 0..25) {
            snippets.add(Snippet("Hello: $i"))
        }
        snippetList.value = snippets
    }

}
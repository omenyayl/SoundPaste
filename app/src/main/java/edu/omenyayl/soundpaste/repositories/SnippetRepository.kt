package edu.omenyayl.soundpaste.repositories

import edu.omenyayl.soundpaste.models.Snippet

object SnippetRepository {
    val snippetList: List<Snippet>

    init {
        this.snippetList = ArrayList()
        for (i in 0..25) {
            this.snippetList.add(Snippet("Hello: ".plus(i)))
        }
    }

}
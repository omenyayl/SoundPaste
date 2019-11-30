package edu.omenyayl.soundpaste.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import edu.omenyayl.soundpaste.R
import edu.omenyayl.soundpaste.models.Snippet
import kotlinx.android.synthetic.main.snippet_item.view.*

/**
 * Adapter for showing a list of snippets
 */
class SnippetListAdapter (private var snippetList: List<Snippet>) : RecyclerView.Adapter<SnippetListAdapter.ViewHolder>() {

    lateinit var onCopyClickListener: ((Snippet) -> Unit)
    lateinit var onShareClickListener: ((Snippet) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.snippet_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val snippet = this.snippetList[position]
        holder.textViewSnippet.text = snippet.text
        if (this::onCopyClickListener.isInitialized) {
            holder.buttonCopy.setOnClickListener { onCopyClickListener(snippet) }
        }
        if (this::onShareClickListener.isInitialized) {
            holder.buttonShare.setOnClickListener { onShareClickListener(snippet) }
        }
    }

    override fun getItemCount(): Int {
        return this.snippetList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSnippet = itemView.textViewSnippet!!
        val buttonCopy = itemView.buttonCopy!!
        val buttonShare = itemView.buttonShare!!
    }
}

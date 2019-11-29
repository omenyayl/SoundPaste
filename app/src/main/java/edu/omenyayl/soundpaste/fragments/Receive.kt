package edu.omenyayl.soundpaste.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import edu.omenyayl.soundpaste.R
import edu.omenyayl.soundpaste.adapters.SnippetListAdapter
import edu.omenyayl.soundpaste.models.Snippet
import edu.omenyayl.soundpaste.viewModels.ReceiveViewModel
import kotlinx.android.synthetic.main.receive_fragment.*

/**
 * Fragment for receiving messages
 */
class Receive : Fragment() {

    companion object {
        fun newInstance() = Receive()
    }

    private lateinit var viewModel: ReceiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.receive_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ReceiveViewModel::class.java)
        viewModel.getSnippetList().observe(this.viewLifecycleOwner, Observer { t -> initRecyclerViewSnippetList(t) })
    }

    private fun onSnippetClick(snippet: Snippet) {
        Toast.makeText(context, snippet.text, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerViewSnippetList(snippetList: List<Snippet>) {
        val adapter = SnippetListAdapter(snippetList)
        adapter.onModelClickListener = this::onSnippetClick
        recyclerViewSnippetList.adapter = adapter
        recyclerViewSnippetList.layoutManager = LinearLayoutManager(context)
        recyclerViewSnippetList.isNestedScrollingEnabled = false
    }

}

package edu.omenyayl.soundpaste.fragments

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import edu.omenyayl.soundpaste.R
import edu.omenyayl.soundpaste.adapters.SnippetListAdapter
import edu.omenyayl.soundpaste.dialogs.ReceiveMessageDialog
import edu.omenyayl.soundpaste.models.Snippet
import edu.omenyayl.soundpaste.viewModels.ReceiveViewModel
import kotlinx.android.synthetic.main.receive_fragment.*

/**
 * Fragment for receiving messages
 */
class Receive : Fragment() {

    private object PermissionCodes {
        const val REQUEST_PERMISSION_RECORD_AUDIO = 1
    }

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

        buttonListen.setOnClickListener { onButtonListenClicked() }
    }

    private fun onButtonListenClicked() {
        if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), PermissionCodes.REQUEST_PERMISSION_RECORD_AUDIO)
        } else {
            startListening()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PermissionCodes.REQUEST_PERMISSION_RECORD_AUDIO -> {
                if (grantResults.isNotEmpty() && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(context, "Microphone permissions required to use this feature.", Toast.LENGTH_LONG).show()
                } else {
                    startListening()
                }
            }
        }
    }

    private fun startListening() {
        val dialog = ReceiveMessageDialog()
        dialog.show(fragmentManager!!, "message")
    }

    private fun onClickSnippetShare(snippet: Snippet) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "SoundPaste")
        shareIntent.putExtra(Intent.EXTRA_TEXT, snippet.text)
        startActivity(shareIntent)
    }

    private fun onClickSnippetCopy(snippet: Snippet) {
        val clipBoardManager: ClipboardManager = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipBoardManager.primaryClip = ClipData.newPlainText("SoundPaste snippet", snippet.text)
        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerViewSnippetList(snippetList: List<Snippet>) {
        val adapter = SnippetListAdapter(snippetList)
        adapter.onCopyClickListener = this::onClickSnippetCopy
        adapter.onShareClickListener = this::onClickSnippetShare
        recyclerViewSnippetList.adapter = adapter
        recyclerViewSnippetList.layoutManager = LinearLayoutManager(context)
        recyclerViewSnippetList.isNestedScrollingEnabled = false
    }

}

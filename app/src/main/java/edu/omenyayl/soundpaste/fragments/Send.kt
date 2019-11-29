package edu.omenyayl.soundpaste.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import edu.omenyayl.soundpaste.R
import edu.omenyayl.soundpaste.viewModels.SendViewModel
import kotlinx.android.synthetic.main.send_fragment.*

/**
 * Fragment for sending messages
 */
class Send : Fragment() {

    companion object {
        fun newInstance() = Send()
    }

    private lateinit var viewModel: SendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.send_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SendViewModel::class.java)
        initEditTextContent(viewModel.getSnippetText().value ?: "")
        buttonClear.setOnClickListener { initEditTextContent(""); viewModel.onSnippetTextChanged("") }
    }

    private fun initEditTextContent(initialText: String) {
        val maxChars = context?.resources?.getInteger(R.integer.maximum_send_text_characters)
        textViewCharacterLimit.text = String.format("%d / %d", 0, maxChars)
        editTextContent.setText(initialText, TextView.BufferType.EDITABLE)
        editTextContent.addTextChangedListener {
            text -> run {
                textViewCharacterLimit.text = String.format("%d / %d", text?.length, maxChars)
                viewModel.onSnippetTextChanged(text.toString())
            }
        }
    }

}

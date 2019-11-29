package edu.omenyayl.soundpaste.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import edu.omenyayl.soundpaste.R
import edu.omenyayl.soundpaste.misc.Constants
import edu.omenyayl.soundpaste.viewModels.SendViewModel
import kotlinx.android.synthetic.main.send_fragment.*
import kotlin.math.max

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
        initEditTextContent()
    }

    private fun initEditTextContent() {
        val maxChars = context?.resources?.getInteger(R.integer.maximum_send_text_characters)
        textViewCharacterLimit.text = String.format("%d / %d", 0, maxChars)
        editTextContent.addTextChangedListener {
                text -> textViewCharacterLimit.text =
            String.format("%d / %d", text?.length, maxChars)
        }
    }

}

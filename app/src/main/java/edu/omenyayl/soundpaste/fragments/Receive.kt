package edu.omenyayl.soundpaste.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import edu.omenyayl.soundpaste.R
import edu.omenyayl.soundpaste.viewModels.ReceiveViewModel

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
        // TODO: Use the ViewModel
    }

}

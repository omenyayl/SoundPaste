package edu.omenyayl.soundpaste.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import edu.omenyayl.soundpaste.R
import edu.omenyayl.soundpaste.misc.Constants
import edu.omenyayl.soundpaste.viewModels.SendViewModel
import io.chirp.chirpsdk.ChirpSDK
import io.chirp.chirpsdk.models.ChirpError
import io.chirp.chirpsdk.models.ChirpErrorCode
import io.chirp.chirpsdk.models.ChirpSDKState
import kotlinx.android.synthetic.main.send_fragment.*

/**
 * Fragment for sending messages
 */
class Send : Fragment() {

    lateinit var chirp: ChirpSDK

    companion object {
        private const val ARGS_INITIAL_MESSAGE = "args_initial_message"
        fun newInstance(message: String): Send {
            val sendFragment = Send()
            val args = Bundle()
            args.putString(ARGS_INITIAL_MESSAGE, message)
            sendFragment.arguments = args
            return sendFragment
        }
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
        initViews()
        initChirpSDK()
    }

    private fun initViews() {
        initEditTextContent(viewModel.getSnippetText().value ?: arguments?.getString(ARGS_INITIAL_MESSAGE) ?: "")
        buttonClear.setOnClickListener { initEditTextContent(""); viewModel.onSnippetTextChanged("") }
        buttonSend.setOnClickListener { onClickButtonSend() }
    }

    private fun onClickButtonSend() {
        val message = editTextContent.text.toString()
        if (message.isEmpty()) {
            Toast.makeText(context, "Message cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            sendChirp(message)
        }
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

    private fun initChirpSDK() {
        chirp = ChirpSDK(context!!, Constants.CHIRP_KEY, Constants.CHIRP_SECRET)
        val error = chirp.setConfig(Constants.CHIRP_CONFIG)
        if (error.code == 0) {
            Log.d("ChirpSDK: ", "Configured ChirpSDK")
        } else {
            Log.e("ChirpError: ", error.message)
        }
    }

    private fun stopChirp() {
        if (!::chirp.isInitialized) return
        if (chirp.getState() > ChirpSDKState.CHIRP_SDK_STATE_STOPPED) {
            val error = chirp.stop()
            if (error.code > 0) {
                Log.e("Chirp", "ChirpSDKError: " + error.message)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        stopChirp()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::chirp.isInitialized) {
            chirp.close()
        }
    }

    private fun sendChirp(message: String) {
        chirp.start(send = true, receive = false)
        val payload: ByteArray = message.toByteArray()

        val error = chirp.send(payload)

        when (error.code) {
            0 -> Toast.makeText(context, "Transmit message", Toast.LENGTH_SHORT).show()
            ChirpErrorCode.CHIRP_SDK_DEVICE_IS_MUTED.code ->
                Toast.makeText(context, "Device is muted, un-mute to transmit", Toast.LENGTH_SHORT).show()
            else -> Log.e("ChirpError: ", error.message)
        }
    }
}

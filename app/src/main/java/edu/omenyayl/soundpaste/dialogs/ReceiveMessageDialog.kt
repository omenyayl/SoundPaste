package edu.omenyayl.soundpaste.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import edu.omenyayl.soundpaste.R
import edu.omenyayl.soundpaste.misc.Constants
import edu.omenyayl.soundpaste.viewModels.ReceiveViewModel
import io.chirp.chirpsdk.ChirpSDK

class ReceiveMessageDialog: DialogFragment() {

    lateinit var chirp: ChirpSDK
    lateinit var viewModel: ReceiveViewModel

    @ExperimentalStdlibApi
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        viewModel = ViewModelProviders.of(this).get(ReceiveViewModel::class.java)

        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dialog_receive, null)
        builder.setView(view)
            .setNegativeButton("Cancel", null)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(true)
        startListening()
        return dialog
    }

    override fun onStop() {
        super.onStop()
        stopListening()
    }

    @ExperimentalStdlibApi
    private fun startListening() {
        chirp = ChirpSDK(context!!, Constants.CHIRP_KEY, Constants.CHIRP_SECRET)
        val error = chirp.setConfig(Constants.CHIRP_CONFIG)
        if (error.code == 0) {
            Log.d("ChirpSDK: ", "Configured ChirpSDK")
        } else {
            Log.e("ChirpError: ", error.message)
        }
        chirp.onReceived { payload, _ -> run {
            val message = payload?.decodeToString()
            if (message != null) onMessageReceived(message)
        } }
        chirp.start(send = false, receive = true)
    }

    private fun onMessageReceived(message: String) {
        viewModel.onReceiveMessage(message)
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        dialog?.dismiss()
    }

    private fun stopListening() {
        chirp.stop()
        chirp.close()
//        Toast.makeText(context, "Stopping", Toast.LENGTH_SHORT).show()
    }
}
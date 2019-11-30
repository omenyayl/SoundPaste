package edu.omenyayl.soundpaste.activities

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import edu.omenyayl.soundpaste.R
import edu.omenyayl.soundpaste.fragments.Receive
import edu.omenyayl.soundpaste.fragments.Send

/**
 * Entry point of the application
 */
class MainActivity : AppCompatActivity() {

    /**
     * Bottom navigation handling
     */
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_send -> {
                viewSendFragment()
                return@OnNavigationItemSelectedListener true
        }
            R.id.navigation_receive -> {
                viewReceiveFragment()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun viewSendFragment() {
        viewSendFragment("")
    }

    /**
     * Start the send fragment
     * @param initialMessage Start the send fragment with the text box pre-filled with this param
     */
    private fun viewSendFragment(initialMessage: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, Send.newInstance(initialMessage))
            .commit()
    }

    /**
     * Start the receive fragment
     */
    private fun viewReceiveFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, Receive.newInstance())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        viewSendFragment() // start the send fragment first

        if (intent?.action == Intent.ACTION_SEND &&
            intent?.type == "text/plain") {
            handleActionSendIntent(intent)
        }
    }

    private fun handleActionSendIntent(intent: Intent) {
        viewSendFragment(intent.getStringExtra(Intent.EXTRA_TEXT))
    }
}

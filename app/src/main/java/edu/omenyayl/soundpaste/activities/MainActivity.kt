package edu.omenyayl.soundpaste.activities

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import edu.omenyayl.soundpaste.R
import edu.omenyayl.soundpaste.fragments.Receive
import edu.omenyayl.soundpaste.fragments.Send

/**
 * Entry point
 */
class MainActivity : AppCompatActivity() {

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
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, Send())
            .commit()
    }

    private fun viewReceiveFragment() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, Receive())
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        viewSendFragment() // start the send fragment first
    }
}

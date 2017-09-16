package com.alderferstudios.handandfootscores

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.method.LinkMovementMethod
import android.view.MenuItem
import android.view.View
import android.widget.TextView

/**
 * About section of Hand and Foot Scores
 *
 * @author Ben Alderfer
 * Alderfer Studios
 */
class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        //set the version text to the current version
        try {
            val pInfo = packageManager.getPackageInfo(packageName, 0)
            val version = pInfo.versionName
            (findViewById<View>(R.id.version) as TextView).text = getString(R.string.version) + " $version"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        //make links clickable
        (findViewById<View>(R.id.opensource) as TextView).movementMethod = LinkMovementMethod.getInstance()
        (findViewById<View>(R.id.translate) as TextView).movementMethod = LinkMovementMethod.getInstance()
        (findViewById<View>(R.id.contact) as TextView).movementMethod = LinkMovementMethod.getInstance()
    }

    /**
     * Only button is back arrow - no need to check
     *
     * @param item - unused
     * @return - unused
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }
}

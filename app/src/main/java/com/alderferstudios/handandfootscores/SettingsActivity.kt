package com.alderferstudios.handandfootscores

import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceFragment
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.MenuItem

/**
 * Hand and Foot Scores
 * Settings activity
 * Lets the user change values for different items
 *
 * @author Ben Alderfer
 * Alderfer Studios
 */
class SettingsActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        private var shared: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null
    }

    /**
     * Creates the activity
     * Checks if portrait or landscape and makes fragments accordingly
     *
     * @param savedInstanceState the previous state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        shared = PreferenceManager.getDefaultSharedPreferences(this)
        editor = shared?.edit()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)
        try {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } catch (e: NullPointerException) {
            Log.e("On Create error", "Failed to set back button")
        }

        //sets default values if the preferences have not yet been used
        PreferenceManager.setDefaultValues(this, R.xml.prefs, false)

        //get and set toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        //lock orientation on phone
        if (!DeviceUtils.isTablet(this)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }

        if (!isLandscape()) {
            fragmentManager.beginTransaction()
                    .replace(R.id.framePort, PortraitFragment()).commit()
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.frame1, LandscapePrefsFragment1()).commit()

            fragmentManager.beginTransaction()
                    .replace(R.id.frame2, LandscapePrefsFragment2()).commit()
        }

        shared?.registerOnSharedPreferenceChangeListener(this)
    }

    /**
     * If the back arrow was clicked on toolbar
     *
     * @param item the item selected (only option is back arrow)
     * @return always true (useless)
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        onBackPressed()
        return true
    }

    /**
     * When a preference changes
     *
     * @param sharedPreferences the Shared Preferences
     * @param key               the preference that was changed
     */
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {

    }

    /**
     * Checks if the device is in landscape
     *
     * @return true if in landscape
     */
    private fun isLandscape(): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    /**
     * The generic preference fragment class
     * Single or dual fragments based on this
     */
    open class PrefFrag : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

        /**
         * Creates the Fragment
         *
         * @param savedInstanceState the previous state
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
        }

        /**
         * Sets the summaries on start
         */
        protected open fun setSummaries() {
            setCleanBookSummary()                                                                //clean book value
            setDirtyBookSummary()                                                                //dirty book value
            setCleanWildBookSummary()                                                            //clean wild book value
            setDirtyWildBookSummary()                                                            //dirty wild book value
            setRedThreeSummary()                                                                 //red three value
            setBlackThreeSummary()                                                               //black three value
            setFourNineSummary()                                                                 //four nine value
            setTenKingSummary()                                                                  //ten king value
            setAceTwoSummary()                                                                   //ace two value
            setJokerSummary()                                                                    //joker value
            setWinSummary()                                                                      //win bonus
        }

        /**
         * Sets the summaries on change
         */
        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            when (key) {
                "cleanBook" -> setCleanBookSummary()
                "dirtyBook" -> setDirtyBookSummary()
                "cleanWildBook" -> setCleanWildBookSummary()
                "dirtyWildBook" -> setDirtyWildBookSummary()
                "redThree" -> setRedThreeSummary()
                "blackThree" -> setBlackThreeSummary()
                "fourNine" -> setFourNineSummary()
                "tenKing" -> setTenKingSummary()
                "aceTwo" -> setAceTwoSummary()
                "joker" -> setJokerSummary()
                "winBonus" -> setWinSummary()
            }
        }

        /**
         * Sets the summary for the clean book value
         * String needs to be acquired differently since its being added
         */
        protected fun setCleanBookSummary() {
            if (isAdded) {                                                                      //must check if the fragment is added to the activity
                val p = findPreference("cleanBook")
                if (p != null) {
                    //make sure value is not ""
                    if (shared?.getString("cleanBook", "500") == "") {
                        editor?.putString("cleanBook", "500")
                        editor?.apply()
                    }

                    p.summary = "$resources.getString(R.string.clean_book_desc) $shared?.getString('cleanBook', '500')"
                }
            }
        }

        /**
         * Sets the summary for the dirty book value
         * String needs to be acquired differently since its being added
         */
        protected fun setDirtyBookSummary() {
            if (isAdded) {                                                                      //must check if the fragment is added to the activity
                val p = findPreference("dirtyBook")
                if (p != null) {
                    //make sure value is not ""
                    if (shared?.getString("dirtyBook", "300") == "") {
                        editor?.putString("dirtyBook", "300")
                        editor?.apply()
                    }

                    p.summary = "$resources.getString(R.string.dirty_book_desc) $shared?.getString('dirtyBook', '300')"
                }
            }
        }

        /**
         * Sets the summary for the clean wild book value
         * String needs to be acquired differently since its being added
         */
        protected fun setCleanWildBookSummary() {
            if (isAdded) {                                                                      //must check if the fragment is added to the activity
                val p = findPreference("cleanWildBook")
                if (p != null) {
                    //make sure value is not ""
                    if (shared?.getString("cleanWildBook", "1500") == "") {
                        editor?.putString("cleanWildBook", "1500")
                        editor?.apply()
                    }

                    p.summary = "$resources.getString(R.string.clean_wild_desc) $shared?.getString('cleanWildBook', '1500')"
                }
            }
        }

        /**
         * Sets the summary for the dirty wild book value
         * String needs to be acquired differently since its being added
         */
        protected fun setDirtyWildBookSummary() {
            if (isAdded) {                                                                      //must check if the fragment is added to the activity
                val p = findPreference("dirtyWildBook")
                if (p != null) {
                    //make sure value is not ""
                    if (shared?.getString("dirtyWildBook", "1300") == "") {
                        editor?.putString("dirtyWildBook", "1300")
                        editor?.apply()
                    }

                    p.summary = "$resources.getString(R.string.dirty_wild_desc) $shared?.getString('dirtyWildBook', '1300')"
                }
            }
        }

        /**
         * Sets the summary for the red three value
         * String needs to be acquired differently since its being added
         */
        protected fun setRedThreeSummary() {
            if (isAdded) {                                                                      //must check if the fragment is added to the activity
                val p = findPreference("redThree")
                if (p != null) {
                    //make sure value is not ""
                    if (shared?.getString("redThree", "300") == "") {
                        editor?.putString("redThree", "300")
                        editor?.apply()
                    }

                    p.summary = "$resources.getString(R.string.red_three_desc) -Math.abs(Integer.parseInt($shared?.getString('redThree', '300')))"
                }
            }
        }

        /**
         * Sets the summary for the black three value
         * String needs to be acquired differently since its being added
         */
        protected fun setBlackThreeSummary() {
            if (isAdded) {                                                                      //must check if the fragment is added to the activity
                val p = findPreference("blackThree")
                if (p != null) {
                    //make sure value is not ""
                    if (shared?.getString("blackThree", "100") == "") {
                        editor?.putString("blackThree", "100")
                        editor?.apply()
                    }

                    p.summary = "$resources.getString(R.string.black_three_desc) -Math.abs(Integer.parseInt($shared?.getString('blackThree', '100')))"
                }
            }
        }

        /**
         * Sets the summary for the 4s-9s value
         * String needs to be acquired differently since its being added
         */
        protected fun setFourNineSummary() {
            if (isAdded) {                                                                      //must check if the fragment is added to the activity
                val p = findPreference("fourNine")
                if (p != null) {
                    //make sure value is not ""
                    if (shared?.getString("fourNine", "5") == "") {
                        editor?.putString("fourNine", "5")
                        editor?.apply()
                    }

                    p.summary = "$resources.getString(R.string.four_nine_desc) -Math.abs(Integer.parseInt($shared?.getString('fourNine', '5')))"
                }
            }
        }

        /**
         * Sets the summary for the 10s-Kings value
         * String needs to be acquired differently since its being added
         */
        protected fun setTenKingSummary() {
            if (isAdded) {                                                                      //must check if the fragment is added to the activity
                val p = findPreference("tenKing")
                if (p != null) {
                    //make sure value is not ""
                    if (shared?.getString("tenKing", "10") == "") {
                        editor?.putString("tenKing", "10")
                        editor?.apply()
                    }

                    p.summary = "$resources.getString(R.string.ten_king_desc) -Math.abs(Integer.parseInt($shared?.getString('tenKing', '10')))"
                }
            }
        }

        /**
         * Sets the summary for the Aces and Twos value
         * String needs to be acquired differently since its being added
         */
        protected fun setAceTwoSummary() {
            if (isAdded) {                                                                      //must check if the fragment is added to the activity
                val p = findPreference("aceTwo")
                if (p != null) {
                    //make sure value is not ""
                    if (shared?.getString("aceTwo", "20") == "") {
                        editor?.putString("aceTwo", "20")
                        editor?.apply()
                    }

                    p.summary = "$resources.getString(R.string.ace_two_desc) -Math.abs(Integer.parseInt($shared?.getString('aceTwo', '20')))"
                }
            }
        }

        /**
         * Sets the summary for the Joker value
         * String needs to be acquired differently since its being added
         */
        protected fun setJokerSummary() {
            if (isAdded) {                                                                      //must check if the fragment is added to the activity
                val p = findPreference("joker")
                if (p != null) {
                    //make sure value is not ""
                    if (shared?.getString("joker", "50") == "") {
                        editor?.putString("joker", "50")
                        editor?.apply()
                    }

                    p.summary = "$resources.getString(R.string.joker_desc) -Math.abs(Integer.parseInt($shared?.getString('joker', '50')))"
                }
            }
        }

        /**
         * Sets the summary for the win value
         * String needs to be acquired differently since its being added
         */
        protected fun setWinSummary() {
            if (isAdded) {                                                                      //must check if the fragment is added to the activity
                val p = findPreference("winBonus")
                if (p != null) {
                    //make sure value is not ""
                    if (shared?.getString("winBonus", "300") == "") {
                        editor?.putString("winBonus", "300")
                        editor?.apply()
                    }

                    p.summary = "$resources.getString(R.string.win_desc) $shared?.getString('winBonus', '300')"
                }
            }
        }
    }

    /**
     * The portrait fragment, contains everything
     */
    class PortraitFragment : PrefFrag() {

        /**
         * Creates the Fragment
         *
         * @param savedInstanceState the previous state
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            PreferenceManager.setDefaultValues(activity, R.xml.prefs, false)
            addPreferencesFromResource(R.xml.prefs)

            setSummaries()
            shared?.registerOnSharedPreferenceChangeListener(this)
        }
    }

    /**
     * Left landscape fragment, contains functionality tweaks
     */
    class LandscapePrefsFragment1 : PrefFrag() {

        /**
         * Creates the Fragment
         *
         * @param savedInstanceState the previous state
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            PreferenceManager.setDefaultValues(activity, R.xml.prefs, false)
            addPreferencesFromResource(R.xml.land_prefs1)

            setSummaries()
            shared?.registerOnSharedPreferenceChangeListener(this)
        }

        /**
         * Sets the summaries on start
         */
        override fun setSummaries() {
            setCleanBookSummary()                                                                //clean book value
            setDirtyBookSummary()                                                                //dirty book value
            setCleanWildBookSummary()                                                            //clean wild book value
            setDirtyWildBookSummary()                                                            //dirty wild book value
            setRedThreeSummary()                                                                 //red three value
            setBlackThreeSummary()                                                               //black three value
        }

        /**
         * Sets the summaries on change
         */
        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            when (key) {
                "cleanBook" -> setCleanBookSummary()
                "dirtyBook" -> setDirtyBookSummary()
                "cleanWildBook" -> setCleanWildBookSummary()
                "dirtyWildBook" -> setDirtyWildBookSummary()
                "redThree" -> setRedThreeSummary()
                "blackThree" -> setBlackThreeSummary()
            }
        }
    }

    /**
     * Right landscape fragment, contains split and design tweaks
     */
    class LandscapePrefsFragment2 : PrefFrag() {

        /**
         * Creates the Fragment
         *
         * @param savedInstanceState the previous state
         */
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            PreferenceManager.setDefaultValues(activity, R.xml.prefs, false)
            addPreferencesFromResource(R.xml.land_prefs2)

            setSummaries()
            shared?.registerOnSharedPreferenceChangeListener(this)
        }

        /**
         * Sets the summaries
         */
        override fun setSummaries() {
            setFourNineSummary()                                                                 //four nine value
            setTenKingSummary()                                                                  //ten king value
            setAceTwoSummary()                                                                   //ace two value
            setJokerSummary()                                                                    //joker value
            setWinSummary()                                                                      //win bonus
        }

        /**
         * When a preference changes
         *
         * @param sharedPreferences the Shared Preferences
         * @param key               the preference that was changed
         */
        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
            when (key) {
                "fourNine" -> setFourNineSummary()
                "tenKing" -> setTenKingSummary()
                "aceTwo" -> setAceTwoSummary()
                "joker" -> setJokerSummary()
                "winBonus" -> setWinSummary()
            }
        }
    }
}

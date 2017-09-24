package com.alderferstudios.handandfootscores

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ScrollView

/**
 * Hand and Foot Scores
 * Main activity
 * Handles navigation, updating scores, clearing, etc...
 * Some things are public for testing
 *
 * @author Ben Alderfer
 * Alderfer Studios
 */
class ScoreActivity : AppCompatActivity() {
    companion object {
        var shared: SharedPreferences? = null
        protected var editor: SharedPreferences.Editor? = null
    }

    var tabNum: Int = 0
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    private var adapter: PagerAdapter? = null

    val editTexts = arrayOfNulls<Array<EditText?>>(4)                                                 //holds all the views, first dimension is tab number

    /**
     * Creates the Activity and gets all the variables/listeners ready
     *
     * @param savedInstanceState the savedInstanceState from before
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        shared = PreferenceManager.getDefaultSharedPreferences(this)
        resetPrefs(this, false)       //resets prefs only first time
        editor = shared?.edit()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //lock orientation on phone
        if (!DeviceUtils.isTablet(this)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }

        //current tab
        tabNum = 0

        //set up tabs
        try {
            tabLayout = findViewById(R.id.tab_layout)
            tabLayout?.addTab(tabLayout?.newTab()!!.setText(getString(R.string.team1) + '\n' + 0))
            tabLayout?.addTab(tabLayout?.newTab()!!.setText(getString(R.string.team2) + '\n' + 0))
            tabLayout?.addTab(tabLayout?.newTab()!!.setText(getString(R.string.team3) + '\n' + 0))
            tabLayout?.addTab(tabLayout?.newTab()!!.setText(getString(R.string.team4) + '\n' + 0))
            tabLayout?.tabGravity = TabLayout.GRAVITY_FILL
        } catch (e: NullPointerException) {
            Log.e("tabLayout error", "Failed to add tabLayout tabs")
            e.printStackTrace()
        }

        //set up viewpager
        viewPager = findViewById(R.id.pager)
        adapter = PagerAdapter(supportFragmentManager, tabLayout?.tabCount ?: 4)
        viewPager?.adapter = adapter
        viewPager?.addOnPageChangeListener(object : TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            override fun onPageSelected(position: Int) {
                viewPager?.currentItem = position
                tabNum = position
            }
        })

        tabLayout?.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    /**
     * Resets the prefs to their default values based on param
     * @param reRead true: overwrite everything, false: only reset the first time this is called
     */
    fun resetPrefs(context: Context, reRead: Boolean) {
        PreferenceManager.setDefaultValues(context, R.xml.prefs, reRead)
    }

    /**
     * Creates the options menu
     *
     * @param menu the Menu
     * @return always true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_score, menu)
        return true
    }

    /**
     * Handles toolbar/overflow options
     *
     * @param item the option tapped
     * @return true if it matches one of the options, otherwise goes to super
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_clear_all -> {
                if (editTexts[tabNum] == null) {
                    saveEditTexts()
                }
                promptClear()
                return true
            }

            R.id.action_settings -> {
                val settingsActivity = Intent(this, SettingsActivity::class.java)
                startActivity(settingsActivity)
                return true
            }

            R.id.action_instructions -> {
                val instructionActivity = Intent(this, InstructionActivity::class.java)
                startActivity(instructionActivity)
                return true
            }

            R.id.help -> {
                val helpActivity = Intent(this, HelpActivity::class.java)
                startActivity(helpActivity)
                return true
            }

            R.id.about -> {
                val aboutActivity = Intent(this, AboutActivity::class.java)
                startActivity(aboutActivity)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Saves all the EditTexts for later
     * Only saves the EditText once
     * Keeps a separate array for each tab
     * Used to minimize findViewById usage
     */
    private fun saveEditTexts() {
        if (editTexts[tabNum] == null) {
            val temp = adapter?.getItem(tabNum) as ScoreFragment?
            editTexts[tabNum] = temp?.ets
        }
    }

    /**
     * Calculates the score with no bonus
     */
    fun lostRound(v: View) {
        calculateScore(0)
    }

    /**
     * Calculates the score with the win bonus added
     */
    fun wonRound(v: View) {
        calculateScore(Integer.parseInt(shared?.getString("winBonus", "300")))
    }

    /**
     * Calculates the score
     * Returns the score for testing
     *
     * @param bonus the bonus to add if they won
     */
    fun calculateScore(bonus: Int): Int {
        var score = bonus
        saveEditTexts()

        //books
        val numCleanBooks = editTexts[tabNum]?.get(0)?.text.toString()
        if (numCleanBooks != "") {
            score += Math.abs(Integer.parseInt(numCleanBooks) * Integer.parseInt(shared?.getString("cleanBook", "500")))
        }

        val numDirtyBooks = editTexts[tabNum]?.get(1)?.text.toString()
        if (numDirtyBooks != "") {
            score += Math.abs(Integer.parseInt(numDirtyBooks) * Integer.parseInt(shared?.getString("dirtyBook", "300")))
        }

        val numCleanWildBooks = editTexts[tabNum]?.get(2)?.text.toString()
        if (numCleanWildBooks != "") {
            score += Math.abs(Integer.parseInt(numCleanWildBooks) * Integer.parseInt(shared?.getString("cleanWildBook", "1500")))
        }

        val numDirtyWildBooks = editTexts[tabNum]?.get(3)?.text.toString()
        if (numDirtyWildBooks != "") {
            score += Math.abs(Integer.parseInt(numDirtyWildBooks) * Integer.parseInt(shared?.getString("dirtyWildBook", "1300")))
        }

        //3's, three values are positive because they are being subtracted from score
        val redThreeValue = Math.abs(Integer.parseInt(shared?.getString("redThree", "300")))
        val numRedThrees = editTexts[tabNum]?.get(4)?.text.toString()
        if (numRedThrees != "") {
            score -= Math.abs(Integer.parseInt(numRedThrees) * redThreeValue)
        }

        val blackThreeValue = Math.abs(Integer.parseInt(shared?.getString("blackThree", "100")))
        val numBlackThrees = editTexts[tabNum]?.get(5)?.text.toString()
        if (numBlackThrees != "") {
            score -= Math.abs(Integer.parseInt(numBlackThrees) * blackThreeValue)
        }

        //additional points
        val fourNineValue = Math.abs(Integer.parseInt(shared?.getString("fourNine", "5")))
        val numFourNines = editTexts[tabNum]?.get(6)?.text.toString()
        if (numFourNines != "") {
            score += Integer.parseInt(numFourNines) * fourNineValue
        }

        val tenKingValue = Math.abs(Integer.parseInt(shared?.getString("tenKing", "10")))
        val numTenKings = editTexts[tabNum]?.get(7)?.text.toString()
        if (numTenKings != "") {
            score += Integer.parseInt(numTenKings) * tenKingValue
        }

        val aceTwoValue = Math.abs(Integer.parseInt(shared?.getString("aceTwo", "20")))
        val numAceTwos = editTexts[tabNum]?.get(8)?.text.toString()
        if (numAceTwos != "") {
            score += Integer.parseInt(numAceTwos) * aceTwoValue
        }

        val jokerValue = Math.abs(Integer.parseInt(shared?.getString("joker", "50")))
        val numJokers = editTexts[tabNum]?.get(9)?.text.toString()
        if (numJokers != "") {
            score += Integer.parseInt(numJokers) * jokerValue
        }

        val numExtraPoints = editTexts[tabNum]?.get(10)?.text.toString()
        if (numExtraPoints != "") {
            score += Integer.parseInt(numExtraPoints)
        }

        updateFragment(score)
        clearEditTexts(tabNum)

        return score
    }

    /**
     * Updates the fragment and the tab
     *
     * @param score the score to add to the fragment and tab
     */
    private fun updateFragment(score: Int) {
        val temp = adapter?.getItem(tabNum) as ScoreFragment?
        temp?.addScore(score)
        try {
            tabLayout?.getTabAt(tabNum)?.text = temp?.title + '\n' + temp?.score
        } catch (e: NullPointerException) {
            Log.e("Update Fragment error", "Failed to update tab")
        }

        temp?.getScrollView()?.fullScroll(ScrollView.FOCUS_UP)
    }

    /**
     * Clears the EditTexts
     * Clears from the given tab number
     *
     * @param tabNumber the number of the tab to clear its EditTexts
     */
    private fun clearEditTexts(tabNumber: Int) {
        editTexts[tabNumber]?.forEach {
            it?.setText("")
        }

        editTexts[tabNumber]?.get(0)?.requestFocus()
    }

    private fun promptClear() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.clear_all_message))

        builder.setPositiveButton(getString(R.string.clear_all_positive)) { _, _ ->
            clearAll()
        }
        builder.setNegativeButton(getString(R.string.clear_all_negative), null)

        val dialog = builder.create()
        dialog.show()
    }

    /**
     * Clears everything from all tabs
     * Does not clear tabs that haven't been made yet
     */
    private fun clearAll() {
        var temp: ScoreFragment?
        for (i in 0..3) {
            if (editTexts[i] != null) {
                clearEditTexts(i)
                temp = adapter?.getItem(i) as ScoreFragment?
                temp?.score = 0

                try {
                    tabLayout?.getTabAt(i)?.text = temp?.title + '\n' + temp?.score
                } catch (e: NullPointerException) {
                    Log.e("Clear all error", "Failed to update tab")
                }

                temp?.getScrollView()?.fullScroll(ScrollView.FOCUS_UP)
                editTexts[i]?.get(0)?.requestFocus()
            }
        }
    }

    /**
     * Custom FragmentPagerAdapter
     */
    inner class PagerAdapter
    /**
     * Constructs the PagerAdapter
     *
     * @param fm   the FragmentManager
     * @param numTabs the number of tabs
     */
    (fm: FragmentManager, private val numTabs: Int) : FragmentPagerAdapter(fm) {
        private val frags: Array<ScoreFragment?> = arrayOfNulls(numTabs)

        init {
            frags[0] = makeScoreFrag(getString(R.string.team1))
            frags[1] = makeScoreFrag(getString(R.string.team2))
            frags[2] = makeScoreFrag(getString(R.string.team3))
            frags[3] = makeScoreFrag(getString(R.string.team4))
        }

        /**
         * Gets the fragment at the position
         *
         * @param position the position of the fragment
         * @return the fragment if it exists
         */
        override fun getItem(position: Int): Fragment? {
            if (position < frags.size) {
                return frags[position]
            }

            return null
        }

        /**
         * Gets the number of tabs
         *
         * @return numTabs the number of tabs
         */
        override fun getCount(): Int {
            return frags.size
        }

        /**
         * Makes and returns a ScoreFragment
         *
         * @param title the fragment's title on the tab
         * @return the ScoreFragment
         */
        private fun makeScoreFrag(title: String): ScoreFragment {
            val bundle = Bundle()
            bundle.putInt("score", 0)
            bundle.putString("title", title)
            val frag = ScoreFragment()
            frag.arguments = bundle
            return frag
        }
    }
}

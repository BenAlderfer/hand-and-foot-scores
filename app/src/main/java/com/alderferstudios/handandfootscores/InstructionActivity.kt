package com.alderferstudios.handandfootscores

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView

/**
 * Instructional activity
 *
 * @author Ben Alderfer
 */
class InstructionActivity : AppCompatActivity() {
    private var slideNum = 0
    private var viewPager: ViewPager? = null
    private var backArrow: Button? = null
    private var forwardArrow: Button? = null
    private var dots: ImageView? = null

    /**
     * Creates the activity and saves the items for changing later
     *
     * @param savedInstanceState the previous state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instruction)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        forwardArrow = findViewById(R.id.forwardArrow)
        backArrow = findViewById(R.id.backArrow)
        backArrow?.visibility = View.INVISIBLE
        dots = findViewById(R.id.dots)

        viewPager = findViewById(R.id.instructionPager)
        val adapter = Adapter(supportFragmentManager)
        viewPager?.adapter = adapter
        viewPager?.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                viewPager?.currentItem = position
                slideNum = position
                updateControls()
            }
        })

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
     * Goes to previous instruction
     *
     * @param view the calling button
     */
    fun back(view: View) {
        --slideNum
        updateControls()
        viewPager?.currentItem = slideNum
    }

    /**
     * Advances the instruction
     *
     * @param view the calling button
     */
    fun forward(view: View) {
        ++slideNum
        updateControls()
        viewPager?.currentItem = slideNum
    }

    /**
     * Updates the arrows and dots
     */
    private fun updateControls() {
        if (slideNum == 0) {
            backArrow?.visibility = View.INVISIBLE
        } else {
            backArrow?.visibility = View.VISIBLE
        }

        if (slideNum == 3) {
            forwardArrow?.visibility = View.INVISIBLE
        } else {
            forwardArrow?.visibility = View.VISIBLE
        }

        when (slideNum) {
            0 -> dots?.setImageResource(R.drawable.dots1)
            1 -> dots?.setImageResource(R.drawable.dots2)
            2 -> dots?.setImageResource(R.drawable.dots3)
            3 -> dots?.setImageResource(R.drawable.dots4)
        }
    }

    /**
     * Custom adapter for the instructions
     */
    private inner class Adapter
    /**
     * Makes an adapter with a FragmentManager
     *
     * @param fm the FragmentManager
     */
    (fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val frags = arrayOfNulls<Fragment>(4)

        init {
            frags[0] = InstructionFragment1()
            frags[1] = InstructionFragment2()
            frags[2] = InstructionFragment3()
            frags[3] = InstructionFragment4()
        }

        /**
         * Gets the fragment and changes the Views
         *
         * @param position the slide number
         * @return frag the same fragment regardless of position
         * only the Views in the fragment change
         */
        override fun getItem(position: Int): Fragment {
            return frags[position] ?: InstructionFragment1()
        }

        /**
         * Gets the number of slides
         *
         * @return the number of slides (4)
         */
        override fun getCount(): Int {
            return frags.size
        }
    }
}

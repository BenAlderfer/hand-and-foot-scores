package com.alderferstudios.handandfootscores

import android.content.Context
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
        viewPager?.adapter = Adapter(this)
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
     * @param v the calling button
     */
    fun back(@Suppress("UNUSED_PARAMETER") v: View) {
        --slideNum
        updateControls()
        viewPager?.currentItem = slideNum
    }

    /**
     * Advances the instruction
     *
     * @param v the calling button
     */
    fun forward(@Suppress("UNUSED_PARAMETER") v: View) {
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
    private inner class Adapter(val context: Context) : PagerAdapter() {

        private val layouts = arrayOfNulls<Int>(4)

        init {
            layouts[0] = R.layout.instruction_setup
            layouts[1] = R.layout.instruction_objective
            layouts[2] = R.layout.instruction_play
            layouts[3] = R.layout.instruction_scoring
        }

        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            val inflater = LayoutInflater.from(context)
            //defaults to main screen
            val layout = inflater.inflate(layouts[position] ?:
                    R.layout.instruction_setup, collection, false) as ViewGroup
            collection.addView(layout)
            return layout
        }

        override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
            collection.removeView(view as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getPageTitle(position: Int): CharSequence {
            return getString(R.string.title_activity_instruction)
        }

        override fun getCount(): Int {
            return layouts.size
        }
    }
}

package com.alderferstudios.handandfootscores

import android.content.Context
import android.os.Looper
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.widget.EditText
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScoreActivityTest {
    private var activity: ScoreActivity? = null
    private var context: Context? = null
    private var startedLooper = false

    @Before
    fun setup() {
        if (!startedLooper) {
            Looper.prepare()    //needed before instantiating classes
            startedLooper = true
        }
        context = InstrumentationRegistry.getTargetContext()

        activity = ScoreActivity()
        activity?.tabNum = 0

        //fill in array with defaults
        val etArray = arrayOfNulls<EditText>(11)
        for (i: Int in etArray.indices) {
            val et = EditText(context)
            et.setText("$i")
            etArray[i] = et
        }
        activity!!.editTexts[0] = etArray

        activity?.resetPrefs(context!!, true)   //force reset prefs
    }

    @Test
    fun calcScoreBonus() {
        assertEquals(6, activity?.calculateScore(300))
    }

    @Test
    fun calcScoreNoBonus() {
        assertEquals(2, activity?.calculateScore(0))
    }
}
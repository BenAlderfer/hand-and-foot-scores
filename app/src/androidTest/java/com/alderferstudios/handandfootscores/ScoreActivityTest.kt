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

    @Before
    fun setup() {
        if (Looper.myLooper() == null) {
            Looper.prepare()    //needed before instantiating classes
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
        activity?.editTexts?.set(0, etArray)

        activity?.initPrefs(context!!)
        activity?.resetPrefs(context!!, true)   //force reset prefs
    }

    @Test
    fun calcScoreBonus() {
        assertEquals(5100, activity?.calculateScore(300))
    }

    @Test
    fun calcScoreNoBonus() {
        assertEquals(4800, activity?.calculateScore(0))
    }
}
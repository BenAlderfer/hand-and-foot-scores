package com.alderferstudios.handandfootscores

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ScrollView

/**
 * Hand and Foot Scores
 * Score Fragment
 * Every tab contains one of these fragments
 *
 * @author Ben Alderfer
 * Alderfer Studios
 */
class ScoreFragment : Fragment() {
    var score: Int = 0
    var title: String? = null

    /**
     * Creates the activity
     * saves the score, number, and title
     *
     * @param savedInstanceState the previous state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        score = arguments.getInt("score", 0)
        title = arguments.getString("title")
    }

    /**
     * When the view is created
     *
     * @param inflater           the LayoutInflater
     * @param container          the ViewGroup
     * @param savedInstanceState the previous state
     * @return the inflated View
     */
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_score, container, false)
    }

    /**
     * Gets the EditTexts in this Fragment and returns them
     *
     * @return ets the array of EditTexts
     */
    val ets: Array<EditText?>
        get() {
            val ets = arrayOfNulls<EditText>(11)
            try {
                ets[0] = view?.findViewById(R.id.numcleanbooks)
                ets[1] = view?.findViewById(R.id.numdirtybooks)
                ets[2] = view?.findViewById(R.id.numcleanwildbooks)
                ets[3] = view?.findViewById(R.id.numdirtywildbooks)
                ets[4] = view?.findViewById(R.id.numredthrees)
                ets[5] = view?.findViewById(R.id.numblackthrees)
                ets[6] = view?.findViewById(R.id.numfournine)
                ets[7] = view?.findViewById(R.id.numtenking)
                ets[8] = view?.findViewById(R.id.numacetwo)
                ets[9] = view?.findViewById(R.id.numjokers)
                ets[10] = view?.findViewById(R.id.numextrapoints)
            } catch (e: NullPointerException) {
                Log.e("Get Ets error", "Failed to fetch views")
            }

            return ets
        }

    /**
     * Gets the scroll view in this Fragment and returns it
     *
     * @return sv the scroll view
     */
    fun getScrollView(): ScrollView {
        var sv = ScrollView(activity)
        try {
            sv = view?.findViewById(R.id.scrollView) ?: ScrollView(activity)
        } catch (e: NullPointerException) {
            Log.e("Get ScrollView error", "Failed to fetch scroll view")
        }

        return sv
    }

    /**
     * Adds the new score to the current score
     *
     * @param s the new score
     */
    fun addScore(s: Int) {
        score += s
    }
}

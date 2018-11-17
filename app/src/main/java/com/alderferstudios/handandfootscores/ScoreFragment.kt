package com.alderferstudios.handandfootscores

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ScrollView
import androidx.fragment.app.Fragment

/**
 * Hand and Foot Scores
 * Score Fragment
 * Every tab contains one of these fragments
 *
 * @author Ben Alderfer
 */
class ScoreFragment : Fragment() {
    var score: Int = 0
    var title: String? = null
    var scrollView: ScrollView? = null
    val ets = arrayOfNulls<EditText>(11)

    /**
     * Creates the activity
     * saves the score, number, and title
     *
     * @param savedInstanceState the previous state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        score = arguments?.getInt("score") ?: 0
        title = arguments?.getString("title")
    }

    /**
     * When the view is created
     *
     * @param inflater           the LayoutInflater
     * @param container          the ViewGroup
     * @param savedInstanceState the previous state
     * @return the inflated View
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_score, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        scrollView = view?.findViewById(R.id.scrollView)

        ets[0] = view?.findViewById(R.id.numCleanBooks)
        ets[1] = view?.findViewById(R.id.numDirtyBooks)
        ets[2] = view?.findViewById(R.id.numCleanWildBooks)
        ets[3] = view?.findViewById(R.id.numDirtyWildBooks)
        ets[4] = view?.findViewById(R.id.numRedThrees)
        ets[5] = view?.findViewById(R.id.numBlackThrees)
        ets[6] = view?.findViewById(R.id.numFourNine)
        ets[7] = view?.findViewById(R.id.numTenKing)
        ets[8] = view?.findViewById(R.id.numAceTwo)
        ets[9] = view?.findViewById(R.id.numJokers)
        ets[10] = view?.findViewById(R.id.numExtraPoints)
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

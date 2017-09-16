package com.alderferstudios.handandfootscores;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;

/**
 * Hand and Foot Scores
 * Score Fragment
 * Every tab contains one of these fragments
 *
 * @author Ben Alderfer
 *         Alderfer Studios
 */
public class ScoreFragment extends Fragment {
    private int score;
    private String title;

    /**
     * Creates the activity
     * saves the score, number, and title
     *
     * @param savedInstanceState the previous state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        score = getArguments().getInt("score", 0);
        title = getArguments().getString("title");
    }

    /**
     * When the view is created
     *
     * @param inflater           the LayoutInflater
     * @param container          the ViewGroup
     * @param savedInstanceState the previous state
     * @return the inflated View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_score, container, false);
    }

    /**
     * Gets the EditTexts in this Fragment and returns them
     *
     * @return ets the array of EditTexts
     */
    protected EditText[] getEts() {
        EditText[] ets = new EditText[11];
        try {
            ets[0] = getView().findViewById(R.id.numcleanbooks);
            ets[1] = getView().findViewById(R.id.numdirtybooks);
            ets[2] = getView().findViewById(R.id.numcleanwildbooks);
            ets[3] = getView().findViewById(R.id.numdirtywildbooks);
            ets[4] = getView().findViewById(R.id.numredthrees);
            ets[5] = getView().findViewById(R.id.numblackthrees);
            ets[6] = getView().findViewById(R.id.numfournine);
            ets[7] = getView().findViewById(R.id.numtenking);
            ets[8] = getView().findViewById(R.id.numacetwo);
            ets[9] = getView().findViewById(R.id.numjokers);
            ets[10] = getView().findViewById(R.id.numextrapoints);
        } catch (NullPointerException e) {
            Log.e("Get Ets error", "Failed to fetch views");
        }

        return ets;
    }

    /**
     * Gets the scroll view in this Fragment and returns it
     *
     * @return sv the scroll view
     */
    protected ScrollView getScrollView() {
        ScrollView sv = new ScrollView(getActivity());
        try {
            sv = getView().findViewById(R.id.scrollView);
        } catch (NullPointerException e) {
            Log.e("Get ScrollView error", "Failed to fetch scroll view");
        }
        return sv;
    }

    /**
     * Gets the score
     *
     * @return score the ScoreFragment's score
     */
    protected int getScore() {
        return score;
    }

    /**
     * Sets the score
     *
     * @param newScore the new score
     */
    protected void setScore(int newScore) {
        score = newScore;
    }

    /**
     * Adds the new score to the current score
     *
     * @param s the new score
     */
    protected void addScore(int s) {
        score += s;
    }

    /**
     * Gets the fragment's title
     *
     * @return title the fragment's title
     */
    protected String getTitle() {
        return title;
    }

    /**
     * Sets the fragment's title
     *
     * @param t the title to set
     */
    protected void setTitle(String t) {
        title = t;
    }
}

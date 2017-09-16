package com.alderferstudios.handandfootscores;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

/**
 * Hand and Foot Scores
 * Main activity
 * Handles navigation, updating scores, clearing, etc...
 *
 * @author Ben Alderfer
 *         Alderfer Studios
 */
public class ScoreActivity extends AppCompatActivity {
    protected static SharedPreferences shared;
    protected static SharedPreferences.Editor editor;

    private int tabNum;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;

    private EditText[][] ets = new EditText[4][];                                                 //holds all the views, first dimension is tab number

    /**
     * Creates the Activity and gets all the variables/listeners ready
     *
     * @param savedInstanceState the savedInstanceState from before
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        shared = PreferenceManager.getDefaultSharedPreferences(this);
        PreferenceManager.setDefaultValues(this, R.xml.prefs, false);                             //sets default values if the preferences have not yet been used
        editor = shared.edit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //lock orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        //current tab
        tabNum = 0;

        //set up tabs
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.team1) + '\n' + 0));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.team2) + '\n' + 0));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.team3) + '\n' + 0));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.team4) + '\n' + 0));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //set up viewpager
        viewPager = findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout) {
            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
                tabNum = position;
            }
        });

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * Creates the options menu
     *
     * @param menu the Menu
     * @return always true (useless)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_score, menu);
        return true;
    }

    /**
     * Handles toolbar/overflow options
     *
     * @param item the option tapped
     * @return true if it matches one of the options, otherwise goes to super
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear_all:
                if (ets[tabNum] == null) {
                    saveEditTexts();
                }
                clearAll();
                return true;

            case R.id.action_settings:
                Intent settingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(settingsActivity);
                return true;

            case R.id.action_instructions:
                Intent instructionActivity = new Intent(this, InstructionActivity.class);
                startActivity(instructionActivity);
                return true;

            case R.id.help:
                Intent helpActivity = new Intent(this, HelpActivity.class);
                startActivity(helpActivity);
                return true;

            case R.id.about:
                Intent aboutActivity = new Intent(this, AboutActivity.class);
                startActivity(aboutActivity);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Saves all the EditTexts for later
     * Only saves the EditText once
     * Keeps a separate array for each tab
     * Used to minimize findViewById usage
     */
    private void saveEditTexts() {
        // if (ets[tabNum] == null) {
        ScoreFragment temp = (ScoreFragment) adapter.getItem(tabNum);
        ets[tabNum] = temp.getEts();
        // }
    }

    /**
     * Calculates the score with no bonus
     */
    public void lostRound(View v) {
        calculateScore(0);
    }

    /**
     * Calculates the score with the win bonus added
     */
    public void wonRound(View v) {
        calculateScore(Integer.parseInt(shared.getString("winBonus", "300")));
    }

    /**
     * Calculates the score
     *
     * @param bonus the bonus to add if they won
     */
    private void calculateScore(int bonus) {
        int score = bonus;
        saveEditTexts();

        //books
        String numCleanBooks = ets[tabNum][0].getText().toString();
        if (!numCleanBooks.equals("")) {
            score += Math.abs(Integer.parseInt(numCleanBooks) * Integer.parseInt(shared.getString("cleanBook", "500")));
        }

        String numDirtyBooks = ets[tabNum][1].getText().toString();
        if (!numDirtyBooks.equals("")) {
            score += Math.abs(Integer.parseInt(numDirtyBooks) * Integer.parseInt(shared.getString("dirtyBook", "300")));
        }

        String numCleanWildBooks = ets[tabNum][2].getText().toString();
        if (!numCleanWildBooks.equals("")) {
            score += Math.abs(Integer.parseInt(numCleanWildBooks) * Integer.parseInt(shared.getString("cleanWildBook", "1500")));
        }

        String numDirtyWildBooks = ets[tabNum][3].getText().toString();
        if (!numDirtyWildBooks.equals("")) {
            score += Math.abs(Integer.parseInt(numDirtyWildBooks) * Integer.parseInt(shared.getString("dirtyWildBook", "1300")));
        }

        //3's, three values are positive because they are being subtracted from score
        int redThreeValue = Math.abs(Integer.parseInt(shared.getString("redThree", "300")));
        String numRedThrees = ets[tabNum][4].getText().toString();
        if (!numRedThrees.equals("")) {
            score -= Math.abs(Integer.parseInt(numRedThrees) * redThreeValue);
        }

        int blackThreeValue = Math.abs(Integer.parseInt(shared.getString("blackThree", "100")));
        String numBlackThrees = ets[tabNum][5].getText().toString();
        if (!numBlackThrees.equals("")) {
            score -= Math.abs(Integer.parseInt(numBlackThrees) * blackThreeValue);
        }

        //additional points
        int fourNineValue = Math.abs(Integer.parseInt(shared.getString("fourNine", "5")));
        String numFourNines = ets[tabNum][6].getText().toString();
        if (!numFourNines.equals("")) {
            score += Integer.parseInt(numFourNines) * fourNineValue;
        }

        int tenKingValue = Math.abs(Integer.parseInt(shared.getString("tenKing", "10")));
        String numTenKings = ets[tabNum][7].getText().toString();
        if (!numTenKings.equals("")) {
            score += Integer.parseInt(numTenKings) * tenKingValue;
        }

        int aceTwoValue = Math.abs(Integer.parseInt(shared.getString("aceTwo", "20")));
        String numAceTwos = ets[tabNum][8].getText().toString();
        if (!numAceTwos.equals("")) {
            score += Integer.parseInt(numAceTwos) * aceTwoValue;
        }

        int jokerValue = Math.abs(Integer.parseInt(shared.getString("joker", "50")));
        String numJokers = ets[tabNum][9].getText().toString();
        if (!numJokers.equals("")) {
            score += Integer.parseInt(numJokers) * jokerValue;
        }

        String numExtraPoints = ets[tabNum][10].getText().toString();
        if (!numExtraPoints.equals("")) {
            score += Integer.parseInt(numExtraPoints);
        }

        updateFragment(score);
        clearEditTexts(tabNum);
    }

    /**
     * Updates the fragment and the tab
     *
     * @param score the score to add to the fragment and tab
     */
    private void updateFragment(int score) {
        ScoreFragment temp = (ScoreFragment) adapter.getItem(tabNum);
        temp.addScore(score);
        try {
            tabLayout.getTabAt(tabNum).setText(temp.getTitle() + '\n' + temp.getScore());
        } catch (NullPointerException e) {
            Log.e("Update Fragment error", "Failed to update tab");
        }

        temp.getScrollView().fullScroll(ScrollView.FOCUS_UP);
    }

    /**
     * Clears the EditTexts
     * Clears from the given tab number
     *
     * @param tabNumber the number of the tab to clear its EditTexts
     */
    private void clearEditTexts(int tabNumber) {
        if (ets[tabNumber] != null) {
            for (EditText e : ets[tabNumber]) {
                e.setText("");
            }
        }

        ets[tabNumber][0].requestFocus();
    }

    /**
     * Clears everything from all tabs
     * Does not clear tabs that haven't been made yet
     */
    private void clearAll() {
        ScoreFragment temp;
        for (int i = 0; i < 4; i++) {
            if (ets[i] != null) {
                clearEditTexts(i);
                temp = (ScoreFragment) adapter.getItem(i);
                temp.setScore(0);

                try {
                    tabLayout.getTabAt(i).setText(temp.getTitle() + '\n' + temp.getScore());
                } catch (NullPointerException e) {
                    Log.e("Clear all error", "Failed to update tab");
                }

                temp.getScrollView().fullScroll(ScrollView.FOCUS_UP);
                ets[i][0].requestFocus();
            }
        }
    }

    /**
     * Custom FragmentPagerAdapter
     */
    public class PagerAdapter extends FragmentPagerAdapter {
        private int numTabs;
        private ScoreFragment[] frags;

        /**
         * Constructs the PagerAdapter
         *
         * @param fm   the FragmentManager
         * @param tabs the number of tabs
         */
        public PagerAdapter(FragmentManager fm, int tabs) {
            super(fm);
            numTabs = tabs;

            frags = new ScoreFragment[numTabs];
            frags[0] = makeScoreFrag(getString(R.string.team1));
            frags[1] = makeScoreFrag(getString(R.string.team2));
            frags[2] = makeScoreFrag(getString(R.string.team3));
            frags[3] = makeScoreFrag(getString(R.string.team4));
        }

        /**
         * Gets the fragment at the position
         *
         * @param position the position of the fragment
         * @return the fragment if it exists
         */
        @Override
        public Fragment getItem(int position) {
            if (position < frags.length) {
                return frags[position];
            } else {
                return null;
            }
        }

        /**
         * Gets the number of tabs
         *
         * @return numTabs the number of tabs
         */
        @Override
        public int getCount() {
            return numTabs;
        }

        /**
         * Makes and returns a ScoreFragment
         *
         * @param title the fragment's title on the tab
         * @return the ScoreFragment
         */
        private ScoreFragment makeScoreFrag(String title) {
            Bundle bundle = new Bundle();
            bundle.putInt("score", 0);
            bundle.putString("title", title);
            ScoreFragment frag = new ScoreFragment();
            frag.setArguments(bundle);
            return frag;
        }
    }
}

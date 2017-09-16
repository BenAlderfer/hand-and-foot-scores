package com.alderferstudios.handandfootscores;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

/**
 * Hand and Foot Scores
 * Settings activity
 * Lets the user change values for different items
 *
 * @author Ben Alderfer
 *         Alderfer Studios
 */
public class SettingsActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    protected static SharedPreferences shared;
    protected static SharedPreferences.Editor editor;

    /**
     * Creates the activity
     * Checks if portrait or landscape and makes fragments accordingly
     *
     * @param savedInstanceState the previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        shared = PreferenceManager.getDefaultSharedPreferences(this);
        editor = shared.edit();

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (NullPointerException e) {
            Log.e("On Create error", "Failed to set back button");
        }

        //sets default values if the preferences have not yet been used
        PreferenceManager.setDefaultValues(this, R.xml.prefs, false);

        //get and set toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //lock orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        //pick which fragments to use
        //landscape disabled for now
        /*if (isLandscape()) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.frame1, new LandscapePrefsFragment1()).commit();

            getFragmentManager().beginTransaction()
                    .replace(R.id.frame2, new LandscapePrefsFragment2()).commit();
        } else {*/
            getFragmentManager().beginTransaction()
                    .replace(R.id.framePort, new PortraitFragment()).commit();
        //}



        shared.registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * If the back arrow was clicked on toolbar
     *
     * @param item the item selected (only option is back arrow)
     * @return always true (useless)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    /**
     * When a preference changes
     *
     * @param sharedPreferences the Shared Preferences
     * @param key               the preference that was changed
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    /**
     * Checks if the device is in landscape
     *
     * @return true if in landscape
     */
    protected boolean isLandscape() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * The generic preference fragment class
     * Single or dual fragments based on this
     */
    public static class PrefFrag extends PreferenceFragment
            implements SharedPreferences.OnSharedPreferenceChangeListener {

        /**
         * Creates the Fragment
         *
         * @param savedInstanceState the previous state
         */
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        /**
         * Sets the summaries on start
         */
        protected void setSummaries() {
            setCleanBookSummary();                                                                //clean book value
            setDirtyBookSummary();                                                                //dirty book value
            setCleanWildBookSummary();                                                            //clean wild book value
            setDirtyWildBookSummary();                                                            //dirty wild book value
            setRedThreeSummary();                                                                 //red three value
            setBlackThreeSummary();                                                               //black three value
            setFourNineSummary();                                                                 //four nine value
            setTenKingSummary();                                                                  //ten king value
            setAceTwoSummary();                                                                   //ace two value
            setJokerSummary();                                                                    //joker value
            setWinSummary();                                                                      //win bonus
        }

        /**
         * Sets the summaries on change
         */
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
                case "cleanBook":
                    setCleanBookSummary();
                    break;                                                                        //clean book value
                case "dirtyBook":
                    setDirtyBookSummary();
                    break;                                                                        //dirty book value
                case "cleanWildBook":
                    setCleanWildBookSummary();
                    break;                                                                        //clean wild book value
                case "dirtyWildBook":
                    setDirtyWildBookSummary();
                    break;                                                                        //dirty wild book value
                case "redThree":
                    setRedThreeSummary();
                    break;                                                                        //red three value
                case "blackThree":
                    setBlackThreeSummary();
                    break;                                                                        //black three value
                case "fourNine":
                    setFourNineSummary();
                    break;                                                                        //four nine value
                case "tenKing":
                    setTenKingSummary();
                    break;                                                                        //ten king value
                case "aceTwo":
                    setAceTwoSummary();
                    break;                                                                        //ace two value
                case "joker":
                    setJokerSummary();
                    break;                                                                        //joker value
                case "winBonus":
                    setWinSummary();
                    break;                                                                        //win bonus
            }
        }

        /**
         * Sets the summary for the clean book value
         * String needs to be acquired differently since its being added
         */
        protected void setCleanBookSummary() {
            if (isAdded()) {                                                                      //must check if the fragment is added to the activity
                Preference p = findPreference("cleanBook");
                if (p != null) {
                    //make sure value is not ""
                    if (shared.getString("cleanBook", "500").equals("")) {
                        editor.putString("cleanBook", "500");
                        editor.apply();
                    }

                    p.setSummary(getResources().getString(R.string.clean_book_desc)
                            + " " + shared.getString("cleanBook", "500"));
                }
            }
        }

        /**
         * Sets the summary for the dirty book value
         * String needs to be acquired differently since its being added
         */
        protected void setDirtyBookSummary() {
            if (isAdded()) {                                                                      //must check if the fragment is added to the activity
                Preference p = findPreference("dirtyBook");
                if (p != null) {
                    //make sure value is not ""
                    if (shared.getString("dirtyBook", "300").equals("")) {
                        editor.putString("dirtyBook", "300");
                        editor.apply();
                    }

                    p.setSummary(getResources().getString(R.string.dirty_book_desc)
                            + " " + shared.getString("dirtyBook", "300"));
                }
            }
        }

        /**
         * Sets the summary for the clean wild book value
         * String needs to be acquired differently since its being added
         */
        protected void setCleanWildBookSummary() {
            if (isAdded()) {                                                                      //must check if the fragment is added to the activity
                Preference p = findPreference("cleanWildBook");
                if (p != null) {
                    //make sure value is not ""
                    if (shared.getString("cleanWildBook", "1500").equals("")) {
                        editor.putString("cleanWildBook", "1500");
                        editor.apply();
                    }

                    p.setSummary(getResources().getString(R.string.clean_wild_desc)
                            + " " + shared.getString("cleanWildBook", "1500"));
                }
            }
        }

        /**
         * Sets the summary for the dirty wild book value
         * String needs to be acquired differently since its being added
         */
        protected void setDirtyWildBookSummary() {
            if (isAdded()) {                                                                      //must check if the fragment is added to the activity
                Preference p = findPreference("dirtyWildBook");
                if (p != null) {
                    //make sure value is not ""
                    if (shared.getString("dirtyWildBook", "1300").equals("")) {
                        editor.putString("dirtyWildBook", "1300");
                        editor.apply();
                    }

                    p.setSummary(getResources().getString(R.string.dirty_wild_desc)
                            + " " + shared.getString("dirtyWildBook", "1300"));
                }
            }
        }

        /**
         * Sets the summary for the red three value
         * String needs to be acquired differently since its being added
         */
        protected void setRedThreeSummary() {
            if (isAdded()) {                                                                      //must check if the fragment is added to the activity
                Preference p = findPreference("redThree");
                if (p != null) {
                    //make sure value is not ""
                    if (shared.getString("redThree", "300").equals("")) {
                        editor.putString("redThree", "300");
                        editor.apply();
                    }

                    p.setSummary(getResources().getString(R.string.red_three_desc)
                            + " -" + Math.abs(Integer.parseInt(shared.getString("redThree", "300"))));
                }
            }
        }

        /**
         * Sets the summary for the black three value
         * String needs to be acquired differently since its being added
         */
        protected void setBlackThreeSummary() {
            if (isAdded()) {                                                                      //must check if the fragment is added to the activity
                Preference p = findPreference("blackThree");
                if (p != null) {
                    //make sure value is not ""
                    if (shared.getString("blackThree", "100").equals("")) {
                        editor.putString("blackThree", "100");
                        editor.apply();
                    }

                    p.setSummary(getResources().getString(R.string.black_three_desc)
                            + " -" + Math.abs(Integer.parseInt(shared.getString("blackThree", "100"))));
                }
            }
        }

        /**
         * Sets the summary for the 4s-9s value
         * String needs to be acquired differently since its being added
         */
        protected void setFourNineSummary() {
            if (isAdded()) {                                                                      //must check if the fragment is added to the activity
                Preference p = findPreference("fourNine");
                if (p != null) {
                    //make sure value is not ""
                    if (shared.getString("fourNine", "5").equals("")) {
                        editor.putString("fourNine", "5");
                        editor.apply();
                    }

                    p.setSummary(getResources().getString(R.string.four_nine_desc)
                            + " -" + Math.abs(Integer.parseInt(shared.getString("fourNine", "5"))));
                }
            }
        }

        /**
         * Sets the summary for the 10s-Kings value
         * String needs to be acquired differently since its being added
         */
        protected void setTenKingSummary() {
            if (isAdded()) {                                                                      //must check if the fragment is added to the activity
                Preference p = findPreference("tenKing");
                if (p != null) {
                    //make sure value is not ""
                    if (shared.getString("tenKing", "10").equals("")) {
                        editor.putString("tenKing", "10");
                        editor.apply();
                    }

                    p.setSummary(getResources().getString(R.string.ten_king_desc)
                            + " -" + Math.abs(Integer.parseInt(shared.getString("tenKing", "10"))));
                }
            }
        }

        /**
         * Sets the summary for the Aces and Twos value
         * String needs to be acquired differently since its being added
         */
        protected void setAceTwoSummary() {
            if (isAdded()) {                                                                      //must check if the fragment is added to the activity
                Preference p = findPreference("aceTwo");
                if (p != null) {
                    //make sure value is not ""
                    if (shared.getString("aceTwo", "20").equals("")) {
                        editor.putString("aceTwo", "20");
                        editor.apply();
                    }

                    p.setSummary(getResources().getString(R.string.ace_two_desc)
                            + " -" + Math.abs(Integer.parseInt(shared.getString("aceTwo", "20"))));
                }
            }
        }

        /**
         * Sets the summary for the Joker value
         * String needs to be acquired differently since its being added
         */
        protected void setJokerSummary() {
            if (isAdded()) {                                                                      //must check if the fragment is added to the activity
                Preference p = findPreference("joker");
                if (p != null) {
                    //make sure value is not ""
                    if (shared.getString("joker", "50").equals("")) {
                        editor.putString("joker", "50");
                        editor.apply();
                    }

                    p.setSummary(getResources().getString(R.string.joker_desc)
                            + " -" + Math.abs(Integer.parseInt(shared.getString("joker", "50"))));
                }
            }
        }

        /**
         * Sets the summary for the win value
         * String needs to be acquired differently since its being added
         */
        protected void setWinSummary() {
            if (isAdded()) {                                                                      //must check if the fragment is added to the activity
                Preference p = findPreference("winBonus");
                if (p != null) {
                    //make sure value is not ""
                    if (shared.getString("winBonus", "300").equals("")) {
                        editor.putString("winBonus", "300");
                        editor.apply();
                    }

                    p.setSummary(getResources().getString(R.string.win_desc)
                            + " " + shared.getString("winBonus", "300"));
                }
            }
        }
    }

    /**
     * The portrait fragment, contains everything
     */
    public static class PortraitFragment extends PrefFrag {

        /**
         * Creates the Fragment
         *
         * @param savedInstanceState the previous state
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            PreferenceManager.setDefaultValues(getActivity(), R.xml.prefs, false);
            addPreferencesFromResource(R.xml.prefs);

            setSummaries();
            shared.registerOnSharedPreferenceChangeListener(this);
        }
    }

    /**
     * Left landscape fragment, contains functionality tweaks
     */
    public static class LandscapePrefsFragment1 extends PrefFrag {

        /**
         * Creates the Fragment
         *
         * @param savedInstanceState the previous state
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            PreferenceManager.setDefaultValues(getActivity(), R.xml.prefs, false);
            addPreferencesFromResource(R.xml.land_prefs1);

            setSummaries();
            shared.registerOnSharedPreferenceChangeListener(this);
        }

        /**
         * Sets the summaries on start
         */
        @Override
        protected void setSummaries() {
            setCleanBookSummary();                                                                //clean book value
            setDirtyBookSummary();                                                                //dirty book value
            setCleanWildBookSummary();                                                            //clean wild book value
            setDirtyWildBookSummary();                                                            //dirty wild book value
            setRedThreeSummary();                                                                 //red three value
            setBlackThreeSummary();                                                               //black three value
        }

        /**
         * Sets the summaries on change
         */
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
                case "cleanBook":
                    setCleanBookSummary();
                    break;                                                                        //clean book value
                case "dirtyBook":
                    setDirtyBookSummary();
                    break;                                                                        //dirty book value
                case "cleanWildBook":
                    setCleanWildBookSummary();
                    break;                                                                        //clean wild book value
                case "dirtyWildBook":
                    setDirtyWildBookSummary();
                    break;                                                                        //dirty wild book value
                case "redThree":
                    setRedThreeSummary();
                    break;                                                                        //red three value
                case "blackThree":
                    setBlackThreeSummary();
                    break;                                                                        //black three value
            }
        }
    }

    /**
     * Right landscape fragment, contains split and design tweaks
     */
    public static class LandscapePrefsFragment2 extends PrefFrag {

        /**
         * Creates the Fragment
         *
         * @param savedInstanceState the previous state
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            PreferenceManager.setDefaultValues(getActivity(), R.xml.prefs, false);
            addPreferencesFromResource(R.xml.land_prefs2);

            setSummaries();
            shared.registerOnSharedPreferenceChangeListener(this);
        }

        /**
         * Sets the summaries
         */
        @Override
        protected void setSummaries() {
            setFourNineSummary();                                                                 //four nine value
            setTenKingSummary();                                                                  //ten king value
            setAceTwoSummary();                                                                   //ace two value
            setJokerSummary();                                                                    //joker value
            setWinSummary();                                                                      //win bonus
        }

        /**
         * When a preference changes
         *
         * @param sharedPreferences the Shared Preferences
         * @param key               the preference that was changed
         */
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            switch (key) {
                case "fourNine":
                    setFourNineSummary();
                    break;                                                                        //four nine value
                case "tenKing":
                    setTenKingSummary();
                    break;                                                                        //ten king value
                case "aceTwo":
                    setAceTwoSummary();
                    break;                                                                        //ace two value
                case "joker":
                    setJokerSummary();
                    break;                                                                        //joker value
                case "winBonus":
                    setWinSummary();
                    break;                                                                        //win bonus
            }
        }
    }
}

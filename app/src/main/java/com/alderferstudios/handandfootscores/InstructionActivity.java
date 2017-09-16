package com.alderferstudios.handandfootscores;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Instructional activity
 *
 * @author Ben Alderfer
 */
public class InstructionActivity extends AppCompatActivity {
    private int slideNum = 0;
    private ViewPager viewPager;
    private Button backArrow, forwardArrow;
    private ImageView dots;

    /**
     * Creates the activity and saves the items for changing later
     *
     * @param savedInstanceState the previous state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        forwardArrow = findViewById(R.id.forwardArrow);
        backArrow = findViewById(R.id.backArrow);
        backArrow.setVisibility(View.INVISIBLE);
        dots = findViewById(R.id.dots);

        viewPager = findViewById(R.id.instructionPager);
        Adapter adapter = new Adapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
                slideNum = position;
                updateControls();
            }
        });

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
     * Goes to previous instruction
     *
     * @param view the calling button
     */
    public void back(View view) {
        --slideNum;
        updateControls();
        viewPager.setCurrentItem(slideNum);
    }

    /**
     * Advances the instruction
     *
     * @param view the calling button
     */
    public void forward(View view) {
        ++slideNum;
        updateControls();
        viewPager.setCurrentItem(slideNum);
    }

    /**
     * Updates the arrows and dots
     */
    private void updateControls() {
        if (slideNum == 0) {
            backArrow.setVisibility(View.INVISIBLE);
        } else {
            backArrow.setVisibility(View.VISIBLE);
        }

        if (slideNum == 3) {
            forwardArrow.setVisibility(View.INVISIBLE);
        } else {
            forwardArrow.setVisibility(View.VISIBLE);
        }

        switch (slideNum) {
            case 0:
                dots.setImageResource(R.drawable.dots1);
                break;
            case 1:
                dots.setImageResource(R.drawable.dots2);
                break;
            case 2:
                dots.setImageResource(R.drawable.dots3);
                break;
            case 3:
                dots.setImageResource(R.drawable.dots4);
                break;
        }
    }

    /**
     * Custom adapter for the instructions
     */
    private class Adapter extends FragmentPagerAdapter {
        private Fragment[] frags = new Fragment[4];

        /**
         * Makes an adapter with a FragmentManager
         *
         * @param fm the FragmentManager
         */
        public Adapter(FragmentManager fm) {
            super(fm);

            frags[0] = new InstructionFragment1();
            frags[1] = new InstructionFragment2();
            frags[2] = new InstructionFragment3();
            frags[3] = new InstructionFragment4();
        }

        /**
         * Gets the fragment and changes the Views
         *
         * @param position the slide number
         * @return frag the same fragment regardless of position
         * only the Views in the fragment change
         */
        @Override
        public Fragment getItem(int position) {
            return frags[position];
        }

        /**
         * Gets the number of slides
         *
         * @return the number of slides (4)
         */
        @Override
        public int getCount() {
            return 4;
        }
    }
}

package com.trust.android.popmovies.activities;

import android.os.Bundle;

import com.trust.android.popmovies.R;
import com.trust.android.popmovies.fragments.MainFragment;

/**
 * For TRUSTUDIO
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.trust.android.popmovies.activities
 * or see link for more detail http://bibucket.org/iwanz98/pop-movies
 */

public class MainActivity extends BaseActivity {
    private MainFragment mFragmentMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            mFragmentMain = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, mFragmentMain).commit();
        } else {
            mFragmentMain = (MainFragment) getSupportFragmentManager().getFragment(savedInstanceState, "mFragment");
        }
    }
}

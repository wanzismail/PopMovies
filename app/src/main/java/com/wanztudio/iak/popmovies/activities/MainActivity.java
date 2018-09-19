package com.wanztudio.iak.popmovies.activities;

import android.os.Bundle;

import com.wanztudio.iak.popmovies.R;
import com.wanztudio.iak.popmovies.fragments.MainFragment;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.activities
 * or see link for more detail https://github.com/iwanz98/PopMovies
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

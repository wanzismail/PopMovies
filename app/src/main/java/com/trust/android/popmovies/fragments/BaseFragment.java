package com.trust.android.popmovies.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.rey.material.widget.SnackBar;
import com.trust.android.popmovies.activities.BaseActivity;

/**
 * For TRUSTUDIO
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.trust.android.popmovies.fragments
 * or see link for more detail http://bibucket.org/iwanz98/pop-movies
 */

public class BaseFragment extends Fragment {

    public ActionBar getActionbar() {
        return ((BaseActivity) getActivity()).getSupportActionBar();
    }

    public boolean isNetworkOn(Context context) {
        return ((BaseActivity) getActivity()).isNetworkOn(context);
    }

    public SharedPreferences getSharedPreferences() {
        return ((BaseActivity) getActivity()).getSharedPreferences();
    }

    public void showSnackbar(SnackBar mSnackBar, int text) {
        ((BaseActivity) getActivity()).showSnackbar(mSnackBar, text);
    }

    public void showSnackbar(SnackBar mSnackBar, String text) {
        ((BaseActivity) getActivity()).showSnackbar(mSnackBar, text);
    }
}

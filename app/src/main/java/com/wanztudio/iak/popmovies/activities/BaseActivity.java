package com.wanztudio.iak.popmovies.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.rey.material.widget.SnackBar;
import com.wanztudio.iak.popmovies.utils.Cons;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.activities
 * or see link for more detail https://github.com/iwanz98/PopMovies
 */

public class BaseActivity extends AppCompatActivity {
    public SharedPreferences mSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPref = getSharedPreferences(Cons.PRIVATE_PREF, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return mSharedPref;
    }

    public boolean isNetworkOn(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    public void showSnackbar(SnackBar mSnackBar, int text) {
        if (mSnackBar.getState() == SnackBar.STATE_SHOWN) {
            mSnackBar.dismiss();
        } else {
            mSnackBar.text(getResources().getText(text))
                    .duration(3000)
                    .show();
        }
    }

    public void showSnackbar(SnackBar mSnackBar, String text) {
        if (mSnackBar.getState() == SnackBar.STATE_SHOWN) {
            mSnackBar.dismiss();
        } else {
            mSnackBar.text(text)
                    .duration(3000)
                    .show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

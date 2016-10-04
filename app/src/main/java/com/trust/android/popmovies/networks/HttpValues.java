package com.trust.android.popmovies.networks;

import java.util.TreeSet;

/**
 * For TRUSTUDIO
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.trust.android.popmovies.networks
 * or see link for more detail http://bibucket.org/iwanz98/pop-movies
 */

public class HttpValues {
    private TreeSet<String> mValues;

    public HttpValues() {
        mValues = new TreeSet();
    }

    public HttpValues(String value) {
        mValues = new TreeSet();
        mValues.add(value);
    }

    public void add(String value) {
        mValues.add(value);
    }

    public void remove(String value) {
        mValues.remove(value);
    }

    public void clear() {
        mValues.clear();
    }

    public boolean isEmpty() {
        return mValues.isEmpty();
    }

    public TreeSet<String> getAll() {
        return mValues;
    }
}

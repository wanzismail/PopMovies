package com.wanztudio.iak.popmovies.networks;

import java.util.TreeSet;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.networks
 * or see link for more detail https://github.com/iwanz98/PopMovies
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

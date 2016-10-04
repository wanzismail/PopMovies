package com.trust.android.popmovies.models;

import java.util.ArrayList;

/**
 * For TRUSTUDIO
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.trust.android.popmovies.models
 * or see link for more detail http://bibucket.org/iwanz98/pop-movies
 */


public class HighestRatedEvent {
    public ArrayList<Movie> movieArrayList;
    public String status;
    public String message;

    public HighestRatedEvent(String status, ArrayList<Movie> movieArrayList) {
        this.status = status;
        this.movieArrayList = movieArrayList;
    }

    public HighestRatedEvent(String status, String message) {
        this.status = status;
        this.message = message;
    }
}

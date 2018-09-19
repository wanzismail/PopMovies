package com.wanztudio.iak.popmovies.models;

import java.util.ArrayList;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.models
 * or see link for more detail https://github.com/iwanz98/PopMovies
 */


public class TrailerEvent {
    public ArrayList<Trailer> trailerArrayList;
    public String status;
    public String message;

    public TrailerEvent(String status, ArrayList<Trailer> trailerArrayList) {
        this.status = status;
        this.trailerArrayList = trailerArrayList;
    }

    public TrailerEvent(String status, String message) {
        this.status = status;
        this.message = message;
    }
}

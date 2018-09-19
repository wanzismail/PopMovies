package com.wanztudio.iak.popmovies.data;

import android.provider.BaseColumns;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.data
 * or see link for more detail https://github.com/iwanz98/PopMovies
 */

public class FavoriteContract {

    public static final int COLUMN_ID                      = 1;
    public static final int COLUMN_TITLE                   = 2;
    public static final int COLUMN_POSTER                  = 3;
    public static final int COLUMN_SYNOPSIS                = 4;
    public static final int COLUMN_USER_RATING             = 5;
    public static final int COLUMN_RELEASE_DATE            = 6;

    public static final class FavoriteEntry implements BaseColumns {
        public static final String TABLE_NAME = "favorite_movies";

        public static final String COLUMN_ID                      = "favorite_id";
        public static final String COLUMN_TITLE                   = "favorite_title";
        public static final String COLUMN_POSTER                  = "favorite_poster";
        public static final String COLUMN_SYNOPSIS                = "favorite_synopsis";
        public static final String COLUMN_USER_RATING             = "favorite_user_rating";
        public static final String COLUMN_RELEASE_DATE            = "favorite_release_date";

    }
}

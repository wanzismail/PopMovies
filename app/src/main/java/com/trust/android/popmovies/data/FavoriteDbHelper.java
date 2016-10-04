package com.trust.android.popmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.trust.android.popmovies.data.FavoriteContract.*;

/**
 * For TRUSTUDIO
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.trust.android.popmovies.data
 * or see link for more detail http://bibucket.org/iwanz98/pop-movies
 */

public class FavoriteDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "movie.db";

    public FavoriteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FLIGHT_TABLE = "CREATE TABLE " + FavoriteEntry.TABLE_NAME + " (" +
                FavoriteEntry._ID + " INTEGER PRIMARY KEY," +
                FavoriteEntry.COLUMN_ID + " INTEGER  NOT NULL, " +
                FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL, " +
                FavoriteEntry.COLUMN_USER_RATING + " REAL NOT NULL, " +
                FavoriteEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL " +
                ")";
        db.execSQL(SQL_CREATE_FLIGHT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }
}

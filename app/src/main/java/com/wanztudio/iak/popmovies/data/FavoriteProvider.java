package com.wanztudio.iak.popmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

import static com.wanztudio.iak.popmovies.data.FavoriteContract.FavoriteEntry;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.data
 * or see link for more detail https://github.com/iwanz98/PopMovies
 */

public class FavoriteProvider extends ContentProvider{
    // fields for my content provider
    private static final String PROVIDER_NAME = "com.wanztudio.iak.popmovies.favoriteProvider";
    private static final String URL = "content://" + PROVIDER_NAME + "/favorite";
    public static final Uri CONTENT_URI = Uri.parse(URL);

    private static final int FAVORITES = 1;
    private static final int FAVORITES_ID = 2;

    // projection map for a query
    private static HashMap<String, String> FavoriteMap;

    private SQLiteDatabase database;
    private FavoriteDbHelper dbHelper;

    // maps content URI "patterns" to the integer values that were set above
    private static final UriMatcher uriMatcher;

    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "favorites", FAVORITES);
        uriMatcher.addURI(PROVIDER_NAME, "favorites/#", FAVORITES_ID);
    }

    @Override
    public boolean onCreate() {
        // class that creates and manages the provider's database
        dbHelper = new FavoriteDbHelper(getContext());
        database = dbHelper.getReadableDatabase();

        if(database == null)
            return false;
        else
            return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // TODO Auto-generated method stub
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // the TABLE_NAME to query on
        queryBuilder.setTables(FavoriteEntry.TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            // maps all database column names
            case FAVORITES:
                queryBuilder.setProjectionMap(FavoriteMap);
                break;
            case FAVORITES_ID:
                queryBuilder.appendWhere( FavoriteEntry._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder == ""){
            // No sorting-> sort on names by default
            sortOrder = FavoriteEntry._ID;
        }

        Cursor cursor = queryBuilder.query(database, projection, selection,
                selectionArgs, null, null, sortOrder);
        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO Auto-generated method stub
        long row = database.insert(FavoriteEntry.TABLE_NAME, "", values);

        // If record is added successfully
        if(row > 0) {
            Uri newUri = ContentUris.withAppendedId(CONTENT_URI, row);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Fail to add a new record into " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        int count = 0;

        switch (uriMatcher.match(uri)){
            case FAVORITES:
                count = database.update(FavoriteEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case FAVORITES_ID:
                count = database.update(FavoriteEntry.TABLE_NAME, values, FavoriteEntry._ID +
                        " = " + uri.getLastPathSegment() +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        int count = 0;

        switch (uriMatcher.match(uri)){
            case FAVORITES:
                // delete all the records of the table
                count = database.delete(FavoriteEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case FAVORITES_ID:
                String id = uri.getLastPathSegment();	//gets the id
                count = database.delete( FavoriteEntry.TABLE_NAME, FavoriteEntry.COLUMN_ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        switch (uriMatcher.match(uri)){
            // Get all friend-birthday records
            case FAVORITES:
                return "vnd.android.cursor.dir/vnd.popmovies.favorites";
            // Get a particular friend
            case FAVORITES_ID:
                return "vnd.android.cursor.item/vnd.popmovies.favorites";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}

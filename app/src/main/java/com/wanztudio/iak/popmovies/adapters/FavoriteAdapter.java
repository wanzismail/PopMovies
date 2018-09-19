package com.wanztudio.iak.popmovies.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wanztudio.iak.popmovies.R;
import com.wanztudio.iak.popmovies.data.FavoriteContract;
import com.wanztudio.iak.popmovies.fragments.MainFragment;
import com.wanztudio.iak.popmovies.utils.Debug;

import java.io.File;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.adapters
 * or see link for more detail https://github.com/iwanz98/PopMovies
 */


public class FavoriteAdapter extends android.support.v4.widget.CursorAdapter {

    public FavoriteAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    private static class ViewHolder {
        RelativeLayout mLayout;
        ImageView mImageView;
        TextView mTitle;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_favorite, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.mLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
        holder.mImageView = (ImageView) view.findViewById(R.id.thumbnail);
        holder.mTitle = (TextView) view.findViewById(R.id.title);

        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        Debug.i(cursor.getString(FavoriteContract.COLUMN_POSTER));

        Uri uri = Uri.fromFile(new File(cursor.getString(FavoriteContract.COLUMN_POSTER)));

        Picasso.with(mContext)
                .load(uri)
                .placeholder(R.drawable.poster_default)
                .into(holder.mImageView);

        holder.mTitle.setText(cursor.getString(FavoriteContract.COLUMN_TITLE));

        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment.viewDetail(cursor);
            }
        });
    }
}

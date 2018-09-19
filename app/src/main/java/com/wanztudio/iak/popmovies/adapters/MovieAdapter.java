package com.wanztudio.iak.popmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.wanztudio.iak.popmovies.R;
import com.wanztudio.iak.popmovies.fragments.MainFragment;
import com.wanztudio.iak.popmovies.models.Movie;

import java.util.ArrayList;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.adapters
 * or see link for more detail https://github.com/iwanz98/PopMovies
 */

public class MovieAdapter extends BaseAdapter {
    private Context mContext;
    private MainFragment mainFragment;
    private ArrayList<Movie> mItems;

    private ViewHolder viewHolder;

    public MovieAdapter(Context context, MainFragment mainFragment, ArrayList<Movie> items) {
        this.mContext = context;
        this.mainFragment = mainFragment;
        this.mItems = items;
    }

    private static class ViewHolder {
        RelativeLayout mLayout;
        ImageView mImageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.gridview_movie_item, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.mLayout = (RelativeLayout) convertView.findViewById(R.id.layout);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.thumbnail);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Movie item = mItems.get(position);

        Picasso.with(mContext)
                .load(item.poster_path)
                .placeholder(R.drawable.poster_default)
                .into(viewHolder.mImageView);

        viewHolder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainFragment.viewDetail(item);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

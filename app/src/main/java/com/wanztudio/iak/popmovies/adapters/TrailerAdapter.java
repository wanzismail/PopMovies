package com.wanztudio.iak.popmovies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wanztudio.iak.popmovies.R;
import com.wanztudio.iak.popmovies.models.Trailer;

import java.util.List;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.adapters
 * or see link for more detail https://github.com/iwanz98/PopMovies
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private Context mContext;
    private List<Trailer> trailerList;


    public TrailerAdapter(Context context, List<Trailer> list) {
        mContext = context;
        this.trailerList = list;
    }

    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.mName.setText("" + trailerList.get(position).name);

        viewHolder.mTrailer = trailerList.get(position);

        final String key = viewHolder.mTrailer.key;

        viewHolder.mainPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(key));
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public RelativeLayout mainPlay;
        public TextView mName;
        public Trailer mTrailer;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            mName = (TextView) itemLayoutView.findViewById(R.id.title_trailer);
            mainPlay = (RelativeLayout) itemLayoutView.findViewById(R.id.mainPlay);
        }
    }
}

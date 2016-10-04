package com.trust.android.popmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.trust.android.popmovies.R;
import com.trust.android.popmovies.models.Review;

import java.util.List;

/**
 * For TRUSTUDIO
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.trust.android.popmovies.adapters
 * or see link for more detail http://bibucket.org/iwanz98/pop-movies
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private Context mContext;
    private List<Review> reviewList;


    public ReviewAdapter(Context context, List<Review> list) {
        mContext = context;
        this.reviewList = list;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.mReview = reviewList.get(position);

        String author = viewHolder.mReview.author;
        String content = viewHolder.mReview.content;

        viewHolder.mName.setText(author);
        viewHolder.mComment.setText(content);
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView mName;
        public TextView mComment;
        public Review mReview;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            mName = (TextView) itemLayoutView.findViewById(R.id.primary);
            mComment = (TextView) itemLayoutView.findViewById(R.id.secondary);
        }
    }
}

package com.trust.android.popmovies.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.SnackBar;
import com.squareup.picasso.Picasso;
import com.trust.android.popmovies.R;
import com.trust.android.popmovies.adapters.ReviewAdapter;
import com.trust.android.popmovies.adapters.TrailerAdapter;
import com.trust.android.popmovies.data.FavoriteProvider;
import com.trust.android.popmovies.models.Review;
import com.trust.android.popmovies.models.ReviewEvent;
import com.trust.android.popmovies.models.Trailer;
import com.trust.android.popmovies.models.TrailerEvent;
import com.trust.android.popmovies.networks.Loader;
import com.trust.android.popmovies.utils.Cons;
import com.trust.android.popmovies.utils.Debug;
import com.trust.android.popmovies.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import io.techery.properratingbar.ProperRatingBar;

import static com.trust.android.popmovies.data.FavoriteContract.FavoriteEntry;

/**
 * For TRUSTUDIO
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.trust.android.popmovies.fragments
 * or see link for more detail http://bibucket.org/iwanz98/pop-movies
 */

public class DetailFragment extends BaseFragment implements View.OnClickListener{

    private Loader mLoader;

    private TrailerAdapter mAdapter;
    private ReviewAdapter rvAdapter;

    private SnackBar mSnackBar;
    private Button mAddFavorite;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private ProperRatingBar lowerRatingBar;
    private boolean isProgress = false;

    private int id;
    private String title;
    private String poster_path;
    private String release_date;
    private double vote_average;
    private String overview;
    private String data;

    private LinearLayout mainTrailer;
    private TextView ketTrailer;

    private LinearLayout mainReview;
    private TextView ketReview;
    
    private ImageView mThumbnail;
    private TextView mTitle;
    private TextView mReleaseDate;
    private TextView mRating;
    private TextView mOverView;

    private Trailer  mTrailer;
    private Review mReview;

    public static DetailFragment newInstance(int id, String title, String poster_path, String release_date,
                                             double vote_average, String overview, String data) {
        DetailFragment mFragment = new DetailFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(Cons.PACKAGE_PREFIX + "id", id);
        bundle.putString(Cons.PACKAGE_PREFIX + "title", title);
        bundle.putString(Cons.PACKAGE_PREFIX + "poster_path", poster_path);
        bundle.putString(Cons.PACKAGE_PREFIX + "release_date", release_date);
        bundle.putDouble(Cons.PACKAGE_PREFIX + "vote_average", vote_average);
        bundle.putString(Cons.PACKAGE_PREFIX + "overview", overview);
        bundle.putString(Cons.PACKAGE_PREFIX + "data", data);
        mFragment.setArguments(bundle);

        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        getActionbar().setTitle("Detail Movie");
        getActionbar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, null);

        initViews(view);

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_share, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                if (mTrailer != null) {
                   shareTextUrl(mTrailer.name, mTrailer.key);
                } else {
                    Toast.makeText(getActivity(), "Sorry, the trailer is not available! ", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initViews(View view) {
        id = getArguments().getInt(Cons.PACKAGE_PREFIX + "id");
        title = getArguments().getString(Cons.PACKAGE_PREFIX + "title");
        poster_path = getArguments().getString(Cons.PACKAGE_PREFIX + "poster_path");
        release_date = getArguments().getString(Cons.PACKAGE_PREFIX + "release_date");
        vote_average = getArguments().getDouble(Cons.PACKAGE_PREFIX + "vote_average");
        overview = getArguments().getString(Cons.PACKAGE_PREFIX + "overview");
        data = getArguments().getString(Cons.PACKAGE_PREFIX + "data");

        mAddFavorite = (Button) view.findViewById(R.id.btnFavorite);

        ketTrailer = (TextView) view.findViewById(R.id.ket_trailer);
        mainTrailer = (LinearLayout) view.findViewById(R.id.mainTrailer);

        ketReview = (TextView) view.findViewById(R.id.ket_review);
        mainReview = (LinearLayout) view.findViewById(R.id.mainReview);

        mLoader = new Loader(getActivity());

        mSnackBar = (SnackBar) view.findViewById(R.id.snackbar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView2 = (RecyclerView) view.findViewById(R.id.recycler_view2);

        mThumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        mTitle = (TextView) view.findViewById(R.id.title);
        mReleaseDate = (TextView) view.findViewById(R.id.release_date);
        mRating = (TextView) view.findViewById(R.id.rating);
        mOverView = (TextView) view.findViewById(R.id.overview);
        lowerRatingBar = (ProperRatingBar) view.findViewById(R.id.lowerRatingBar);

        String aksi = getSharedPreferences().getString("aksi", "");
        Uri uri = Uri.fromFile(new File(poster_path));

        if (aksi.equals("Favorite Movies")) {
            Picasso.with(getActivity())
                    .load(uri)
                    .placeholder(R.drawable.poster_default)
                    .into(mThumbnail);
        } else {
            Picasso.with(getActivity())
                    .load(poster_path)
                    .placeholder(R.drawable.poster_default)
                    .into(mThumbnail);
        }

        mTitle.setText(title);
        mReleaseDate.setText(Utils.getInvertDate(release_date));
        mRating.setText(String.valueOf(vote_average) + "/ 10");
        mOverView.setText(overview);
        lowerRatingBar.setRating(((int) vote_average)/2);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        getTrailer(true);

        if (data.equals("db"))
            mAddFavorite.setText("Remove from Favorites");

        mAddFavorite.setOnClickListener(this);
        Debug.i(mAddFavorite.getText().toString());

    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


    private void getTrailer(boolean progress) {
        if (!isNetworkOn(getActivity())) {
            showSnackbar(mSnackBar, R.string.tidak_ada_koneksi);
        } else {
            try {
                mLoader.getTrailer(String.valueOf(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getReview(boolean progress) {
        if (!isNetworkOn(getActivity())) {
            showSnackbar(mSnackBar, R.string.tidak_ada_koneksi);
        } else {
            try {
                mLoader.getReview(String.valueOf(id));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Subscribe
    public void onTrailerEvent(TrailerEvent event) {
        if (event.status.equals("OK")) {

            if (event.trailerArrayList.size() > 0){

                mTrailer =event.trailerArrayList.get(0);

                mAdapter = new TrailerAdapter(getActivity(), event.trailerArrayList);
                mRecyclerView.setAdapter(mAdapter);
                mainTrailer.setVisibility(View.VISIBLE);
                ketTrailer.setVisibility(View.VISIBLE);
            } else {
                mainTrailer.setVisibility(View.GONE);
                ketTrailer.setVisibility(View.GONE);
            }

            getReview(true);
        } else if (event.status.equals("ERROR")) {
            mainTrailer.setVisibility(View.GONE);
            ketTrailer.setVisibility(View.GONE);
            showSnackbar(mSnackBar, event.message);
        }
    }

    @Subscribe
    public void onReviewEvent(ReviewEvent event) {
        if (event.status.equals("OK")) {

            if (event.reviewArrayList.size() > 0){

                mReview = event.reviewArrayList.get(0);

                rvAdapter = new ReviewAdapter(getActivity(), event.reviewArrayList);
                mRecyclerView2.setAdapter(rvAdapter);
                mainReview.setVisibility(View.VISIBLE);
                ketReview.setVisibility(View.VISIBLE);
            } else {
                mainReview.setVisibility(View.GONE);
                ketReview.setVisibility(View.GONE);
            }

            getReview(true);
        } else if (event.status.equals("ERROR")) {
            mainReview.setVisibility(View.GONE);
            ketReview.setVisibility(View.GONE);
            showSnackbar(mSnackBar, event.message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnFavorite:
                if (mAddFavorite.getText().toString().equals("Remove from Favorites")){
                    removeFavorites();
                } else {
                    new DownloadPosterFromURL().execute(poster_path);
                }
                break;
        }
    }

    class DownloadPosterFromURL extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                URL url = new URL(f_url[0]);
                URLConnection conection = url.openConnection();
                conection.connect();
                // getting file length
                int lenghtOfFile = conection.getContentLength();

                // input stream to read file - with 8k buffer
                InputStream input = new BufferedInputStream(url.openStream(), 8192);

                File vidDirectory = new File("/sdcard/PopMovies/");
                vidDirectory.mkdirs();

                // create a File object for the output file
                File outputFile = new File(vidDirectory, title + ".jpg");

                FileOutputStream output = new FileOutputStream(outputFile);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));

                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

                Debug.i(f_url[0].toString());
            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String posterPath = Environment.getExternalStorageDirectory().toString() + "/PopMovies/"+ title +".jpg";
            addFavorites(posterPath);
        }
    }

    private void shareTextUrl(String title, String link) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        share.putExtra(Intent.EXTRA_SUBJECT, title);
        share.putExtra(Intent.EXTRA_TEXT, link);

        startActivity(Intent.createChooser(share, "Share Link!"));
    }

    private void addFavorites(String path) {
        // Add a new birthday record
        ContentValues values = new ContentValues();

        values.put(FavoriteEntry.COLUMN_ID,id);
        values.put(FavoriteEntry.COLUMN_TITLE,title);
        values.put(FavoriteEntry.COLUMN_POSTER,path);
        values.put(FavoriteEntry.COLUMN_SYNOPSIS,overview);
        values.put(FavoriteEntry.COLUMN_USER_RATING,vote_average);
        values.put(FavoriteEntry.COLUMN_RELEASE_DATE,release_date);

        getActivity().getContentResolver().insert(FavoriteProvider.CONTENT_URI, values);

        Toast.makeText(getActivity(),"Successfully added to the favorites.", Toast.LENGTH_LONG).show();

        mAddFavorite.setText("Remove from Favorites");
    }

    public void removeFavorites() {
        String URL = "content://com.trust.android.popmovies.favoriteProvider/favorites/"+id;
        Uri favorites = Uri.parse(URL);
        getActivity().getContentResolver().delete(favorites, null, null);

        String posterPath = Environment.getExternalStorageDirectory().toString() + "/PopMovies/"+ title +".jpg";

        Debug.i(posterPath);

        File file = new File(posterPath);

        if(file.exists()){
            try {
                file.getCanonicalFile().delete();
                if(file.exists()){
                    getActivity().deleteFile(file.getName());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Toast.makeText(getActivity(),"Successfully remove from favorites", Toast.LENGTH_LONG).show();

        mAddFavorite.setText("Mark as Favorites");
    }



}

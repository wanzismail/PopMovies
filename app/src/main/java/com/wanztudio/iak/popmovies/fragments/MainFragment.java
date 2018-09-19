package com.wanztudio.iak.popmovies.fragments;

import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.rey.material.widget.SnackBar;
import com.wanztudio.iak.popmovies.R;
import com.wanztudio.iak.popmovies.adapters.FavoriteAdapter;
import com.wanztudio.iak.popmovies.adapters.MovieAdapter;
import com.wanztudio.iak.popmovies.data.FavoriteContract;
import com.wanztudio.iak.popmovies.models.HighestRatedEvent;
import com.wanztudio.iak.popmovies.models.Movie;
import com.wanztudio.iak.popmovies.models.PopularEvent;
import com.wanztudio.iak.popmovies.networks.Loader;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.fragments
 * or see link for more detail https://github.com/iwanz98/PopMovies
 */

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private Loader mLoader;
    private GridView gridView;

    private MovieAdapter mAdapter;
    private FavoriteAdapter mFavoriteAdapter;
    private ArrayList<Movie> mItems;
    private ArrayAdapter<String> sortAdapter;

    private AVLoadingIndicatorView mProgressView;
    private SnackBar mSnackBar;
    private ListView mListView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private TextView mError;

    private boolean isProgress = false;

    private static FragmentTransaction mFragmentTransaction;

    public static MainFragment newInstance() {
        MainFragment mFragment = new MainFragment();
        return mFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);

        initViews(view);

        return view;
    }

    private void initViews(View view) {
        mFragmentTransaction = getFragmentManager().beginTransaction();

        mLoader = new Loader(getActivity());

        mSnackBar = (SnackBar) view.findViewById(R.id.snackbar);
        mProgressView = (AVLoadingIndicatorView) view.findViewById(R.id.progress_view);
        mListView = (ListView) view.findViewById(R.id.list_view);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mError = (TextView) view.findViewById(R.id.error_jaringan);
        gridView = (GridView) view.findViewById(R.id.gridView);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        String aksi = getSharedPreferences().getString("aksi", "Popular Movie");
        if (aksi.equals("Popular Movie"))
            getPopular(true);
        else if (aksi.equals("Highest Rated Movies"))
            getHighestRated(true);
        else if (aksi.equals("Favorite Movies"))
            showFavorites();

    }

    @Override
    public void onResume() {
        super.onResume();

        getActionbar().setTitle("Pop Movies");
        getActionbar().setDisplayHomeAsUpEnabled(false);

        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    private void setProgressView(boolean isProgres) {
        if (isProgres) {
            isProgress = true;
            mProgressView.setVisibility(View.VISIBLE);
        } else {
            isProgress = false;
            mProgressView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_sort, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:

                AlertDialog.Builder alertSort = new AlertDialog.Builder(getActivity());
                alertSort.setTitle("Show Movies by:");

                sortAdapter = new ArrayAdapter(getActivity(), R.layout.list_item_sort);
                sortAdapter.add("Popular Movies");
                sortAdapter.add("Highest Rated Movies");
                sortAdapter.add("Favorite Movies");

                alertSort.setAdapter(sortAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSharedPreferences().edit().putString("aksi", sortAdapter.getItem(which)).commit();
                        mSwipeRefreshLayout.setRefreshing(true);
                        if (which == 0)
                            getPopular(false);
                        else if (which == 1)
                            getHighestRated(false);
                        else if (which == 2)
                            showFavorites();
                    }
                });
                alertSort.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getPopular(boolean progress) {
        if (!isNetworkOn(getActivity())) {
            if (isProgress) setProgressView(true);
            if (!progress) mSwipeRefreshLayout.setRefreshing(false);

            showSnackbar(mSnackBar, R.string.tidak_ada_koneksi);
        } else {
            try {
                if (progress) setProgressView(true);

                mLoader.getPopular("popularity.desc");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getHighestRated(boolean progress) {
        if (!isNetworkOn(getActivity())) {
            if (isProgress) setProgressView(true);
            if (!progress) mSwipeRefreshLayout.setRefreshing(false);

            showSnackbar(mSnackBar, R.string.tidak_ada_koneksi);
        } else {
            try {
                if (progress) setProgressView(true);

                mLoader.getHighestRated("vote_average.desc");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe
    public void onPopularEvent(PopularEvent event) {
        setProgressView(false);
        mSwipeRefreshLayout.setRefreshing(false);

        if (event.status.equals("OK")) {
            mAdapter = new MovieAdapter(getActivity(), this, event.movieArrayList);
            mListView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            gridView.setAdapter(mAdapter);
            mError.setVisibility(View.GONE);
        } else if (event.status.equals("ERROR")) {
            showSnackbar(mSnackBar, event.message);
            mError.setVisibility(View.VISIBLE);
            mError.setText("Failed to load data");
        }
    }

    @Subscribe
    public void onHighestRatedEvent(HighestRatedEvent event) {
        setProgressView(false);
        mSwipeRefreshLayout.setRefreshing(false);

        if (event.status.equals("OK")) {
            mAdapter = new MovieAdapter(getActivity(), this, event.movieArrayList);
            mListView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            gridView.setAdapter(mAdapter);
            mError.setVisibility(View.GONE);
        } else if (event.status.equals("ERROR")) {
            showSnackbar(mSnackBar, event.message);
            mError.setVisibility(View.VISIBLE);
            mError.setText("Failed to load data");
        }
    }

    @Override
    public void onRefresh() {
        String aksi = getSharedPreferences().getString("aksi", "");
        if (aksi.equals("Popular Movie"))
            getPopular(false);
        else if (aksi.equals("Highest Rated Movies"))
            getHighestRated(false);
        else if (aksi.equals("Favorite Movies"))
            showFavorites();

    }

    public void viewDetail(Movie item) {
        mFragmentTransaction.replace(R.id.content, new DetailFragment().newInstance(item.id, item.title, item.poster_path,
                item.release_date, item.vote_average, item.overview, "json"));
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    public static void viewDetail(Cursor c) {
        mFragmentTransaction.replace(R.id.content, new DetailFragment().newInstance(c.getInt(FavoriteContract.COLUMN_ID),
                c.getString(FavoriteContract.COLUMN_TITLE), c.getString(FavoriteContract.COLUMN_POSTER),
                c.getString(FavoriteContract.COLUMN_RELEASE_DATE), c.getDouble(FavoriteContract.COLUMN_USER_RATING),
                c.getString(FavoriteContract.COLUMN_SYNOPSIS), "db"));
        mFragmentTransaction.addToBackStack(null);
        mFragmentTransaction.commit();
    }

    private void showFavorites() {
        // Show all the birthdays sorted by friend's name
        String URL = "content://com.wanztudio.iak.popmovies.favoriteProvider/favorites";
        Uri favorites = Uri.parse(URL);
        Cursor c = getActivity().getContentResolver().query(favorites, null, null, null, null);

        if (!c.moveToFirst()) {
            mListView.setVisibility(View.GONE);
            gridView.setVisibility(View.GONE);
            mError.setVisibility(View.VISIBLE);
            mError.setText("No favorites added");
        } else {
            gridView.setVisibility(View.GONE);
            mError.setVisibility(View.GONE);
            mFavoriteAdapter = new FavoriteAdapter(getActivity(), c, 0);

            mListView.setVisibility(View.VISIBLE);
            mListView.setAdapter(mFavoriteAdapter);
        }

        setProgressView(false);
        mSwipeRefreshLayout.setRefreshing(false);
    }
}

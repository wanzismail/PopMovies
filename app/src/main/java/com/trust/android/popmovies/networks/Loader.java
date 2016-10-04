package com.trust.android.popmovies.networks;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.trust.android.popmovies.models.HighestRatedEvent;
import com.trust.android.popmovies.models.Movie;
import com.trust.android.popmovies.models.PopularEvent;
import com.trust.android.popmovies.models.Review;
import com.trust.android.popmovies.models.ReviewEvent;
import com.trust.android.popmovies.models.Trailer;
import com.trust.android.popmovies.models.TrailerEvent;
import com.trust.android.popmovies.utils.Cons;
import com.trust.android.popmovies.utils.Debug;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * For TRUSTUDIO
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.trust.android.popmovies.networks
 * or see link for more detail http://bibucket.org/iwanz98/pop-movies
 */

public class Loader {
    private Context context;
    private SignedUrl signedUrl;

    public Loader(Context context) {
        this.context = context;
    }

    public void getPopular(String data) throws Exception {
        String requestUri = Cons.URL_DISCOVERY_MOVIES;
        signedUrl = new SignedUrl(context, requestUri);
        signedUrl.addParam("sort_by", data);
        signedUrl.addParam("api_key", Cons.API_KEY);

        Debug.i("Url Data ", signedUrl.getSignedUrl());

        Connection.get(signedUrl.getSignedUrl(), new JsonHttpResponseHandler() {
            ArrayList<Movie> movieArrayList = new ArrayList();

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {

                    JSONArray popularArray = response.getJSONArray("results");
                    for (int i = 0; i < popularArray.length()-2; i++) {
                        JSONObject movieObj = popularArray.getJSONObject(i);

                        Movie movie = new Movie();

                        movie.id = movieObj.getInt("id");
                        movie.title = movieObj.getString("title");
                        movie.poster_path = Cons.URL_THUMBNAIL_MOVIES + movieObj.getString("poster_path");
                        movie.release_date = movieObj.getString("release_date");
                        movie.vote_average = movieObj.getDouble("vote_average");
                        movie.overview = movieObj.getString("overview");

                        movieArrayList.add(movie);
                    }
                    EventBus.getDefault().post(new PopularEvent("OK", movieArrayList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                throwable.printStackTrace();

                try {
                    String message = errorResponse.getString("status_message");
                    EventBus.getDefault().post(new PopularEvent("ERROR", message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getHighestRated(String data) throws Exception {
        String requestUri = Cons.URL_DISCOVERY_MOVIES;
        signedUrl = new SignedUrl(context, requestUri);
        signedUrl.addParam("certification_country","US");
        signedUrl.addParam("certification", "R");
        signedUrl.addParam("sort_by", data);
        signedUrl.addParam("api_key", Cons.API_KEY);

        Debug.i("Url Data ", signedUrl.getSignedUrl());

        Connection.get(signedUrl.getSignedUrl(), new JsonHttpResponseHandler() {
            ArrayList<Movie> movieArrayList = new ArrayList();

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {

                    JSONArray popularArray = response.getJSONArray("results");
                    for (int i = 0; i < popularArray.length()-2; i++) {
                        JSONObject movieObj = popularArray.getJSONObject(i);

                        Movie movie = new Movie();

                        movie.id = movieObj.getInt("id");
                        movie.title = movieObj.getString("title");
                        movie.poster_path = Cons.URL_THUMBNAIL_MOVIES + movieObj.getString("poster_path");
                        movie.release_date = movieObj.getString("release_date");
                        movie.vote_average = movieObj.getDouble("vote_average");
                        movie.overview = movieObj.getString("overview");

                        movieArrayList.add(movie);
                    }
                    EventBus.getDefault().post(new HighestRatedEvent("OK", movieArrayList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                throwable.printStackTrace();

                try {
                    String message = errorResponse.getString("status_message");
                    EventBus.getDefault().post(new HighestRatedEvent("ERROR", message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getTrailer(String data) throws Exception {
        String requestUri = Cons.URL_TRAILER_MOVIES + data + "/videos";
        signedUrl = new SignedUrl(context, requestUri);
        signedUrl.addParam("api_key", Cons.API_KEY);

        Debug.i("Url Data ", signedUrl.getSignedUrl());

        Connection.get(signedUrl.getSignedUrl(), new JsonHttpResponseHandler() {
            ArrayList<Trailer> trailerArrayList = new ArrayList();

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {

                    JSONArray trailerArray = response.getJSONArray("results");
                    for (int i = 0; i < trailerArray.length(); i++) {
                        JSONObject trailerObj = trailerArray.getJSONObject(i);

                        Trailer trailer = new Trailer();

                        trailer.name = trailerObj.getString("name");
                        trailer.key = "https://www.youtube.com/watch?v=" + trailerObj.getString("key");

                        trailerArrayList.add(trailer);
                    }
                    EventBus.getDefault().post(new TrailerEvent("OK", trailerArrayList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                throwable.printStackTrace();

                try {
                    String message = errorResponse.getString("status_message");
                    EventBus.getDefault().post(new TrailerEvent("ERROR", message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getReview(String data) throws Exception {
        String requestUri = Cons.URL_TRAILER_MOVIES + data + "/reviews";
        signedUrl = new SignedUrl(context, requestUri);
        signedUrl.addParam("api_key", Cons.API_KEY);

        Debug.i("Url Data ", signedUrl.getSignedUrl());

        Connection.get(signedUrl.getSignedUrl(), new JsonHttpResponseHandler() {
            ArrayList<Review> reviewArrayList = new ArrayList();

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {

                    JSONArray reviewArray = response.getJSONArray("results");
                    for (int i = 0; i < reviewArray.length(); i++) {
                        JSONObject reviewObj = reviewArray.getJSONObject(i);

                        Review review = new Review();

                        review.author = reviewObj.getString("author");
                        review.content = reviewObj.getString("content");

                        reviewArrayList.add(review);
                    }
                    EventBus.getDefault().post(new ReviewEvent("OK", reviewArrayList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                throwable.printStackTrace();

                try {
                    String message = errorResponse.getString("status_message");
                    EventBus.getDefault().post(new ReviewEvent("ERROR", message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
package com.trust.android.popmovies.networks;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * For TRUSTUDIO
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.trust.android.popmovies.networks
 * or see link for more detail http://bibucket.org/iwanz98/pop-movies
 */

public class Connection {
    private static final AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(url, params, (ResponseHandlerInterface) responseHandler);
    }

    public static void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(context, url, params, responseHandler);
    }

    public static void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.get(url, params, (ResponseHandlerInterface) responseHandler);
    }

    public static void get(Context context, String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.get(context, url, params, responseHandler);
    }

    public static void get(String url, JsonHttpResponseHandler responseHandler) {
        client.get(url, responseHandler);
    }

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.get(url, responseHandler);
    }

    public static void get(Context context, String url, JsonHttpResponseHandler responseHandler) {
        client.get(context, url, (ResponseHandlerInterface) responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    public static void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(context, url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.post(url, params, responseHandler);
    }

    public static void post(String url, JsonHttpResponseHandler responseHandler) {
        client.post(url, responseHandler);
    }

    public static void post(Context context, String url, HttpEntity httpEntity, String contentType, JsonHttpResponseHandler responseHandler) {
        client.post(context, url, httpEntity, contentType, responseHandler);
    }

    public static void postProxy(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.setProxy("localhost", 80);
        client.post(url, params, responseHandler);
    }

    public static void post(Context context, String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        client.post(context, url, params, responseHandler);
    }

    public static void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler, int value) {
        client.setTimeout(value);
        client.post(url, params, responseHandler);
    }
}

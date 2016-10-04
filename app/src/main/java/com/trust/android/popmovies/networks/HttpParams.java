package com.trust.android.popmovies.networks;

import java.util.Iterator;
import java.util.TreeMap;

/**
 * For TRUSTUDIO
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.trust.android.popmovies.networks
 * or see link for more detail http://bibucket.org/iwanz98/pop-movies
 */

public class HttpParams {
    private TreeMap<String, HttpValues> mParams;

    public HttpParams() {
        mParams = new TreeMap();
    }

    public void put(String key, HttpValues values) {
        if (!mParams.containsKey(key)) {
            mParams.put(key, values);
        }
    }

    public HttpValues get(String key) {
        return mParams.containsKey(key) ? (HttpValues) mParams.get(key) : null;
    }

    public boolean containsKey(String key) {
        return mParams.containsKey(key);
    }

    public String getQueryString() {
        if (mParams.size() == 0) {
            return "";
        }
        StringBuffer querySb = new StringBuffer();
        int size = mParams.size();
        int i = 0;
        for (String key : mParams.keySet()) {
            HttpValues values = mParams.get(key);
            if (values == null) {
                querySb.append(key + "=");
            } else if (values.isEmpty()) {
                querySb.append(key + "=");
            } else {
                Iterator<String> iterator = values.getAll().iterator();
                while (iterator.hasNext()) {
                    querySb.append(key + "=" + URIUtil.encode(iterator.next()));
                    if (iterator.hasNext()) {
                        querySb.append("&");
                    }
                }
            }
            if (i != size - 1) {
                querySb.append("&");
            }
            i++;
        }
        return "?" + querySb.toString();
    }
}

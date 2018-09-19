package com.wanztudio.iak.popmovies.networks;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.networks
 * or see link for more detail https://github.com/iwanz98/PopMovies
 */

public class URIUtil {
    public static String encode(String input) {
        StringBuilder resultStr = new StringBuilder();
        for (char ch : input.toCharArray()) {
            if (isUnSafe(ch)) {
                resultStr.append('%');
                resultStr.append(toHex(ch / 16));
                resultStr.append(toHex(ch % 16));
            } else {
                resultStr.append(ch);
            }
        }
        return resultStr.toString();
    }

    private static char toHex(int ch) {
        return (char) (ch < 10 ? '0' + ch : 'A' + ch - 10);
    }

    private static boolean isUnSafe(char ch) {
        if (ch > 128 || ch < 0)
            return true;
        return " %*$&+,/:;=?@<>#%'\n".indexOf(ch) >= 0;
    }
}

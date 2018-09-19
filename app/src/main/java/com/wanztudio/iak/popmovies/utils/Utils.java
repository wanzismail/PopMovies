package com.wanztudio.iak.popmovies.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * For LEARNING
 * Created by Ridwan Ismail on 05 Mei 2016
 * You can contact me at : ismail.ridwan98@gmail.com
 * -------------------------------------------------
 * POP MOVIES
 * com.wanztudio.iak.popmovies.utils
 * or see link for more detail https://github.com/iwanz98/PopMovies
 */


public class Utils {

    public static String getDetailDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getInvertDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "MMMM dd, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String toRupiahFormat(String nominal) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

        double value = Double.valueOf(nominal);

        String res = formatter.format(value);

        res = res.replace(",", "#");
        res = res.replace(".", ",");
        res = res.replace("#", ".");

        return (value < 0) ? res.replace("$", "Rp. -") : res.replace("$", "Rp. ");
    }
}

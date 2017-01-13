package com.onkarnene.upcomingmovies.utils;
/*
 * Created by Onkar Nene on 12-01-2017.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConfig {

    /**
     * @return true if device is currently connected to the internet (WiFi or Mobile Data) otherwise false
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

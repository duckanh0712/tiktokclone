package com.example.tiktukapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

public class Utils {
    public static boolean isNetworkAvailable(Context context) {
        if (context == null) {
            return false;
        } else {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) {
                return false;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Network network = connectivityManager.getActiveNetwork();
                if (network == null) {
                    return false;
                } else {
                    NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                    return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
                }
            } else {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                return networkInfo != null && networkInfo.isConnected();
            }
        }
    }

    public static class Validation {
        public static boolean isUsernameValid(String username) {
            if (TextUtils.isEmpty(username)) {
                return false;
            }
//        if (username.matches("")){
//            return false;
//        }
            return true;
        }

        public static boolean isEmailValid(String email)  {
            if (TextUtils.isEmpty(email)) {
                return false;
            }
//        if (email.matches("")){
//            return false;
//        }
            return true;
        }

        public static boolean isPasswordValid(String password)  {
            if (TextUtils.isEmpty(password)) {
                return false;
            }
//        if (email.matches("")){
//            return false;
//        }
            return true;
        }
    }
}


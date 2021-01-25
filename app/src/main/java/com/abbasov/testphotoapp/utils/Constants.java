package com.abbasov.testphotoapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Patterns;

public class Constants {

    public static final String PICS_URL = "https://picsum.photos/v2/";
    public static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/";

    public static boolean isOnline() {
        try {
            int result = 0; // Returns connection type. 0: none; 1: mobile data; 2: wifi
            ConnectivityManager cm = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (cm != null) {
                    NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                    if (capabilities != null) {
                        if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            result = 2;
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            result = 1;
                        } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                            result = 3;
                        }
                    }
                }
            } else {
                if (cm != null) {
                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    if (activeNetwork != null) {
                        // connected to the internet
                        if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                            result = 2;
                        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                            result = 1;
                        } else if (activeNetwork.getType() == ConnectivityManager.TYPE_VPN) {
                            result = 3;
                        }
                    }
                }
            }
            return result != 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{6,}";
        return password.matches(pattern);
    }

}

package com.abbasov.testphotoapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.abbasov.testphotoapp.utils.Constants.PICS_URL;
import static com.abbasov.testphotoapp.utils.Constants.WEATHER_URL;

public class WeatherApiClient {

    private static Retrofit ourInstance;

    public static Retrofit getInstance() {
        if (ourInstance == null) {
            ourInstance = new Retrofit.Builder()
                    .baseUrl(WEATHER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return ourInstance;
    }
}

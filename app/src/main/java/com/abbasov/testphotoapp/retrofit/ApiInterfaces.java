package com.abbasov.testphotoapp.retrofit;

import com.abbasov.testphotoapp.retrofit.models.PicturesResponse;
import com.abbasov.testphotoapp.retrofit.models.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterfaces {

    @GET("list")
    Call<List<PicturesResponse>> getPictures(@Query("page") int page,
                                             @Query("limit") int limit);

    @GET("weather")
    Call<WeatherResponse> auth(@Query("appid") String appid,
                               @Query("q") String q,
                               @Query("lang") String lang,
                               @Query("units") String units);

}

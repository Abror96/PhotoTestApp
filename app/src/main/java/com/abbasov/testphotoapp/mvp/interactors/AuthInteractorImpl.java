package com.abbasov.testphotoapp.mvp.interactors;

import android.util.Log;

import com.abbasov.testphotoapp.R;
import com.abbasov.testphotoapp.mvp.contracts.AuthContract;
import com.abbasov.testphotoapp.retrofit.ApiInterfaces;
import com.abbasov.testphotoapp.retrofit.WeatherApiClient;
import com.abbasov.testphotoapp.retrofit.models.PicturesResponse;
import com.abbasov.testphotoapp.retrofit.models.WeatherResponse;
import com.abbasov.testphotoapp.utils.App;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthInteractorImpl implements AuthContract.Interactor {

    private ApiInterfaces apiService =
            WeatherApiClient.getInstance().create(ApiInterfaces.class);
    private String TAG = "LOGGERR weather";

    @Override
    public void auth(OnFinishedListener onFinishedListener, String appId, String cityName, String lang, String units) {
        Call<WeatherResponse> auth = apiService.auth(
                appId,
                cityName,
                lang,
                units
        );

        auth.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body() != null) {
                    onFinishedListener.authFinished(response.body());
                } else {
                    onFinishedListener.failure(App.getContext().getString(R.string.error_has_occurred) + statusCode);
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedListener.noInternet();
            }
        });
    }
}

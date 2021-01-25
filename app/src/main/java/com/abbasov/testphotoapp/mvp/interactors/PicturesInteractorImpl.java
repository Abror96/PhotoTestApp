package com.abbasov.testphotoapp.mvp.interactors;

import android.util.Log;

import com.abbasov.testphotoapp.R;
import com.abbasov.testphotoapp.mvp.contracts.PicturesContract;
import com.abbasov.testphotoapp.retrofit.ApiInterfaces;
import com.abbasov.testphotoapp.retrofit.PicsApiClient;
import com.abbasov.testphotoapp.retrofit.models.PicturesResponse;
import com.abbasov.testphotoapp.utils.App;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PicturesInteractorImpl implements PicturesContract.Interactor {

    private ApiInterfaces apiService =
            PicsApiClient.getInstance().create(ApiInterfaces.class);
    private String TAG = "LOGGERR pics";

    @Override
    public void getPictures(OnFinishedListener onFinishedListener, int page, int limit) {
        Call<List<PicturesResponse>> getPictures = apiService.getPictures(
                page,
                limit
        );

        getPictures.enqueue(new Callback<List<PicturesResponse>>() {
            @Override
            public void onResponse(Call<List<PicturesResponse>> call, Response<List<PicturesResponse>> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body() != null) {
                    onFinishedListener.getPicturesFinished(response.body());
                } else {
                    onFinishedListener.failure(App.getContext().getString(R.string.error_has_occurred) + statusCode);
                }
            }

            @Override
            public void onFailure(Call<List<PicturesResponse>> call, Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedListener.noInternet();
            }
        });
    }
}

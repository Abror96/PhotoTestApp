package com.abbasov.testphotoapp.mvp.contracts;

import com.abbasov.testphotoapp.retrofit.models.WeatherResponse;

public interface AuthContract {

    interface View {

        void authSuccess(WeatherResponse weatherResponse);

        void showSnackbar(String message);

        void noInternet();

    }

    interface Presenter {

        void destroy();

        void authCalled(String appId, String cityName, String lang, String units);

    }

    interface Interactor {

        interface OnFinishedListener {

            void authFinished(WeatherResponse weatherResponse);

            void failure(String message);

            void noInternet();

        }

        void auth(OnFinishedListener onFinishedListener, String appId, String cityName, String lang, String units);

    }

}

package com.abbasov.testphotoapp.mvp.presenters;

import com.abbasov.testphotoapp.mvp.contracts.AuthContract;
import com.abbasov.testphotoapp.retrofit.models.WeatherResponse;

import static com.abbasov.testphotoapp.utils.Constants.isOnline;

public class AuthPresenterImpl implements AuthContract.Presenter, AuthContract.Interactor.OnFinishedListener {

    private AuthContract.View view;
    private AuthContract.Interactor interactor;

    public AuthPresenterImpl(AuthContract.View view, AuthContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void authCalled(String appId, String cityName, String lang, String units) {
        if (isOnline()) {
            interactor.auth(this, appId, cityName, lang, units);
        } else {
            noInternet();
        }
    }

    @Override
    public void authFinished(WeatherResponse weatherResponse) {
        if (view != null) {
            view.authSuccess(weatherResponse);
        }
    }

    @Override
    public void failure(String message) {
        if (view != null) {
            view.showSnackbar(message);
        }
    }

    @Override
    public void noInternet() {
        if (view != null) {
            view.noInternet();
        }
    }
}

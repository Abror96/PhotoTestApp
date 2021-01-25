package com.abbasov.testphotoapp.mvp.presenters;

import com.abbasov.testphotoapp.mvp.contracts.PicturesContract;
import com.abbasov.testphotoapp.retrofit.models.PicturesResponse;

import java.util.List;

import static com.abbasov.testphotoapp.utils.Constants.isOnline;

public class PicturesPresenterImpl implements PicturesContract.Presenter, PicturesContract.Interactor.OnFinishedListener {

    private PicturesContract.View view;
    private PicturesContract.Interactor interactor;

    public PicturesPresenterImpl(PicturesContract.View view, PicturesContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void getPicturesCalled(int page, int limit) {
        if (isOnline()) {
            interactor.getPictures(this, page, limit);
        } else {
            noInternet();
        }
    }

    @Override
    public void getPicturesFinished(List<PicturesResponse> pictures) {
        if (view != null) {
            view.hideProgress();
            view.getPicturesSuccess(pictures);
        }
    }

    @Override
    public void failure(String message) {
        if (view != null) {
            view.hideProgress();
            view.showSnackbar(message);
        }
    }

    @Override
    public void noInternet() {
        if (view != null) {
            view.hideProgress();
            view.noInternet();
        }
    }
}

package com.abbasov.testphotoapp.mvp.contracts;

import com.abbasov.testphotoapp.retrofit.models.PicturesResponse;

import java.util.List;

public interface PicturesContract {

    interface View {

        void getPicturesSuccess(List<PicturesResponse> pictures);

        void hideProgress();

        void showSnackbar(String message);

        void noInternet();

    }

    interface Presenter {

        void destroy();

        void getPicturesCalled(int page, int limit);

    }

    interface Interactor {

        interface OnFinishedListener {

            void getPicturesFinished(List<PicturesResponse> pictures);

            void failure(String message);

            void noInternet();

        }

        void getPictures(OnFinishedListener onFinishedListener, int page, int limit);

    }

}

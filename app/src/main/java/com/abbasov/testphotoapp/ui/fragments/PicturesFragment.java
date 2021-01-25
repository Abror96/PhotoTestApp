package com.abbasov.testphotoapp.ui.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.abbasov.testphotoapp.R;
import com.abbasov.testphotoapp.databinding.FragmentPicturesBinding;
import com.abbasov.testphotoapp.mvp.contracts.PicturesContract;
import com.abbasov.testphotoapp.mvp.interactors.PicturesInteractorImpl;
import com.abbasov.testphotoapp.mvp.presenters.PicturesPresenterImpl;
import com.abbasov.testphotoapp.retrofit.models.PicturesResponse;
import com.abbasov.testphotoapp.ui.adapters.PicturesAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.abbasov.testphotoapp.utils.Constants.isOnline;

public class PicturesFragment extends Fragment implements PicturesContract.View {

    private FragmentPicturesBinding binding;
    private AlertDialog noInternetAlert;

    private PicturesContract.Presenter presenter;
    private List<PicturesResponse> picturesList;
    private PicturesAdapter adapter;
    private int page = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pictures, container, false);

        presenter = new PicturesPresenterImpl(this, new PicturesInteractorImpl());

        reInitRecyclerView();
        reloadData();

        binding.pullToRefresh.setOnRefreshListener(this::reloadData);

        return binding.getRoot();
    }

    private void reInitRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (picturesList.get(position) == null) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        binding.picturesRecycler.setLayoutManager(gridLayoutManager);

        picturesList = new ArrayList<>();
        adapter = new PicturesAdapter(picturesList, binding.picturesRecycler);
        binding.picturesRecycler.setAdapter(adapter);

        adapter.setOnLoadMoreListener(() -> {
            picturesList.add(null);
            adapter.notifyItemInserted(picturesList.size() - 1);
            new Handler().postDelayed(() -> {
                try {
                    picturesList.remove(picturesList.size() - 1);
                    adapter.notifyItemRemoved(picturesList.size());
                } catch (Exception ignored) {
                }
                page = page + 1;
                presenter.getPicturesCalled(page, 10);
            }, 600);
        });
    }

    private void reloadData() {
        page = 1;
        if (picturesList.size() > 0) {
            reInitRecyclerView();
        }
        presenter.getPicturesCalled(page, 10);
    }

    @Override
    public void getPicturesSuccess(List<PicturesResponse> pictures) {
        if (pictures != null) {
            picturesList.addAll(pictures);
            adapter.notifyDataSetChanged();
            adapter.setLoaded();
        }
    }

    @Override
    public void hideProgress() {
        binding.pullToRefresh.setRefreshing(false);
        binding.progressBar1.setVisibility(View.GONE);
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void noInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.no_internet_layout, null);
        builder.setCancelable(false);
        builder.setView(constraintLayout);

        noInternetAlert = builder.create();
        noInternetAlert.show();

        constraintLayout.findViewById(R.id.open_settings).setOnClickListener(view -> {
            if (isOnline()) {
                if (picturesList == null) {
                    reInitRecyclerView();
                }
                reloadData();
                noInternetAlert.dismiss();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.destroy();
    }
}

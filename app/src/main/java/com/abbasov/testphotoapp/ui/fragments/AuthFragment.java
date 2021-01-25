package com.abbasov.testphotoapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.abbasov.testphotoapp.R;
import com.abbasov.testphotoapp.databinding.FragmentAuthBinding;
import com.abbasov.testphotoapp.mvp.contracts.AuthContract;
import com.abbasov.testphotoapp.mvp.interactors.AuthInteractorImpl;
import com.abbasov.testphotoapp.mvp.presenters.AuthPresenterImpl;
import com.abbasov.testphotoapp.retrofit.models.WeatherResponse;
import com.google.android.material.snackbar.Snackbar;

import static com.abbasov.testphotoapp.utils.Constants.isValidEmail;
import static com.abbasov.testphotoapp.utils.Constants.isValidPassword;

public class AuthFragment extends Fragment implements AuthContract.View {

    private FragmentAuthBinding binding;
    private AuthContract.Presenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false);

        presenter = new AuthPresenterImpl(this, new AuthInteractorImpl());

        binding.signIn.setOnClickListener(view -> {
            String login = binding.etLogin.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            binding.loginWrapper.setError(null);
            binding.passwordWrapper.setError(null);
            if (!login.isEmpty() && !password.isEmpty()) {
                if (isValidEmail(login)) {
                    if (isValidPassword(password)) {
                        binding.progressBar1.setVisibility(View.VISIBLE);
                        presenter.authCalled("c35880b49ff95391b3a6d0edd0c722eb", "санкт-петербург", "ru", "metric");
                    } else {
                        Snackbar.make(binding.getRoot(), R.string.password_error_alert, Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(binding.getRoot(), R.string.enter_valid_email, Snackbar.LENGTH_LONG).show();
                }
            } else {
                if (login.isEmpty())
                    binding.loginWrapper.setError(getString(R.string.required_field));
                if (password.isEmpty())
                    binding.passwordWrapper.setError(getString(R.string.required_field));
            }
        });

        return binding.getRoot();
    }

    @Override
    public void authSuccess(WeatherResponse weatherResponse) {
        showSnackbar(
                weatherResponse.getName() +
                        ", температура: " + weatherResponse.getMain().getTemp() +
                        " °C, облачность: " + weatherResponse.getClouds().getAll() +
                        "%, влажность: " + weatherResponse.getMain().getHumidity() + "%");
    }

    @Override
    public void showSnackbar(String message) {
        binding.progressBar1.setVisibility(View.INVISIBLE);
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void noInternet() {
        showSnackbar(getString(R.string.no_internet));
    }
}

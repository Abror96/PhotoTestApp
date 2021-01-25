package com.abbasov.testphotoapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.abbasov.testphotoapp.R;
import com.abbasov.testphotoapp.databinding.ActivityMainBinding;
import com.abbasov.testphotoapp.ui.fragments.AuthFragment;
import com.abbasov.testphotoapp.ui.fragments.PicturesFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Fragment picturesFragment;
    private Fragment authFragment;
    private Fragment activeFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        picturesFragment = new PicturesFragment();
        authFragment = new AuthFragment();
        activeFragment = picturesFragment;
        fm = getSupportFragmentManager();

        initMainNav();

    }

    private void initMainNav() {
        fm.beginTransaction().add(R.id.main_container, authFragment, "auth").hide(authFragment).commit();
        fm.beginTransaction().add(R.id.main_container, picturesFragment, "pictures").commit();

        binding.mainNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_pictures:
                    fm.beginTransaction().hide(activeFragment).show(picturesFragment).commit();
                    activeFragment = picturesFragment;
                    return true;
                case R.id.nav_auth:
                    fm.beginTransaction().hide(activeFragment).show(authFragment).commit();
                    activeFragment = authFragment;
                    return true;
            }
            return false;
        });
    }
}
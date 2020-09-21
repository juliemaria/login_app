package com.example.ecobeeloginapp.ui.home.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.ecobeeloginapp.R;
import com.example.ecobeeloginapp.data.model.CatApiResponse;
import com.example.ecobeeloginapp.data.model.UserInfo;
import com.example.ecobeeloginapp.databinding.ActivityHomeBinding;
import com.example.ecobeeloginapp.ui.home.viewmodel.HomeviewModel;
import com.example.ecobeeloginapp.ui.login.view.LoginActivity;

public class HomeActivity extends AppCompatActivity {
    ActivityHomeBinding homeBinding;
    HomeviewModel homeviewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        homeviewModel = new ViewModelProvider(this).get(HomeviewModel.class);
        homeBinding.setHomeviewmodel(homeviewModel);
        homeBinding.setLifecycleOwner(this);
        getUserName();
        setObservable();
    }

    private void getUserName() {
        Bundle b = getIntent().getExtras();
        if (b != null && b.getParcelable(getString(R.string.use_details_key)) != null) {
            UserInfo userInfo;
            userInfo = b.getParcelable(getString(R.string.use_details_key));
            if (userInfo != null && userInfo.getDisplayName() != null) {
                setUserName(userInfo.getDisplayName());
            }
        } else {
            SharedPreferences sharedpreferences = getApplication().getSharedPreferences(getString(R.string.login_app_preference_key), Context.MODE_PRIVATE);
            String user =  sharedpreferences.getString(getString(R.string.user_name_key), null);
            if (!TextUtils.isEmpty(user))
            setUserName(user);
        }

    }

    private void setUserName(String userName) {
        String welcomeMessage = getString(R.string.welcome) +
                userName;
        homeBinding.textViewWelcome.setText(welcomeMessage);
    }

    private void setObservable() {
        homeviewModel.getCatApiResponseMutableLiveData().observe(this, new Observer<CatApiResponse>() {
            @Override
            public void onChanged(CatApiResponse catApiResponse) {
                if (catApiResponse != null &&
                        catApiResponse.getUrl() != null) {
                    Glide.with(HomeActivity.this).
                            load(catApiResponse.getUrl()).
                            into(homeBinding.imageviewCat);
                } else {
                    Toast.makeText(HomeActivity.this, R.string.image_load_error, Toast.LENGTH_LONG).show();
                }
            }
        });

        homeviewModel.progress.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean show) {
                if (show)
                    homeBinding.progressBar.setVisibility(View.VISIBLE);
                else
                    homeBinding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logoutUser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void logoutUser() {
        SharedPreferences sharedpreferences = getApplication().getSharedPreferences(getString(R.string.login_app_preference_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(getString(R.string.user_id_key));
        editor.commit();
        navigateBackToLoginScreen();
    }

    private void navigateBackToLoginScreen() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}

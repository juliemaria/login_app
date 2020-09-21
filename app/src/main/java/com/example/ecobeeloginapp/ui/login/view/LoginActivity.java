package com.example.ecobeeloginapp.ui.login.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecobeeloginapp.R;
import com.example.ecobeeloginapp.data.model.LoginAuthResult;
import com.example.ecobeeloginapp.data.model.UserInfo;
import com.example.ecobeeloginapp.databinding.ActivityLoginBinding;
import com.example.ecobeeloginapp.ui.home.view.HomeActivity;
import com.example.ecobeeloginapp.ui.login.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding loginBinding;
    LoginViewModel loginViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginBinding.setLoginviewmodel(loginViewModel);
        loginBinding.setLifecycleOwner(this);
        setObservable();
    }

    private void setObservable() {
        loginViewModel.getLoginAuthResultMutableLiveData().observe(this, new Observer<LoginAuthResult>() {
            @Override
            public void onChanged(LoginAuthResult loginAuthResult) {
                if (loginAuthResult.isAuthenticated()) {
                    navigateToHomeScreen(loginAuthResult);
                } else {
                    displayDialog(getString(R.string.login_failed));
                }
            }
        });

        loginViewModel.getValidCredentials().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                    displayDialog(message);
            }
        });

        loginViewModel.getProgress().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean show) {
                if (show)
                    loginBinding.progressBar.setVisibility(View.VISIBLE);
                else
                    loginBinding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void navigateToHomeScreen(LoginAuthResult loginAuthResult) {
        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
        if (loginAuthResult.getLoggedInUserInfo() != null) {
            homeIntent.putExtra(getString(R.string.use_details_key), loginAuthResult.getLoggedInUserInfo());
            saveUserIDInSharedPreference(loginAuthResult.getLoggedInUserInfo());
        }
        startActivity(homeIntent);
    }

    private void saveUserIDInSharedPreference(UserInfo userInfo) {
        SharedPreferences sharedpreferences = getApplication().getSharedPreferences(getString(R.string.login_app_preference_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if (!TextUtils.isEmpty(userInfo.getDisplayName()))
            editor.putString(getString(R.string.user_id_key), userInfo.getUserId());
        if (!TextUtils.isEmpty(userInfo.getDisplayName()))
            editor.putString(getString(R.string.user_name_key), userInfo.getDisplayName());
        editor.commit();
    }

    private void displayDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        loginBinding.password.getText().clear();
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
}

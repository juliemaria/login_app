package com.example.ecobeeloginapp.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ecobeeloginapp.R;
import com.example.ecobeeloginapp.ui.home.view.HomeActivity;
import com.example.ecobeeloginapp.ui.login.view.LoginActivity;

public class SplashActivity extends AppCompatActivity {
 /** Duration of wait **/
        private final int SPLASH_DISPLAY_LENGTH = 1000;

        @Override
        public void onCreate(Bundle icicle) {
            super.onCreate(icicle);
            setContentView(R.layout.activity_splash);

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    Intent mainIntent;
                    if (getUserIDFromSharedPreference()!=null)
                    {
                     mainIntent = new Intent(SplashActivity.this, HomeActivity.class);
                    }
                    else {
                        mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    }
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }

    private String getUserIDFromSharedPreference() {
        SharedPreferences sharedpreferences =getApplication().getSharedPreferences(getString(R.string.login_app_preference_key), Context.MODE_PRIVATE);
        return sharedpreferences.getString(getString(R.string.user_id_key), null);
    }
}

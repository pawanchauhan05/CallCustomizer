package com.pawansinghchouhan05.callcustomizer.splashScreen.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.activity.HomeActivity;
import com.pawansinghchouhan05.callcustomizer.introScreen.activity.IntroScreenActivity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_splash_screen)
public class SplashScreenActivity extends AppCompatActivity {

    @AfterViews
    void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    if(!Utils.readPreferences(getApplicationContext(), Constant.LOGIN_STATUS, "").equals(Constant.LOGIN_STATUS_VALUE)) {
                        startActivity(new Intent(SplashScreenActivity.this, IntroScreenActivity.class));
                    } else {
                        startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }
}

package com.pawansinghchouhan05.callcustomizer.introScreen.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.paolorotolo.appintro.AppIntro;
import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.introScreen.fragment.SlideContainerFragment;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.activity.RegistrationOrLoginActivity_;

public class IntroScreenActivity extends AppIntro {

    @Override
    public void init(@Nullable Bundle savedInstanceState) {
        addSlide(SlideContainerFragment.newInstance(R.layout.fragment_first));
        addSlide(SlideContainerFragment.newInstance(R.layout.fragment_second));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        loadActivity();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
    }

    /**
     * to load RegistrationOrLogin Activity
     */
    private void loadActivity() {
        Intent intent = new Intent(this, RegistrationOrLoginActivity_.class);
        startActivity(intent);
        finish();
    }
}


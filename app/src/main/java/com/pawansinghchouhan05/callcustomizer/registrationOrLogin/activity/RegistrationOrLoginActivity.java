package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.fragment.LoginFragment;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.fragment.LoginFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_registration_or_login)
public class RegistrationOrLoginActivity extends AppCompatActivity {


    @AfterViews
    void init() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.registrationOrLoginContainer, new LoginFragment_());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

package com.pawansinghchouhan05.callcustomizer.home.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.home.fragment.AddNumberFragment_;
import com.pawansinghchouhan05.callcustomizer.home.fragment.ListMobileNumberFragment_;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.fragment.LoginFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_home)
public class HomeActivity extends AppCompatActivity {

    @AfterViews
    void init() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.homeContainer, new ListMobileNumberFragment_());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}

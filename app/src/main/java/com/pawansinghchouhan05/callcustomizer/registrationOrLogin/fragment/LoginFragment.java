package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.home.activity.HomeActivity_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_login)
public class LoginFragment extends Fragment {

    @Click(R.id.buttonRegisterUser)
    void loadRegistrationFragment() {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.registrationOrLoginContainer, new RegistrationFragment_());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Click(R.id.textViewForgotPassword)
    void forgotPassword() {
        final Dialog dialog = new Dialog(getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_box_for_forgot_password);
        dialog.show();
    }

    @Click(R.id.loginButton)
    void login() {
        Intent intent = new Intent(getContext(), HomeActivity_.class);
        startActivity(intent);
    }
}

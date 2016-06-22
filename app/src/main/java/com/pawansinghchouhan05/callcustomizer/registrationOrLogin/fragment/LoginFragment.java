package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pawansinghchouhan05.callcustomizer.R;

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
}

package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.PopUpMsg;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.activity.HomeActivity;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserLoggedIn;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserLoginForm;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserRegistrationForm;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.services.UserLoginService;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_registration)
public class RegistrationFragment extends Fragment {

    @ViewById(R.id.editTextName)
    EditText editTextName;

    @ViewById(R.id.editTextEmail)
    EditText editTextEmail;

    @ViewById(R.id.editTextPassword)
    EditText editTextPassword;

    @App
    CallCustomizerApplication callCustomizerApplication;

    private UserLoginService userLoginService = callCustomizerApplication.retrofit.create(UserLoginService.class);

    @Click(R.id.buttonRegisterUser)
    void register() {
        Observable<String> registerUser = userLoginService.registerUser(new UserRegistrationForm("sunny", "p@e.com", "12345", Constant.LOGIN_TYPE_AUTH));
        try {
            registerUser.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    Log.e("Complete","C");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Error",e.getMessage());
                }

                @Override
                public void onNext(String status) {
                    Log.e("Status", status);
                    /*if(!userLoggedIn.getEmail().equals(Constant.USER_DOES_NOT_EXIST)) {
                        Utils.savePreferences(getContext(), Constant.LOGIN_TYPE, Constant.LOGIN_TYPE_AUTH);
                        Utils.savePreferences(getContext(), Constant.LOGIN_STATUS, Constant.LOGIN_STATUS_VALUE);
                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        PopUpMsg.getInstance().generateToastMsg(getContext(), Constant.USER_DOES_NOT_EXIST);
                    }*/
                }
            });
        } catch (Exception e) {
        }

    }

}

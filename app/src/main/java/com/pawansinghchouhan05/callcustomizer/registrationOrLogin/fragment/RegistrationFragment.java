package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.PopUpMsg;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.activity.HomeActivity;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.ServerStatus;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserLoggedIn;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserLoginForm;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserRegistrationForm;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.services.UserLoginService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_registration)
public class RegistrationFragment extends Fragment {

    @NotEmpty
    @ViewById(R.id.editTextName)
    EditText editTextName;


    @Email
    @ViewById(R.id.editTextEmail)
    EditText editTextEmail;

    
    @Password(min = 6)
    @ViewById(R.id.editTextPassword)
    EditText editTextPassword;

    @ConfirmPassword
    @ViewById(R.id.conformPassword)
    EditText confirmPasswordEditText;

    private Validator registerInvalidator;

    @App
    CallCustomizerApplication callCustomizerApplication;

    private UserLoginService userLoginService = callCustomizerApplication.retrofit.create(UserLoginService.class);


    @AfterViews
    void init() {
        registerInvalidator = new Validator(this);

        registerInvalidator.setValidationListener(new Validator.ValidationListener() {
            @Override
            public void onValidationSucceeded() {
                register();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                for (ValidationError error : errors) {
                    View view = error.getView();
                    String message = error.getCollatedErrorMessage(getContext());
                    if (view instanceof EditText) {
                        ((EditText) view).setError(message);
                    } else {
                        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    /**
     *
     */
    @Click(R.id.buttonRegisterUser)
    void simpleRegister() {
        registerInvalidator.validate();
    }


    void register() {

        Observable<ServerStatus> registerUser = userLoginService.registerUser(new UserRegistrationForm(editTextName.getText().toString().trim(), editTextEmail.getText().toString().trim(), editTextPassword.getText().toString().trim(), Constant.LOGIN_TYPE_AUTH));
        try {
            registerUser
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ServerStatus>() {
                        
                @Override
                public void onCompleted() {
                    Log.e("Complete","C");
                }

                @Override
                public void onError(Throwable e) {
                    if (e != null) {
                        ServerStatus serverStatus = null;
                        try {
                            if (((HttpException) e).code() == 401) {
                                serverStatus = new Gson().fromJson(((HttpException) e).response().errorBody().string().toString(), ServerStatus.class);
                                PopUpMsg.getInstance().generateToastMsg(getContext(), serverStatus.getStatus());
                            }
                        } catch (Exception e1) {
                        }
                    }
                }

                @Override
                public void onNext(ServerStatus serverStatus) {
                    Log.e("onNext",serverStatus.toString());
                    PopUpMsg.getInstance().generateToastMsg(getContext(), serverStatus.getStatus());
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.registrationOrLoginContainer, new LoginFragment_())
                            .commit();
                }
            });
        } catch (Exception e) {
        }

    }

    /**
     * to load login fragment
     */
    @Click(R.id.textViewLogin)
    void loadLoginFragment() {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.registrationOrLoginContainer, new LoginFragment_())
                .commit();
    }

}

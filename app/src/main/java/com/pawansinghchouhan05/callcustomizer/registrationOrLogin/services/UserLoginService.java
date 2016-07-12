package com.pawansinghchouhan05.callcustomizer.registrationOrLogin.services;

import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserLoggedIn;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserLoginForm;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserRegistrationForm;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by pawan on 12/7/16.
 */
public interface UserLoginService {

    @POST(Constant.LOG_IN)
    Observable<UserLoggedIn> signIn(@Body UserLoginForm userLoginForm);

    @POST(Constant.REGISTER_USER)
    Observable<String> registerUser(@Body UserRegistrationForm registrationForm);

}

package com.pawansinghchouhan05.callcustomizer.home.services;

import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;
import com.pawansinghchouhan05.callcustomizer.home.models.Email;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.ServerStatus;


import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by pawan on 16/7/16.
 */
public interface UserLoggedInService {

    @POST(Constant.ADD_CUSTOM_NUMBER)
    Observable<ServerStatus> addNumber(@Body CustomNumber customNumber);

    @POST(Constant.DELETE_CUSTOM_NUMBER)
    Observable<ServerStatus> deleteNumber(@Body CustomNumber customNumber);

    @POST(Constant.UPDATE_CUSTOM_NUMBER)
    Observable<ServerStatus> updateNumber(@Body CustomNumber customNumber);

    @POST(Constant.GET_CUSTOM_NUMBER)
    Observable<List<CustomNumber>> getNumber(@Body Email email);
}

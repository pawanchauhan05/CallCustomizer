package com.pawansinghchouhan05.callcustomizer.core.services;

import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.ServerStatus;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by pawan on 6/8/16.
 */
public interface SyncService {

    @POST(Constant.SYNC_DATA_TO_SERVER)
    Observable<ServerStatus> syncData(@Body List<CustomNumber> customNumbers);
}

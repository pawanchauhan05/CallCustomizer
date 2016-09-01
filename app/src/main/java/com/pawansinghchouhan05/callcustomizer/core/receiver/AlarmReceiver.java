package com.pawansinghchouhan05.callcustomizer.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.core.services.SyncService;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.ServerStatus;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pawan on 11/8/16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private SyncService syncService = CallCustomizerApplication.retrofit.create(SyncService.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        // For our recurring task, we'll just display a message
        //Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();

        if(!Utils.readPreferences(context, Constant.LOGIN_STATUS, "").equals("")) {
            String status = Utils.getConnectivityStatusString(context);
            Log.e("Login","started");
            if(!Utils.readPreferences(CallCustomizerApplication.getContext(), Constant.CUSTOM_NUMBER_DOC_EXIST, "").equals("")) {
                Log.e("Doc","started");
                if(status.equals(Constant.WIFI_ENABLED) | status.equals(Constant.MOBILE_DATA_ENABLED)) {
                    Log.e("Sync","started");
                    //syncDataToServer();
                }
            }
        }

    }

    /**
     * to sync data to server
     *
     */
    public void syncDataToServer() {
        Log.e("Numbers",CallCustomizerApplication.numbers.toString());
        Observable<ServerStatus> stringObservable = syncService.syncData(CallCustomizerApplication.numbers);
        try {
            stringObservable
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ServerStatus>() {
                        @Override
                        public void onCompleted() {
                            Log.e("Complete","C");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("Error",e.getMessage());
                        }

                        @Override
                        public void onNext(ServerStatus status) {

                        }

                    });
        } catch (Exception e) {}

    }

}
package com.pawansinghchouhan05.callcustomizer.core.services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;
import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.models.Token;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.ServerStatus;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserLoggedIn;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.services.UserLoginService;

import org.androidannotations.annotations.App;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Fitterfox-Pawan on 6/24/2016.
 */
public class CallCustomizerFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private UserLoginService userLoginService = CallCustomizerApplication.retrofit.create(UserLoginService.class);

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        if(!Utils.readPreferences(getApplicationContext(), Constant.LOGGED_IN_USER, "").equals("")) {
            UserLoggedIn userLoggedIn = new Gson().fromJson(Utils.readPreferences(getApplicationContext(), Constant.LOGGED_IN_USER, ""),UserLoggedIn.class);
            Observable<ServerStatus> stringObservable = userLoginService.registerToken(new Token(userLoggedIn.getEmail(), token));
            try {
                stringObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<ServerStatus>() {
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
            } catch (Exception e) {
            }
        }

    }
}


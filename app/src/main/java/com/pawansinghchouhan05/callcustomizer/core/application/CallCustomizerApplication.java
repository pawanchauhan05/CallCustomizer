package com.pawansinghchouhan05.callcustomizer.core.application;

import android.app.Application;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Fitterfox-Pawan on 6/21/2016.
 */
public class CallCustomizerApplication extends Application {

    public static DatabaseReference databaseReference;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}

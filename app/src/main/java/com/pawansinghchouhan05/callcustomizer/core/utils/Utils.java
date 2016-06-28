package com.pawansinghchouhan05.callcustomizer.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumberList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Fitterfox-Pawan on 6/21/2016.
 */
public class Utils {

    public static CustomNumberList customNumberList = new CustomNumberList();

    public static String readPreferences(Context context, String key, String defaultValue) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getString(key, defaultValue);
    }

    public static void savePreferences(Context context, String key, String value) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
        UUID.randomUUID().toString();
    }


    public static void storeCustomNumberListToFCMDatabase(CustomNumber customNumber) {
        //String key = UUID.randomUUID().toString();
        String key = "05dc32b4-78d6-42ba-965f-ce3b8e719784";
        CallCustomizerApplication.databaseReference.child(Constant.NUMBERS).child(key).setValue(customNumberList);
    }

    public static CustomNumberList retriveCustomNumberListToFCMDatabase() {
        String key ="05dc32b4-78d6-42ba-965f-ce3b8e719784";
        CallCustomizerApplication.databaseReference.child(Constant.NUMBERS).child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                customNumberList = dataSnapshot.getValue(CustomNumberList.class);
                Log.e("static",customNumberList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error",databaseError.getMessage());
            }
        });

        return customNumberList;
    }
}

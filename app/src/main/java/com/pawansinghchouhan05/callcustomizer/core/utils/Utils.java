package com.pawansinghchouhan05.callcustomizer.core.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    public static CustomNumberList customNumberList ;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    static  {
        customNumberList = new CustomNumberList();
        Log.e("init","static");
    }

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

    public static void clearPreferences(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }


    public static void storeCustomNumberListToFCMDatabase(CustomNumber customNumber, Context context)  {
        //String key = UUID.randomUUID().toString();

        String key = "05dc32b4-78d6-42ba-965f-ce3b8e719784";
        if(customNumberList != null) {
            if(customNumberList.getCustomNumberList().contains(customNumber)) {
                PopUpMsg.getInstance().generateToastMsg(context, "Number already exist!");
            } else {
                PopUpMsg.getInstance().generateToastMsg(context, "Number successfully added!");
                customNumberList.getCustomNumberList().add(customNumber);
            }
        } else {
            customNumberList = new CustomNumberList();
            customNumberList.getCustomNumberList().add(customNumber);
            PopUpMsg.getInstance().generateToastMsg(context, "Number successfully added!");
        }

        CallCustomizerApplication.databaseReference.child(Constant.NUMBERS).child(key).setValue(customNumberList);
    }

    public static CustomNumberList retriveCustomNumberListToFCMDatabase() {
        String key ="05dc32b4-78d6-42ba-965f-ce3b8e719784";
        CallCustomizerApplication.databaseReference.child(Constant.NUMBERS).child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                customNumberList = dataSnapshot.getValue(CustomNumberList.class);
                //Log.e("static",customNumberList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error",databaseError.getMessage());
            }
        });

        return customNumberList;
    }

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;
        if (conn == TYPE_WIFI) {
            status = Constant.WIFI_ENABLED;
        } else if (conn == TYPE_MOBILE) {
            status = Constant.MOBILE_DATA_ENABLED;
        } else if (conn == TYPE_NOT_CONNECTED) {
            status = Constant.NOT_CONNECTED_TO_INTERNET;
        }
        return status;
    }


}

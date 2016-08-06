package com.pawansinghchouhan05.callcustomizer.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.adapters.MobileNumberAdapter;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumberList;
import com.pawansinghchouhan05.callcustomizer.home.models.Email;
import com.pawansinghchouhan05.callcustomizer.home.services.UserLoggedInService;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserLoggedIn;

import org.androidannotations.annotations.App;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Fitterfox-Pawan on 5/11/2016.
 */
public class ServiceReceiver extends BroadcastReceiver {

    private Context context;
    public AudioManager myAudioManager;
    private int mode;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;
        this.myAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        mode = this.myAudioManager.getRingerMode();


        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener customPhoneListener = new MyPhoneStateListener();

        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        Bundle bundle = intent.getExtras();
        String number = bundle.getString("incoming_number");
        //number = number.replace(" ","");
        number = number.replace("+91","");
        System.out.println("Phone Number : " + number);

        if((!Utils.readPreferences(context, Constant.LOGIN_STATUS, "").equals("")) && Utils.readPreferences(context, Constant.COMPLETE_SILENT, "").equals("")) {
            boolean flag = false;
            for (CustomNumber customNumber: CallCustomizerApplication.numbers) {
                if(customNumber.equals(new CustomNumber("",Long.parseLong(number)))) {
                    flag = true;
                    break;
                }
            }

            if(flag == true) {
                if(mode != AudioManager.RINGER_MODE_NORMAL) {
                    myAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
            }
        }

    }

    public class MyPhoneStateListener extends PhoneStateListener {


        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            Log.e("Incoming" , incomingNumber);


            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("PHONE RINGING.........TAKE IT.........");
                    Log.e("RINGING","PHONE RINGING.........TAKE IT.........");
                    //RingtoneManager.setActualDefaultRingtoneUri(MainActivity.this,RingtoneManager.TYPE_RINGTONE,mUri);

                    // TODO Add custom ringtone which ring on incoming call
                    RingtoneManager mgr = new RingtoneManager(context);
                    RingtoneManager.setActualDefaultRingtoneUri(context,
                            RingtoneManager.TYPE_RINGTONE,
                            mgr.getRingtoneUri(3));
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("CALL_STATE_OFFHOOK...........");
                    Log.e("OFFHOOK","CALL_STATE_OFFHOOK...........");
                    break;

                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }

            //super.onCallStateChanged(state, incomingNumber);
        }
    }



}
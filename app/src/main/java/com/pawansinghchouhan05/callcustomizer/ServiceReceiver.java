package com.pawansinghchouhan05.callcustomizer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;


/**
 * Created by Fitterfox-Pawan on 5/11/2016.
 */
public class ServiceReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener customPhoneListener = new MyPhoneStateListener();

        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        Bundle bundle = intent.getExtras();
        String phone_number = bundle.getString("incoming_number");
        System.out.println("Phone Number : " + phone_number);


    }

    public class MyPhoneStateListener extends PhoneStateListener {

        public void onCallStateChange(int state, String incomingNumber){

            System.out.println("Icoming Number inside onCallStateChange : "  + incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    System.out.println("PHONE RINGING.........TAKE IT.........");
                    //RingtoneManager.setActualDefaultRingtoneUri(MainActivity.this,RingtoneManager.TYPE_RINGTONE,mUri);



                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("CALL_STATE_OFFHOOK...........");
                    break;
            }
        }
    }

}
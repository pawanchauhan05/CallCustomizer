package com.pawansinghchouhan05.callcustomizer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * Created by Fitterfox-Pawan on 5/11/2016.
 */
public class ServiceReceiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        TelephonyManager telephony = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener customPhoneListener = new MyPhoneStateListener();

        telephony.listen(customPhoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        Bundle bundle = intent.getExtras();
        String phone_number = bundle.getString("incoming_number");
        System.out.println("Phone Number : " + phone_number);

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
                            mgr.getRingtoneUri(4));
                    break;

                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("CALL_STATE_OFFHOOK...........");
                    Log.e("OFFHOOK","CALL_STATE_OFFHOOK...........");
                    break;
            }

            //super.onCallStateChanged(state, incomingNumber);
        }
    }

}
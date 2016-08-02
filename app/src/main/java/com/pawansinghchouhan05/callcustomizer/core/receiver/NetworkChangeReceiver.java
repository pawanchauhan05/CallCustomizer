package com.pawansinghchouhan05.callcustomizer.core.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;

/**
 * Created by pawan on 2/8/16.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = Utils.getConnectivityStatusString(context);
        Log.e("status",status);
        // TODO add sync code CBlite to server db
    }
}

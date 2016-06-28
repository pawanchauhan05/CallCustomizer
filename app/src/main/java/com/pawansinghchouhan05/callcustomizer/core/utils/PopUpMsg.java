package com.pawansinghchouhan05.callcustomizer.core.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

/**
 * Created by Fitterfox-Pawan on 6/28/2016.
 */
public class PopUpMsg {
    private static PopUpMsg ourInstance = new PopUpMsg();

    public static PopUpMsg getInstance() {
        return ourInstance;
    }

    private PopUpMsg() {
    }

    private void generateToastMsg(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}

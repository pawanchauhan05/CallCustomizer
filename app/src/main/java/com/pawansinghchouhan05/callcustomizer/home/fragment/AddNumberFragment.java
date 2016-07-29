package com.pawansinghchouhan05.callcustomizer.home.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.Gson;
import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;
import com.pawansinghchouhan05.callcustomizer.home.services.UserLoggedInService;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.ServerStatus;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserLoggedIn;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_add_number)
public class AddNumberFragment extends Fragment {

    private static final int REQUEST_CODE = 1;
    private List<CustomNumber> customNumberList = new ArrayList<>();

    @App
    CallCustomizerApplication callCustomizerApplication;

    private UserLoggedInService userLoggedInService = callCustomizerApplication.retrofit.create(UserLoggedInService.class);

    @ViewById(R.id.editTextName)
    EditText editTextName;

    @ViewById(R.id.editTextNumber)
    EditText editTextNumber;

    @Click(R.id.addNumber)
    void addNumberFromContacts() {
        Uri uri = Uri.parse("content://contacts");
        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @AfterViews
    void init() {
        CustomNumber customNumber = null;
        try {
            customNumber = (CustomNumber) getArguments().getParcelable(Constant.CUSTOM_NUMBER_BUNDLE);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if(customNumber != null) {
            editTextName.setText(customNumber.getName());
            editTextNumber.setText(customNumber.getCustomNumber()+"");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                Uri uri = intent.getData();
                String[] projection = { ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME };

                Cursor cursor = getActivity().getContentResolver().query(uri, projection,
                        null, null, null);
                cursor.moveToFirst();

                int numberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(numberColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String name = cursor.getString(nameColumnIndex);
                number = number.replace(" ","");
                number = number.replace("+91","");
                Log.e("number", "ZZZ number : " + number + " , name : " + name);
                UserLoggedIn userLoggedIn = new Gson().fromJson(Utils.readPreferences(getContext(), Constant.LOGGED_IN_USER, ""), UserLoggedIn.class);
                CustomNumber customNumber = new CustomNumber(userLoggedIn.getEmail(), name, Long.parseLong(number.trim()));
                sendDataToServer(customNumber);

                //CustomNumber customNqumber = new CustomNumber(name, Long.parseLong(number.trim()));
                //Utils.storeCustomNumberListToFCMDatabase(customNumber, getContext());

            }
        }
    }


    void addNumberManually() {
        CustomNumber customNumber = new CustomNumber(editTextName.getText().toString().trim(), Long.parseLong(editTextNumber.getText().toString().trim()));
        Utils.storeCustomNumberListToFCMDatabase(customNumber, getContext());
    }

    /**
     * to add custom number information to server
     */
    @Click(R.id.buttonAdd)
    void addNumber() {

        UserLoggedIn userLoggedIn = new Gson().fromJson(Utils.readPreferences(getContext(), Constant.LOGGED_IN_USER, ""), UserLoggedIn.class);
        CustomNumber customNumber = new CustomNumber(userLoggedIn.getEmail(), editTextName.getText().toString().trim(), Long.parseLong(editTextNumber.getText().toString().trim()));
        Log.e("CM", customNumber.toString());

        sendDataToServer(customNumber);


    }

    /**
     * to custom number object to server
     * @param customNumber
     */
    void sendDataToServer(CustomNumber customNumber) {
        Observable<ServerStatus> stringObservable = userLoggedInService.addNumber(customNumber);
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
        } catch (Exception e) {}
    }

}

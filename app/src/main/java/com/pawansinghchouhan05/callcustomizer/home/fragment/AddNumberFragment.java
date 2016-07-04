package com.pawansinghchouhan05.callcustomizer.home.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.EditText;

import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_add_number)
public class AddNumberFragment extends Fragment {

    private static final int REQUEST_CODE = 1;
    private List<CustomNumber> customNumberList = new ArrayList<>();

    @ViewById(R.id.editTextName)
    EditText editTextName;

    @ViewById(R.id.editTextNumber)
    EditText editTextNumber;

    @Click(R.id.addNumber)
    void addNumber() {
        Uri uri = Uri.parse("content://contacts");
        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_CODE);
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
                CustomNumber customNumber = new CustomNumber(name, Long.parseLong(number.trim()));
                Utils.storeCustomNumberListToFCMDatabase(customNumber, getContext());

            }
        }
    }

    @Click(R.id.buttonAdd)
    void addNumberManually() {
        CustomNumber customNumber = new CustomNumber(editTextName.getText().toString().trim(), Long.parseLong(editTextNumber.getText().toString().trim()));
        Utils.storeCustomNumberListToFCMDatabase(customNumber, getContext());
    }

}

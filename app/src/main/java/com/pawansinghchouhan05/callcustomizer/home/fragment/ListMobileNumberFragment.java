package com.pawansinghchouhan05.callcustomizer.home.fragment;


import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.couchbase.lite.internal.InterfaceAudience;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.core.database.CouchBaseDB;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.PopUpMsg;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.adapters.MobileNumberAdapter;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumberList;
import com.pawansinghchouhan05.callcustomizer.home.models.Email;
import com.pawansinghchouhan05.callcustomizer.home.services.UserLoggedInService;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.ServerStatus;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.models.UserLoggedIn;
import com.pawansinghchouhan05.callcustomizer.registrationOrLogin.services.UserLoginService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@EFragment(R.layout.fragment_list_mobile_number)
public class ListMobileNumberFragment extends Fragment {

    private CustomNumberList customNumberList = new CustomNumberList();
    private MobileNumberAdapter mobileNumberAdapter;

    @App
    CallCustomizerApplication callCustomizerApplication;

    private UserLoggedInService userLoggedInService= CallCustomizerApplication.retrofit.create(UserLoggedInService.class);

    @ViewById(R.id.completeSilentSwitch)
    Switch completeSilentSwitch;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    ListMobileNumberFragment listMobileNumberFragment;

    @AfterViews()
    void init() {
        //customNumberList = Utils.retriveCustomNumberListToFCMDatabase();
        // TODO Optimise this code for list & CB Exist or not
        customNumberList = new CustomNumberList();
        customNumberList = getCustomNumberFromCouchbase();
        listMobileNumberFragment = this;
        try {
            mobileNumberAdapter = new MobileNumberAdapter(customNumberList.getCustomNumberList(), getContext(), this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(mobileNumberAdapter);
            mobileNumberAdapter.notifyDataSetChanged();

        } catch (NullPointerException e) {
            PopUpMsg.getInstance().generateToastMsg(getContext(), "Please first add Number!");
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mobileNumberAdapter.setOnItemClickListener(new MobileNumberAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
               Log.e("postion",position+"");
            }
        });

    }

    public void moveToAddNumberFragment(Bundle bundle) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        AddNumberFragment_ addNumberFragment = new AddNumberFragment_();
        addNumberFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container, addNumberFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private CustomNumberList getCustomNumber() {
        UserLoggedIn userLoggedIn = new Gson().fromJson(Utils.readPreferences(getContext(), Constant.LOGGED_IN_USER, ""), UserLoggedIn.class);

        Observable<List<CustomNumber>> stringObservable = userLoggedInService.getNumber(new Email(userLoggedIn.getEmail()));
        try {
            stringObservable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<CustomNumber>>() {
                @Override
                public void onCompleted() {
                    Log.e("Complete","C");
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Error",e.getMessage());
                }

                @Override
                public void onNext(List<CustomNumber> customNumbers) {
                    Log.e("list", customNumbers.toString());
                    customNumberList.setCustomNumberList(customNumbers);
                    mobileNumberAdapter = new MobileNumberAdapter(customNumberList.getCustomNumberList(), getContext(), listMobileNumberFragment);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(mobileNumberAdapter);
                    mobileNumberAdapter.notifyDataSetChanged();
                }

            });
        } catch (Exception e) { e.printStackTrace();}

        return customNumberList;
    }

    private CustomNumberList getCustomNumberFromCouchbase() {
        Map<String, Object> map = CouchBaseDB.getDocument();
        Gson gson = new Gson();
        List<CustomNumber> numberList= new ArrayList<>();
        for (String key: map.keySet()) {
            try {
                numberList.add(gson.fromJson(map.get(key).toString(), CustomNumber.class));
            } catch (Exception e) { Log.e("Exp", e.getMessage()); }

        }

        /*for (Object object: map.values()) {
            try {
                numberList.add(gson.fromJson(object.toString(), CustomNumber.class));
            } catch (Exception e) { Log.e("Exp", e.getMessage()); }
        }*/
        customNumberList.setCustomNumberList(numberList);
        mobileNumberAdapter = new MobileNumberAdapter(customNumberList.getCustomNumberList(), getContext(), listMobileNumberFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mobileNumberAdapter);
        mobileNumberAdapter.notifyDataSetChanged();
        return customNumberList;
    }

    @Click(R.id.completeSilentSwitch)
    void toggleCompleteSilent() {
        if(completeSilentSwitch.isChecked()) {
            Log.e("check","ON");
            Utils.savePreferences(getContext(), Constant.COMPLETE_SILENT, Constant.COMPLETE_SILENT_STATUS);
        } else {
            Log.e("check","OFF");
            Utils.savePreferences(getContext(), Constant.COMPLETE_SILENT, "");
        }
    }
}

package com.pawansinghchouhan05.callcustomizer.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.adapters.MobileNumberAdapter;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumberList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_list_mobile_number)
public class ListMobileNumberFragment extends Fragment {

    private CustomNumberList customNumberList;
    private MobileNumberAdapter mobileNumberAdapter;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @AfterViews()
    void init() {
        customNumberList = Utils.retriveCustomNumberListToFCMDatabase();
        mobileNumberAdapter = new MobileNumberAdapter(customNumberList.getCustomNumberList());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mobileNumberAdapter);
        mobileNumberAdapter.notifyDataSetChanged();
    }


}

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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.PopUpMsg;
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

    private CustomNumberList customNumberList = new CustomNumberList();
    private MobileNumberAdapter mobileNumberAdapter;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @AfterViews()
    void init() {
        customNumberList = Utils.retriveCustomNumberListToFCMDatabase();
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
}

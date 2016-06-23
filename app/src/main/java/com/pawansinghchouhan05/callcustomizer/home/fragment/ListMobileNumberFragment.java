package com.pawansinghchouhan05.callcustomizer.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.home.adapters.MobileNumberAdapter;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.fragment_list_mobile_number)
public class ListMobileNumberFragment extends Fragment {

    private List<CustomNumber> customNumberList = new ArrayList<>();
    private MobileNumberAdapter mobileNumberAdapter;

    @ViewById(R.id.recycler_view)
    RecyclerView recyclerView;

    @AfterViews()
    void init() {
        mobileNumberAdapter = new MobileNumberAdapter(customNumberList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mobileNumberAdapter);
        customNumberList.add(new CustomNumber("Pawan", 12345500));
        customNumberList.add(new CustomNumber("Divya", 12345500));
        customNumberList.add(new CustomNumber("Ninja", 12345500));
        customNumberList.add(new CustomNumber("Hattori", 12345500));
        customNumberList.add(new CustomNumber("honey", 12345500));

        mobileNumberAdapter.notifyDataSetChanged();
    }

}

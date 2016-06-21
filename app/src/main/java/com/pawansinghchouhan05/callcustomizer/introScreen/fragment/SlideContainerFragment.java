package com.pawansinghchouhan05.callcustomizer.introScreen.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pawansinghchouhan05.callcustomizer.R;


public class SlideContainerFragment extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;

    /**
     * to each time returns own instance
     * @param layoutResId
     * @return
     */
    public static SlideContainerFragment newInstance(int layoutResId) {
        SlideContainerFragment slideContainerFragment = new SlideContainerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        slideContainerFragment.setArguments(args);
        return slideContainerFragment;
    }


    public SlideContainerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(layoutResId, container, false);
    }



}

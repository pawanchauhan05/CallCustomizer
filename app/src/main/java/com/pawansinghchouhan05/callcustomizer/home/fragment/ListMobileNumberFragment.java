package com.pawansinghchouhan05.callcustomizer.home.fragment;


import android.content.Context;
import android.graphics.Movie;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
            mobileNumberAdapter = new MobileNumberAdapter(customNumberList.getCustomNumberList(), getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(mobileNumberAdapter);
            mobileNumberAdapter.notifyDataSetChanged();

        } catch (NullPointerException e) {
            PopUpMsg.getInstance().generateToastMsg(getContext(), "Please first add Number!");
        }

        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CustomNumber customNumber = customNumberList.getCustomNumberList().get(position);
                Toast.makeText(getContext(), customNumber.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
    }

    /*public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }*/

    /*public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }*/

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
}

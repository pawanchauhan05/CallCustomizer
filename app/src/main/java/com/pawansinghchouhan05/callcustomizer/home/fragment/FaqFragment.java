package com.pawansinghchouhan05.callcustomizer.home.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.home.adapters.ExpandableListAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_faq)
public class FaqFragment extends Fragment {

    @ViewById(R.id.faqExpandableList)
    ExpandableListView expandableList;
    String[] faqQuestions;
    String[] faqAnswers;

    @AfterViews
    void init() {
        faqQuestions = getContext().getResources().getStringArray(R.array.faq_question_array);
        faqAnswers = getContext().getResources().getStringArray(R.array.faq_answer_array);
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);


        ExpandableListAdapter adapter = new ExpandableListAdapter(faqQuestions, faqAnswers, getContext());

        adapter.setInflater((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE), getActivity());
        expandableList.setAdapter(adapter);
        expandableList.expandGroup(0,true);
        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            int previousGroup = 0;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (groupPosition != previousGroup)
                    expandableList.collapseGroup(previousGroup);
                previousGroup = groupPosition;
            }
        });
    }

}

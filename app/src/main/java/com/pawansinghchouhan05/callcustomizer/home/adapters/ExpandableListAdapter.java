package com.pawansinghchouhan05.callcustomizer.home.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.pawansinghchouhan05.callcustomizer.R;

/**
 * Created by pawan on 6/7/16.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    LayoutInflater inflater;
    Activity activity;
    String[] faqQuestions;
    String[] faqAnswers;
    Context context;
    TextView answer;
    TextView question;

    public ExpandableListAdapter(String[] faqQuestions, String[] faqAnswers, Context context) {
        this.faqQuestions = faqQuestions;
        this.faqAnswers = faqAnswers;
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        return faqQuestions.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.faq_question_layout, null);
        question = (TextView) view.findViewById(R.id.text_faq_question);
        question.setText(faqQuestions[groupPosition]);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int i1, boolean b, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.faq_answer_layout, null);
        answer = (TextView) view.findViewById(R.id.text_faq_answer);
        answer.setText(faqAnswers[groupPosition]);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}

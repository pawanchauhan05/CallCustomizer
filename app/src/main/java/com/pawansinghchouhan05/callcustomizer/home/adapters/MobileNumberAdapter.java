package com.pawansinghchouhan05.callcustomizer.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.core.application.CallCustomizerApplication;
import com.pawansinghchouhan05.callcustomizer.core.utils.Constant;
import com.pawansinghchouhan05.callcustomizer.core.utils.PopUpMsg;
import com.pawansinghchouhan05.callcustomizer.core.utils.Utils;
import com.pawansinghchouhan05.callcustomizer.home.fragment.ListMobileNumberFragment;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumberList;

import java.util.List;
import java.util.logging.Level;

/**
 * Created by Fitterfox-Pawan on 6/23/2016.
 */
public class MobileNumberAdapter extends RecyclerView.Adapter<MobileNumberAdapter.MyViewHolder> {

    private List<CustomNumber> customNumberList;
    private static MyClickListener myClickListener;
    private Context context;
    private ListMobileNumberFragment listMobileNumberFragment;


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name, mobileNumber;
        public ImageView imageViewEdit, imageViewDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textViewName);
            mobileNumber = (TextView) itemView.findViewById(R.id.textViewMobileNumber);
            imageViewEdit = (ImageView) itemView.findViewById(R.id.imageViewEdit);
            imageViewDelete = (ImageView) itemView.findViewById(R.id.imageViewDelete);
            imageViewDelete.setOnClickListener(this);
            imageViewEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            myClickListener.onItemClick(getPosition(), view);
        }
    }

    public MobileNumberAdapter(List<CustomNumber> customNumberList, Context context, ListMobileNumberFragment listMobileNumberFragment) {
        this.customNumberList = customNumberList;
        this.context = context;
        this.listMobileNumberFragment = listMobileNumberFragment;
    }

    @Override
    public MobileNumberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items_mobile_number_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MobileNumberAdapter.MyViewHolder holder, final int position) {
        final CustomNumber customNumber = customNumberList.get(position);
        holder.name.setText(customNumber.getName());
        holder.mobileNumber.setText(""+customNumber.getMobileNumber());
        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customNumberList.remove(position);
                CustomNumberList numberList = new CustomNumberList();
                numberList.setCustomNumberList(customNumberList);
                CallCustomizerApplication.databaseReference.child(Constant.NUMBERS).child("05dc32b4-78d6-42ba-965f-ce3b8e719784").setValue(numberList);
                notifyDataSetChanged();
                PopUpMsg.getInstance().generateToastMsg(context, "Number Deleted Successfully!");
            }
        });

        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.CUSTOM_NUMBER_BUNDLE,customNumberList.get(position));
                listMobileNumberFragment.moveToAddNumberFragment(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return customNumberList.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }



}

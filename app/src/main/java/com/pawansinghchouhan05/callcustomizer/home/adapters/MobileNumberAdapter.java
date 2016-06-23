package com.pawansinghchouhan05.callcustomizer.home.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pawansinghchouhan05.callcustomizer.R;
import com.pawansinghchouhan05.callcustomizer.home.models.CustomNumber;

import java.util.List;
import java.util.logging.Level;

/**
 * Created by Fitterfox-Pawan on 6/23/2016.
 */
public class MobileNumberAdapter extends RecyclerView.Adapter<MobileNumberAdapter.MyViewHolder> {

    private List<CustomNumber> customNumberList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, mobileNumber;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.textViewName);
            mobileNumber = (TextView) itemView.findViewById(R.id.textViewMobileNumber);
        }
    }

    public MobileNumberAdapter(List<CustomNumber> customNumberList) {
        this.customNumberList = customNumberList;
    }

    @Override
    public MobileNumberAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_items_mobile_number_adapter, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MobileNumberAdapter.MyViewHolder holder, int position) {
        CustomNumber customNumber = customNumberList.get(position);
        holder.name.setText(customNumber.getName());
        holder.mobileNumber.setText(""+customNumber.getMobileNumber());
    }

    @Override
    public int getItemCount() {
        return customNumberList.size();
    }
}

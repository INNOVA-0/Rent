package com.innova.rent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class TenantStatsListAdapter extends ArrayAdapter<TenantStats> implements Filterable {
    private Context mContext;
    int mResource;



    public TenantStatsListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<TenantStats> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // getting view and attaching it to list view

        history history_instance = new history();

            // get the information
            String name= getItem(position).getName();
            int rent =getItem(position).getRent();
            int recievedRent=getItem(position).getRecievedRent();
            int remainingRent=getItem(position).getRemainingRent();

            // create the TenantStats object with the information
            TenantStats tenant = new TenantStats(name,rent,recievedRent,remainingRent);

            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView= inflater.inflate(mResource, parent, false);

            TextView Name = convertView.findViewById(R.id.lvName);
            TextView Rent =  convertView.findViewById(R.id.lvRent);
            TextView RecievedRent =  convertView.findViewById(R.id.lvRecieved);
            TextView RemainingRent =  convertView.findViewById(R.id.lvRemaining);

            Name.setText(name);
            Rent.setText(Integer.toString(rent));
            RecievedRent.setText(Integer.toString(recievedRent));
            RemainingRent.setText(Integer.toString(remainingRent));

             if (recievedRent==0)
            {
                Name.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                Rent.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                RecievedRent.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                RemainingRent.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }
            else {

                 if(remainingRent == 0)  // setting color of data imported in list view
                 {
                     Name.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                     Rent.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                     RecievedRent.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                     RemainingRent.setTextColor(ContextCompat.getColor(mContext, R.color.green));
                 }
                 else{
                     Name.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                     Rent.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                     RecievedRent.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                     RemainingRent.setTextColor(ContextCompat.getColor(mContext, R.color.blue));
                 }
            }

//
//            if(history.isAll)
//            {
//                Name.setText(name);
//                Rent.setText(Integer.toString(rent));
//                RecievedRent.setText(Integer.toString(recievedRent));
//                RemainingRent.setText(Integer.toString(remainingRent));
//                return convertView;
//            }
//            else if(history.isRemaining && remainingRent > 0) // baqaya filter
//            {
//                Name.setText(name);
//                Rent.setText(Integer.toString(rent));
//                RecievedRent.setText(Integer.toString(recievedRent));
//                RemainingRent.setText(Integer.toString(remainingRent));
//                return convertView;
//            }
//            else if(history.isSum  && remainingRent == 0)  // jama filter
//            {
//                Name.setText(name);
//                Rent.setText(Integer.toString(rent));
//                RecievedRent.setText(Integer.toString(recievedRent));
//                RemainingRent.setText(Integer.toString(remainingRent));
//                return convertView;
//            }

        return convertView;
    }
}

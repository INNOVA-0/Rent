package com.innova.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class history extends AppCompatActivity {

    ArrayList<TenantStats> tenantList;
    ListView tenantListView;
    Helper helper;
    SQLiteDatabase db;
    ImageView avatar;
    TextView role;
    managerDashboard manager;
    private static boolean adminIntent=true;  // required to decide wether to go back to admin or manager dashboard

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        manager= new managerDashboard();
        // inflating UI components
        avatar= findViewById(R.id.historyAvatar);
        role= findViewById(R.id.historyRole);
        tenantListView = findViewById(R.id.lvRecord);

        // Database Configuration
        helper= new Helper(this);
        db = helper.getWritableDatabase();

        updateUI();

//        TenantStats tenant= new TenantStats("عبد العزیز خان",125000,120000,5000);
//        TenantStats tenant1= new TenantStats("عبد العزیز ",125000,120000,5000);
//
//        tenantList = new ArrayList<>();
//        tenantList.add(tenant);
//        tenantList.add(tenant);
//        tenantList.add(tenant1);

        TenantStatsListAdapter adapter = new TenantStatsListAdapter(this,R.layout.adapter_view_layout,helper.getTenantStats(db));
        tenantListView.setAdapter(adapter);
    }

    public void updateUI(){
        if(managerDashboard.isManager)
        {
//            Log.d("in update ui", "role is manager");
            avatar.setImageResource(R.drawable.manager);
            role.setText(getResources().getText(R.string.manager));
            adminIntent=false;
            manager.isManager= false;
//            Log.d("in update ui", String.valueOf(manager.isManager));
        }
    }
}
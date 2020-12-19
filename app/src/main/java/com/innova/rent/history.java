package com.innova.rent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class history extends AppCompatActivity {

    ArrayList<TenantStats> tenantList;
    ArrayList<TenantStats> tenants;
    ArrayList<String> listDataArray;
    TenantStatsListAdapter adapter;

    ListView tenantListView;
    android.widget.SearchView searchBar;
    Helper helper;
    SQLiteDatabase db;
    ImageView avatar;
    TextView role;
    RadioGroup selection;
    RadioButton selectedBtn;
    Button export;
    managerDashboard manager;
    private static boolean adminIntent=true;  // required to decide wether to go back to admin or manager dashboard

    public static boolean isAll=true;
    public static boolean isSum=false;
    public static boolean isRemaining=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        manager= new managerDashboard();
        // inflating UI components
        avatar= findViewById(R.id.historyAvatar);
        role= findViewById(R.id.historyRole);
        tenantListView = findViewById(R.id.lvRecord);
        selection = findViewById(R.id.radioOptions);
        export= findViewById(R.id.btn_import);
        searchBar = findViewById(R.id.search);


        // Database Configuration
        helper= new Helper(this);
        db = helper.getWritableDatabase();

        updateUI();


        selection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()  // radio button selection options
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioAll:
                        isAll = true;
                        isRemaining=false;
                        isSum = false;
//                        Toast.makeText(history.this, "All Selected", Toast.LENGTH_SHORT).show();
                        TenantStatsListAdapter adapter = new TenantStatsListAdapter(history.this,R.layout.adapter_view_layout,helper.getTenantStats(db,0));
                        tenantListView.setAdapter(adapter);

                        break;

                    case R.id.radioRemaining:
                        isRemaining=true;
                        isAll = false;
                        isSum = false;
                        TenantStatsListAdapter rem_adapter = new TenantStatsListAdapter(history.this,R.layout.adapter_view_layout,helper.getTenantStats(db,1));
                        tenantListView.setAdapter(rem_adapter);

                        break;

                    case R.id.radioSum:
                        isSum = true;
                        isAll = false;
                        isRemaining=false;
                        TenantStatsListAdapter sum_adapter = new TenantStatsListAdapter(history.this,R.layout.adapter_view_layout,helper.getTenantStats(db,2));
                        tenantListView.setAdapter(sum_adapter);

                        break;
                }
            }
        });

        tenantList= helper.getTenantStats(db,0);
        listDataArray = new ArrayList<>();
        String names = "NAME;RENT;RECIEVED;REMAINING";
        listDataArray.add(names);


        for (int items =0; items < tenantList.size(); items++)
        {
            TenantStats tenant= tenantList.get(items);
            String name= tenant.getName();
            int rent = tenant.getRent();
            int recievedRent=tenant.getRecievedRent();
            int remainingRent=tenant.getRemainingRent();

            String concatenated_data = name + ";"+ rent+ ";" + recievedRent+ ";" + remainingRent +";" ;
            listDataArray.add(concatenated_data);
        }

        tenants = helper.getTenantStats(db,0);
        adapter = new TenantStatsListAdapter(this,R.layout.adapter_view_layout,tenants);
        tenantListView.setAdapter(adapter);
        initSearchView();


        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    boolean writePermissionStatus = checkStoragePermission(false);
                    //Check for permission
                    if (!writePermissionStatus) {
                        ActivityCompat.requestPermissions(history.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        return;
                    } else {
                        boolean writePermissionStatusAgain = checkStoragePermission(true);
                        if (!writePermissionStatusAgain) {
                            Toast.makeText(history.this, "Permission not granted", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            //Permission Granted. Export
                            exportDataToCSV();

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }


    public static String toCSV(String[] array) {
        String result = "";
        if (array.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (String s : array) {
                sb.append(s.trim()).append(",");
            }
            result = sb.deleteCharAt(sb.length() - 1).toString();
        }
        return result;
    }
    private void exportDataToCSV() throws IOException {

        String csvData = "";

        for (int i = 0; i < listDataArray.size(); i++) {

            String currentLIne = listDataArray.get(i);
            String[] cells = currentLIne.split(";");

            csvData += toCSV(cells) + "\n";

        }


        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String uniqueFileName = "rent.csv";
        File file = new File(directory, uniqueFileName);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(csvData);
        fileWriter.flush();
        fileWriter.close();
        Toast.makeText(history.this, "File Exported Successfully", Toast.LENGTH_SHORT).show();

    }
    private boolean checkStoragePermission(boolean showNotification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                if (showNotification) showNotificationAlertToAllowPermission();
                return false;
            }
        } else {
            return true;
        }
    }
    private void showNotificationAlertToAllowPermission() {
        new AlertDialog.Builder(this).setMessage("Please allow Storage Read/Write permission for this app to function properly.").setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }).setNegativeButton("Cancel", null).show();

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

    public void initSearchView()
    {
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                history.this.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<TenantStats> filteredTenatns = new ArrayList<TenantStats>();

                for (TenantStats tenant: tenants)
                {
                    if(tenant.getName().toLowerCase().contains(newText.toLowerCase()))
                    {
                        filteredTenatns.add(tenant);
                    }
                }
                TenantStatsListAdapter filteredAdapter = new TenantStatsListAdapter(history.this,R.layout.adapter_view_layout,filteredTenatns);
                tenantListView.setAdapter(filteredAdapter);
                return false;
            }
        });
    }
}


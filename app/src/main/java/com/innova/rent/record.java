package com.innova.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class record extends AppCompatActivity {

    ImageView avatar;
    TextView role;
    managerDashboard manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        manager= new managerDashboard();

        avatar= findViewById(R.id.recordAvatar);
        role= findViewById(R.id.recordRole);

        updateUI();

//        if(manager.isManager)
//        {
//            manager.isManager= false;
//            Toast.makeText(this, "logged as manager",Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Toast.makeText(this, "not logged as manager",Toast.LENGTH_SHORT).show();
//        }

    }

    public void updateUI(){
        if(managerDashboard.isManager)
        {
//            Log.d("in update ui", "role is manager");
            avatar.setImageResource(R.drawable.manager);
            role.setText(getResources().getText(R.string.manager));
            manager.isManager= false;
//            Log.d("in update ui", String.valueOf(manager.isManager));
        }
    }
}
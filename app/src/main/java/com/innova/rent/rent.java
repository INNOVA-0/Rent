package com.innova.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class rent extends AppCompatActivity {

    private ImageView avatar;
    private TextView role;
    private managerDashboard manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        manager= new managerDashboard();
        avatar= findViewById(R.id.rentAvatar);
        role= findViewById(R.id.rentRole);

        updateUI();


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
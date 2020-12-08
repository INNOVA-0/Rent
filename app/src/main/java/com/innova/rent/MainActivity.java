package com.innova.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    Button btn_login;

    public static Boolean isAdmin;

    // Login Credentials for user roles [ADMIN - MANAGER]
    String adminEmail = "admin@gmail.com";
    String adminPassword= "admin";
    String managerEmail = "manager@gmail.com";
    String managerPassword = "manager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.bt_login);
        
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // static credential verification

                    String enteredEmail =  email.getText().toString();   // getting entered credentials from ui
                    String enteredPassword = password.getText().toString();

                        if((enteredEmail.equals(adminEmail)) && (enteredPassword.equals(adminPassword)))  // if admin
                        {
                            isAdmin = true;
                            Toast.makeText(MainActivity.this, "Loged in as Admin!", Toast.LENGTH_SHORT).show();

                            // after sucessful login -- taking to dashboard [as admin]
                            Intent dashboardIntent = new Intent(getApplicationContext(), dashboard.class);
                            dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboardIntent);

                        }
                        else if ((enteredEmail.equals(managerEmail)) && (enteredPassword.equals(managerPassword))) // if manager
                        {
                            isAdmin = false;
                            Toast.makeText(MainActivity.this, "Loged in as Manager!", Toast.LENGTH_SHORT).show();

                            // after sucessful login -- taking to dashboard [as manager]
                            Intent managerDashboardIntent = new Intent(getApplicationContext(), managerDashboard.class);
                            managerDashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(managerDashboardIntent);
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
                        }
            }
        });
    }
}

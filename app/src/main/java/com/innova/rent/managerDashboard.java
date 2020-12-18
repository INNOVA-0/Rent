package com.innova.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class managerDashboard extends AppCompatActivity  implements View.OnClickListener {

    ImageButton record, rent, expenses, history;
    private Button logout;
    TextView mRentPaid, mRentRemaining;

    private Session session;
    Context cntx = this;

    static boolean isManager=false; // distinguish between logged in user roles

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);

        // inflating UI-Components
        record = findViewById(R.id.mangerRecord);
        rent = findViewById(R.id.managerRent);
        expenses = findViewById(R.id.managerExpenses);
        history = findViewById(R.id.managerHistory);
        logout = findViewById(R.id.managerLogout);

        mRentPaid= findViewById(R.id.monthlyRentRecieved);
        mRentRemaining = findViewById(R.id.monthlyRentRemaining);


        record.setOnClickListener(this);
        rent.setOnClickListener(this);
        expenses.setOnClickListener(this);
        history.setOnClickListener(this);
        logout.setOnClickListener(this);

        setStats();
    }

    @Override
    public void onClick(View view) {

        // layout of manager will be different from admin -- instead of creating multiple activities, changing UI components directly

        switch (view.getId()) {

            case R.id.mangerRecord:
                isManager=true;
                Intent recordIntent = new Intent(this, record.class);
                //recordIntent.putExtra("Role", true);
                //recordIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(recordIntent);
                break;

            case R.id.managerRent:
                isManager=true;
                Intent rentIntent = new Intent(getApplicationContext(), rent.class);
                rentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(rentIntent);
                break;

            case R.id.managerExpenses:
                isManager=true;
                Intent expensesIntent = new Intent(getApplicationContext(), expenditure.class);
                expensesIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(expensesIntent);
                break;

            case R.id.managerHistory:
                isManager=true;
                Intent historyIntent = new Intent(getApplicationContext(), history.class);
                historyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(historyIntent);
                break;

            case R.id.managerLogout:

                session = new Session(cntx);
                session.setRole("null");

                Intent logoutIntent = new Intent(getApplicationContext(), MainActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                Toast.makeText(this, "Logged Out!", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    public void setStats()
    {
        Helper helper;
        SQLiteDatabase db;
        // Database Configuration
        helper= new Helper(this);
        db = helper.getWritableDatabase();

        // geting mpnthly rent recieved -- all the time
        int monthlyRecieved= helper.getMonthlyRecievedRent(db);
        String m_recieved = String.valueOf(monthlyRecieved);
        mRentPaid.setText(m_recieved);

        // geting mothly remaining rent -- all the time
        int m_Remaining= helper.monthlyRemaining(db);
        String m_remaining = String.valueOf(m_Remaining);
        mRentRemaining.setText(m_remaining);
    }
}
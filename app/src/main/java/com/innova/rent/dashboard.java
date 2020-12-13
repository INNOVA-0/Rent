package com.innova.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class dashboard extends AppCompatActivity implements View.OnClickListener {

    ImageButton tenant, record, rent, expenses, history;
    private Button logout;
    TextView mRecieved, mRemaining, tRecieved, tRemaining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //Initializing UI components;
        tenant = findViewById(R.id.admin_tenant);
        record = findViewById(R.id.admin_record);
        rent = findViewById(R.id.admin_rent);
        expenses = findViewById(R.id.admin_expenses);
        history = findViewById(R.id.admin_history);
        logout = findViewById(R.id.admin_logout);

        mRecieved=findViewById(R.id.monthlyRecieved);
        mRemaining=findViewById(R.id.monthlyRemaining);
        tRecieved=findViewById(R.id.totalRecieved);
        tRemaining = findViewById(R.id.totalRemaining);

        tenant.setOnClickListener(this);
        record.setOnClickListener(this);
        rent.setOnClickListener(this);
        expenses.setOnClickListener(this);
        history.setOnClickListener(this);
        logout.setOnClickListener(this);

        setStats();


    }

    // onClick Listeners
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.admin_tenant:
                Intent tenatIntent = new Intent(getApplicationContext(), tenant.class);
                tenatIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(tenatIntent);
                break;

            case R.id.admin_record:
                Intent recordIntent = new Intent(getApplicationContext(), record.class);
                recordIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(recordIntent);
                break;

            case R.id.admin_rent:
                Intent rentIntent = new Intent(getApplicationContext(), rent.class);
                rentIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(rentIntent);
                break;

            case R.id.admin_expenses:
                Intent expensesIntent = new Intent(getApplicationContext(), expenditure.class);
                expensesIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(expensesIntent);
                break;

            case R.id.admin_history:
//                Intent historyIntent = new Intent(getApplicationContext(), history.class);
//                historyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(historyIntent);
                Toast.makeText(this, "History Layout will be inflated", Toast.LENGTH_SHORT).show();
                break;

            case R.id.admin_logout:
                Intent logoutIntent = new Intent(getApplicationContext(), MainActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        // geting total rent paid -- all the time
        int totalRent= helper.getTotalRecievedRent(db);
        String value = String.valueOf(totalRent);
        tRecieved.setText(value);

        // geting total rent remaining -- all the time
        int totalRemaining= helper.totalRemainingRent(db);
        String remaining = String.valueOf(totalRemaining);
        tRemaining.setText(remaining);

        // geting mpnthly rent recieved -- all the time
        int monthlyRecieved= helper.getMonthlyRecievedRent(db);
        String m_recieved = String.valueOf(monthlyRecieved);
        mRecieved.setText(m_recieved);

        // geting mothly remaining rent -- all the time
        int m_Remaining= helper.monthlyRemaining(db);
        String m_remaining = String.valueOf(m_Remaining);
        mRemaining.setText(m_remaining);
    }
}
package com.innova.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class managerDashboard extends AppCompatActivity  implements View.OnClickListener {

    ImageButton record, rent, expenses, history;
    private Button logout;

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


        record.setOnClickListener(this);
        rent.setOnClickListener(this);
        expenses.setOnClickListener(this);
        history.setOnClickListener(this);
        logout.setOnClickListener(this);
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
//                Intent historyIntent = new Intent(getApplicationContext(), history.class);
//                historyIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(historyIntent);
                Toast.makeText(this, "History Layout will be inflated", Toast.LENGTH_SHORT).show();
                break;

            case R.id.managerLogout:
                Intent logoutIntent = new Intent(getApplicationContext(), MainActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logoutIntent);
                Toast.makeText(this, "Logged Out!", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }
}
package com.innova.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class expenditure extends AppCompatActivity {

    Helper helper;
    SQLiteDatabase db;

    private ImageView avatar;
    private TextView role,monthly_expense;
    private EditText expenseAmount, expenseType, expenseDescription;
    private Button expenseSave;
    private managerDashboard manager;
    private static boolean adminIntent=true;  // required to decide wether to go back to admin or manager dashboard
    private int monthlyExpenses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure);

        manager= new managerDashboard();
        avatar= findViewById(R.id.expenditureAvatar);
        role= findViewById(R.id.expenditureRole);
        expenseAmount = findViewById(R.id.expenseAmount);
        expenseType = findViewById(R.id.expenseType);
        expenseDescription = findViewById(R.id.expenseDescription);
        expenseSave= findViewById(R.id.expenseSave);
        monthly_expense = findViewById(R.id.monthlyExpense);

        updateUI();

        // Database Configuration
        helper= new Helper(this);
        db = helper.getWritableDatabase();

        // get monthly expenses for current month
        monthlyExpenses= helper.getCurrentMonthExpenses(db);
        String value = String.valueOf(monthlyExpenses);
        monthly_expense.setText(value);



        // what to do when save button is pressed
        expenseSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( (TextUtils.isEmpty(expenseAmount.getText())) || (TextUtils.isEmpty(expenseType.getText())))
                { // feilds are required

                    Toast.makeText(expenditure.this, "Type and amount are required!", Toast.LENGTH_SHORT).show();
                }
                else if(adminIntent)
                {
                    dbEntry();
                    Intent backAdminDashboard = new Intent(getApplicationContext(), dashboard.class);
                    backAdminDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(backAdminDashboard);
                }
                else if(adminIntent== false)
                {
                    adminIntent=true; // dont ask me why :)
                    dbEntry();
                    Intent backManagerDashboard = new Intent(getApplicationContext(), managerDashboard.class);
                    backManagerDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(backManagerDashboard);
                }
                else
                {
                    Toast.makeText(expenditure.this, "Something's wrong!", Toast.LENGTH_SHORT).show();
                }

            }
        });
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

    public void dbEntry(){
        // fetchs the data from ui and sends it to helper function (insertExpenses) for retrieveal

        int amount= Integer.parseInt(expenseAmount.getText().toString());
        String type = expenseType.getText().toString();
        String description = expenseDescription.getText().toString();

        helper.insertExpenses(amount, type, description,db);
        Toast.makeText(this, "Insert Sucessfull!", Toast.LENGTH_SHORT).show();
    }
}
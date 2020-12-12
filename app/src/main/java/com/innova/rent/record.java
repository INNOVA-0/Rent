package com.innova.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class record extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView avatar;
    TextView role;
    Button creditSave;
    Helper helper;
    SQLiteDatabase db;
    EditText name, creditAmount,creditDescription;
    private Spinner idSpinner, blockSpinner;
    String idSelected,blockSelected;
    managerDashboard manager;
    private static boolean adminIntent=true;  // required to decide wether to go back to admin or manager dashboard

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        manager= new managerDashboard();

        avatar= findViewById(R.id.recordAvatar);
        role= findViewById(R.id.recordRole);
        idSpinner= findViewById(R.id.idSelectorR);
        blockSpinner= findViewById(R.id.blockSelectorR);
        name= findViewById(R.id.recordName);
        creditAmount= findViewById(R.id.creditAmount);
        creditDescription = findViewById(R.id.creditDescription);
        creditSave= findViewById(R.id.record_btn_save);


        updateUI();

        // Database Configuration
        helper= new Helper(this);
        db = helper.getWritableDatabase();


        // idSpinner infaltion and configuration
        idSpinner.setPromptId(R.string.selectID);
        ArrayAdapter<CharSequence> idAdapter = ArrayAdapter.createFromResource(this,R.array.id,android.R.layout.simple_spinner_item);
        idAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idSpinner.setAdapter(idAdapter);
        idSpinner.setOnItemSelectedListener(this);

        // blockSpinner infaltion and configuration
        blockSpinner.setPromptId(R.string.selectBlock);
        ArrayAdapter<CharSequence> blockAdapter = ArrayAdapter.createFromResource(this,R.array.blocks,android.R.layout.simple_spinner_item);
        blockAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        blockSpinner.setAdapter(blockAdapter);
        blockSpinner.setOnItemSelectedListener(this);

        // write everything to database
        creditSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( (TextUtils.isEmpty(creditAmount.getText())) || (TextUtils.isEmpty(name.getText())))
                { // feilds are required

                    Toast.makeText(record.this, "Please Enter all Values!", Toast.LENGTH_SHORT).show();
                }
                else if(adminIntent)
                {
                    fetchData();
                    Intent backAdminDashboard = new Intent(getApplicationContext(), dashboard.class);
                    backAdminDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(backAdminDashboard);
                }
                else if(adminIntent== false)
                {
                    adminIntent=true; // dont ask me why :)
                    fetchData();
                    Intent backManagerDashboard = new Intent(getApplicationContext(), managerDashboard.class);
                    backManagerDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(backManagerDashboard);
                }
                else
                {
                    Toast.makeText(record.this, "Something's wrong!", Toast.LENGTH_SHORT).show();
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

    public void fetchData(){
        // fetchs the data from ui and sends it to helper function (insertExpenses) for retrieveal

        int credit_amount= Integer.parseInt(creditAmount.getText().toString());
        String creditor_name = name.getText().toString();
        String description = creditDescription.getText().toString();
        int id = Integer.parseInt(idSelected);

        helper.insertCredit(id,blockSelected, creditor_name,credit_amount,description,db);
        Toast.makeText(this, "Credit Inserted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView.getId() == R.id.idSelectorR)
        {
            idSelected= adapterView.getItemAtPosition(i).toString();
//            Toast.makeText(record.this, "id selected"+ idSelected, Toast.LENGTH_SHORT).show();
        }

        if(adapterView.getId() == R.id.blockSelectorR)
        {
            blockSelected = adapterView.getItemAtPosition(i).toString();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
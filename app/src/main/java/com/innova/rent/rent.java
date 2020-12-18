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

public class rent extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageView avatar;
    private TextView role,rentRemaining,rentAmount, advanceRecieved;
    private managerDashboard manager;

    Helper helper;
    SQLiteDatabase db;
    Button saveRent;
    private Spinner idSpinner, blockSpinner;
    private EditText name,rentAmountRecieved, description;
    private String idSelected,blockSelected;
    private static boolean adminIntent=true;  // required to decide wether to go back to admin or manager dashboard

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);

        manager= new managerDashboard();
        avatar= findViewById(R.id.rentAvatar);
        role= findViewById(R.id.rentRole);
        saveRent= findViewById(R.id.rent_btn_save);
        idSpinner=findViewById(R.id.rentId);
        blockSpinner=findViewById(R.id.rentBlock);
        name= findViewById(R.id.rentName);
        rentAmountRecieved= findViewById(R.id.rentAmountRecieved);
        description= findViewById(R.id.rentDescription);
        rentRemaining = findViewById(R.id.remainingAmountT);
        rentAmount= findViewById(R.id.rentAmountT);
        advanceRecieved = findViewById(R.id.advanceAmountT);

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

//        if((idSpinner != null && idSpinner.getSelectedItem() !=null ) && (blockSpinner != null && blockSpinner.getSelectedItem() !=null )) {
//
//            int idRecieved = Integer.parseInt(idSelected);
////            String rent = String.valueOf(
//                    helper.getRent(idRecieved,blockSelected,db);
////            rentAmount.setText(rent);
//        }

        saveRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( (TextUtils.isEmpty(rentAmountRecieved.getText())) || (TextUtils.isEmpty(name.getText())))
                { // feilds are required

                    Toast.makeText(rent.this, "Please Enter required Values!", Toast.LENGTH_SHORT).show();

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
                    adminIntent=true;
                    fetchData();
                    Intent backManagerDashboard = new Intent(getApplicationContext(), managerDashboard.class);
                    backManagerDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(backManagerDashboard);
                }
                else
                {
                    Toast.makeText(rent.this, "Something's wrong!", Toast.LENGTH_SHORT).show();
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

        int rent_amount= Integer.parseInt(rentAmountRecieved.getText().toString());
        String creditor_name = name.getText().toString();
        String rent_description = description.getText().toString();
        int id = Integer.parseInt(idSelected);
        helper.insertRent(id,blockSelected, creditor_name,rent_amount,rent_description,db);
        Toast.makeText(this, "Rent Inserted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.rentId)
        {
            idSelected= adapterView.getItemAtPosition(i).toString();
            setStats();
            setName();
        }

        if(adapterView.getId() == R.id.rentBlock)
        {
            blockSelected = adapterView.getItemAtPosition(i).toString();
            setStats();
            setName();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setStats()
    {
        // Setting value for total rent of tenant
        try {
            int id = Integer.parseInt(idSelected);
            int amount = (helper.getRent(id,blockSelected,db));
//            Toast.makeText(this, "Rent :" + amount, Toast.LENGTH_SHORT).show();
            String rent_value = String.valueOf(amount);
            rentAmount.setText(rent_value);

        }catch(Exception e){
            String rent_value = String.valueOf(0);
            rentAmount.setText(rent_value);
         }

        // Setting value for advance recieved from tenant
        try {
            int id = Integer.parseInt(idSelected);
            int amount = (helper.getAdvance(id,blockSelected,db));
//            Toast.makeText(this, "Rent :" + amount, Toast.LENGTH_SHORT).show();
            String rent_value = String.valueOf(amount);
            advanceRecieved.setText(rent_value);

        }catch(Exception e){
            String rent_value = String.valueOf(0);
            advanceRecieved.setText(rent_value);
        }

        // Setting value for advance recieved from tenant
        try {
            int id = Integer.parseInt(idSelected);
            int amount = helper.getRemainingRent(id,blockSelected,db);
//            Toast.makeText(this, "Rent :" + amount, Toast.LENGTH_SHORT).show();
            String rent_value = String.valueOf(amount);
            rentRemaining.setText(rent_value);

        }catch(Exception e){
            Log.e("e_r", String.valueOf(e));
            String rent_value = String.valueOf(0);
            rentRemaining.setText(rent_value);
        }
    }

    public void setName()
    {
        String tenat_name;
        // Setting name of tenant
        try {
            int id = Integer.parseInt(idSelected);
            tenat_name = helper.getName(id,blockSelected,db);
            name.setText(tenat_name);

        }catch(Exception e){
//            Toast.makeText(this, "No Record Avaliable!", Toast.LENGTH_SHORT).show();
            name.setText("");
        }
    }
}
package com.innova.rent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class tenant extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Helper helper;
    SQLiteDatabase db;
    Button saveTenant;
    private Spinner idSpinner, blockSpinner;
    private EditText name,rentAmount, advanceAmount;
    private String idSelected,blockSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant);

        // inflating components
        idSpinner= findViewById(R.id.tenantID);
        blockSpinner= findViewById(R.id.tenantBlock);
        name= findViewById(R.id.tenantName);
        rentAmount= findViewById(R.id.rentAmount);
        advanceAmount= findViewById(R.id.advanceAmount);
        saveTenant= findViewById(R.id.tenant_btn_save);

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

        saveTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( (TextUtils.isEmpty(name.getText())) || (TextUtils.isEmpty(rentAmount.getText())))
                { // feilds are required

                    Toast.makeText(tenant.this, "Please Enter required Values!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    fetchData();
//                    Intent backAdminDashboard = new Intent(getApplicationContext(), dashboard.class);
//                    backAdminDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(backAdminDashboard);
                }

            }
        });

    }

    public void fetchData(){
        int advance_amount = 0;
        // fetchs the data from ui and sends it to helper function (insertExpenses) for retrieveal
        int id = Integer.parseInt(idSelected);
        String tenant_name = name.getText().toString();
        int rent_amount= Integer.parseInt(rentAmount.getText().toString());

        try { // advance feild is not required and can be null
            advance_amount= Integer.parseInt(advanceAmount.getText().toString());
        }
        catch(Exception e)
        {
            advance_amount = 0;
        }

        helper.insertTenant(id,blockSelected, tenant_name, rent_amount, advance_amount,db);
        Toast.makeText(this, "Tenant Inserted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        if(adapterView.getId() == R.id.tenantID)
        {
            idSelected= adapterView.getItemAtPosition(i).toString();
            setName();
//
        }

        if(adapterView.getId() == R.id.tenantBlock)
        {
            blockSelected = adapterView.getItemAtPosition(i).toString();
            setName();
//            Toast.makeText(tenant.this, "block selected"+ blockSelected, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setName()
    {
        String tenat_name;
        // Setting name of tenant
        try {
            int id = Integer.parseInt(idSelected);
            tenat_name = helper.getName(id,blockSelected,db);
            name.setText(tenat_name);
            setAdvance();
            setRent();

        }catch(Exception e){
//            Toast.makeText(this, "No Record Avaliable!", Toast.LENGTH_SHORT).show();
            name.setText("");
            advanceAmount.setText("");
        }
    }

    public void setAdvance()
    {
        try {
            int id = Integer.parseInt(idSelected);
            int advance = helper.getAdvance(id,blockSelected,db);
            String advance_value = String.valueOf(advance);
            advanceAmount.setText(advance_value);

        }catch(Exception e){
            advanceAmount.setText("");
        }
    }

    public void setRent()
    {
        try {
            int id = Integer.parseInt(idSelected);
            int rent = helper.getRent(id,blockSelected,db);
            String rent_value = String.valueOf(rent);
            rentAmount.setText(rent_value);

        }catch(Exception e){
            rentAmount.setText("");
        }
    }
}
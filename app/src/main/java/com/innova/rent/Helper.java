package com.innova.rent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Helper extends SQLiteOpenHelper {

    public static final String dbName = "rent";
    public static final int version = 1;

    public Helper(Context context)
    {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // generating required tables in database
        String createTenantTable = "CREATE TABLE TENANT (_id INTEGER PRIMARY KEY AUTOINCREMENT, ID INTEGER, BLOCK TEXT, NAME TEXT, RENT INTEGER, ADVANCE INTEGER)";
        db.execSQL(createTenantTable);

        String createRecordTable = "CREATE TABLE RECORD (_id INTEGER PRIMARY KEY AUTOINCREMENT, ID INTEGER, BLOCK TEXT, NAME TEXT, AMOUNT INTEGER, DESCRIPTION TEXT)";
        db.execSQL(createRecordTable);

        String createRentTable = "CREATE TABLE RENT (_id INTEGER PRIMARY KEY AUTOINCREMENT, ID INTEGER, BLOCK TEXT, NAME TEXT, RENTRECIEVED INTEGER, DESCRIPTION TEXT)";
        db.execSQL(createRentTable);

        String createExpensesTable = "CREATE TABLE EXPENSES (_id INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT INTEGER, TYPE TEXT, DESCRIPTION TEXT, DATE TEXT)";
        db.execSQL(createExpensesTable);
    }

    public void insertExpenses(int Amount, String type, String description, SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put("AMOUNT", Amount);
        values.put("TYPE", type);
        values.put("DESCRIPTION", description);
        values.put("DATE", getMonth());
        database.insert("EXPENSES", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String getMonth()
    {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        Log.d("Month",dateFormat.format(date));
        String month = dateFormat.format(date);
        return month;
    }

    public int getCurrentMonthExpenses(SQLiteDatabase database)
    {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        String month = dateFormat.format(date);

        Cursor res = database.rawQuery( "SELECT SUM(AMOUNT) FROM EXPENSES WHERE DATE = ?", new String[] { month },null);
        //res.moveToFirst();

        int amount=0;
        if (res == null)
            return amount;
        try{
            if (res.moveToFirst()) // Here i try to move to the first record -- in this case only record
                res.moveToFirst();
                amount = Integer.parseInt(res.getString(0)); // Only assign string value if we moved to first record
        }finally {
            res.close();
        }

        Log.e("amount", String.valueOf(amount));
        return amount;
    }
}

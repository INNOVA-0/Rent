package com.innova.rent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        String createExpensesTable = "CREATE TABLE EXPENSES (_id INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT INTEGER, TYPE TEXT, DESCRIPTION TEXT)";
        db.execSQL(createExpensesTable);
    }

    public void insertExpenses(int Amount, String type, String description, SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put("AMOUNT", Amount);
        values.put("TYPE", type);
        values.put("DESCRIPTION", description);
        database.insert("EXPENSES", null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

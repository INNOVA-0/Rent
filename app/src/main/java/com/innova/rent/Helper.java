package com.innova.rent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.security.PublicKey;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Helper extends SQLiteOpenHelper {

    public static final String dbName = "rent";
    public static final int version = 1;

    private SQLiteDatabase db = this.getWritableDatabase(); // intial insertion of values

    public Helper(Context context)
    {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // generating required tables in database
        String createTenantTable = "CREATE TABLE TENANT (_id INTEGER PRIMARY KEY AUTOINCREMENT, ID INTEGER, BLOCK TEXT, NAME TEXT, RENT INTEGER, ADVANCE INTEGER,DATE TEXT)";
        db.execSQL(createTenantTable);

        String createRecordTable = "CREATE TABLE RECORD (_id INTEGER PRIMARY KEY AUTOINCREMENT, ID INTEGER, BLOCK TEXT, NAME TEXT, AMOUNT INTEGER, DESCRIPTION TEXT,DATE TEXT)";
        db.execSQL(createRecordTable);

        String createRentTable = "CREATE TABLE RENT (_id INTEGER PRIMARY KEY AUTOINCREMENT, ID INTEGER, BLOCK TEXT, NAME TEXT, RENTRECIEVED INTEGER, DESCRIPTION TEXT,DATE TEXT)";
        db.execSQL(createRentTable);

        String createExpensesTable = "CREATE TABLE EXPENSES (_id INTEGER PRIMARY KEY AUTOINCREMENT, AMOUNT INTEGER, TYPE TEXT, DESCRIPTION TEXT, DATE TEXT)";
        db.execSQL(createExpensesTable);

          /*
          * if the databse enteries are null initially exception handling produces numeroues bugs
          * so the best solution is :
          *  -> enter '0' values at place of amount in all tables
          *  -> and handle all the exceptions afterwards
          * */
        insertExpenses(0,"","", db);
        insertCredit(0,"","",0,"",db);
        insertRent(0,"","",0,"",db);
        insertTenant(0,"","",0,0,db);

    }

    public void insertExpenses(int Amount, String type, String description, SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put("AMOUNT", Amount);
        values.put("TYPE", type);
        values.put("DESCRIPTION", description);
        values.put("DATE", getMonth());
        database.insert("EXPENSES", null, values);
    }

    public void insertCredit(int id,String block, String name, int creditAmount, String description, SQLiteDatabase database)
    {
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("BLOCK", block);
        values.put("NAME", name);
        values.put("AMOUNT", creditAmount);
        values.put("DESCRIPTION", description);
        values.put("DATE", getMonth());
        database.insert("RECORD", null, values);
    }

    public void insertRent(int id,String block, String name, int rentRecieved, String description, SQLiteDatabase database){
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("BLOCK", block);
        values.put("NAME", name);
        values.put("RENTRECIEVED", rentRecieved);
        values.put("DESCRIPTION", description);
        values.put("DATE", getMonth());
        database.insert("RENT", null, values);
    }

    public void insertTenant(int id, String block,String name,int rent, int advance, SQLiteDatabase database)
    {
        ContentValues values = new ContentValues();
        values.put("ID", id);
        values.put("BLOCK", block);
        values.put("NAME", name);
        values.put("RENT", rent);
        values.put("ADVANCE", advance);
        values.put("DATE", getMonth());
        database.insert("TENANT", null, values);
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

    public int getExpenses(SQLiteDatabase database) // get total expenses irresepctive of month
    {
        Cursor res = database.rawQuery( "SELECT SUM(AMOUNT) FROM EXPENSES",null);
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

        Log.e("total amount", String.valueOf(amount));
        return amount;
    }

    public int getRent(int id, String block, SQLiteDatabase database)
    {
        Cursor res = database.rawQuery( "SELECT SUM(RENT) FROM TENANT WHERE ID = ? AND BLOCK =?", new String[] {String.valueOf(id), block},null);
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

        Log.e("total_rent_payable", String.valueOf(amount));
        return amount;
    }

    public int rentPaid(int id, String block, SQLiteDatabase database)
    {
        Cursor res = database.rawQuery( "SELECT SUM(RENTRECIEVED) FROM RENT WHERE ID = ? AND BLOCK =?", new String[] {String.valueOf(id), block},null);
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

        Log.e("total rent payed", String.valueOf(amount));
        return amount;
    }

    public int getAdvance(int id, String block, SQLiteDatabase database)
    {
        Cursor res = database.rawQuery( "SELECT SUM(ADVANCE) FROM TENANT WHERE ID = ? AND BLOCK =?", new String[] {String.valueOf(id), block},null);
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

        Log.e("total advance recieved", String.valueOf(amount));
        return amount;
    }

    public int getRecord(int id, String block, SQLiteDatabase database)
    {
        Cursor res = database.rawQuery( "SELECT SUM(AMOUNT) FROM RECORD WHERE ID = ? AND BLOCK =?", new String[] {String.valueOf(id), block},null);
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

        Log.e("total_rent_recieved", String.valueOf(amount));
        return amount;
    }

    public int getRemainingRent(int id, String block, SQLiteDatabase database)
    {
        int advancePaid,khata,rent_paid,totalRent;

        try {  // only exception will be NULL value from database .. so replacing NULL with int '0'
             advancePaid = getAdvance(id, block, database);
        }catch(Exception e){
             advancePaid = 0;
        }

        try {
             khata= getRecord(id, block, database);
        }catch(Exception e){
             khata = 0;
        }

        try {
             rent_paid = rentPaid(id, block, database);
        }catch(Exception e){
             rent_paid = 0;
        }

        try {
             totalRent = getRent(id, block, database);
        }catch(Exception e){
             totalRent = 0;
        }



        int totalPaid = advancePaid + khata + rent_paid;
        int remainingRent = totalRent - totalPaid;
        //remainingRent = Math.abs(remainingRent);

        Log.e("rent_remaining", String.valueOf(remainingRent));
        return  remainingRent;
    }

    public int getTotalRecievedRent(SQLiteDatabase database)
    {
        int amount = 0;
        // geting rent recieved
        Cursor res = database.rawQuery( "SELECT SUM(RENTRECIEVED) FROM RENT",null);
        int rent_recieved=0;
        if (res == null)
        {
            rent_recieved=0;
        }
        else {
            try{
                if (res.moveToFirst()) // Here i try to move to the first record -- in this case only record
                    res.moveToFirst();
                rent_recieved = Integer.parseInt(res.getString(0)); // Only assign string value if we moved to first record
                Log.e("r_recieved", String.valueOf(rent_recieved));
            }finally {
                res.close();
            }
        }

        // geting advance recieved
        Cursor advanceQuery = database.rawQuery( "SELECT SUM(ADVANCE) FROM TENANT",null);
        int advance_recieved=0;
        if (advanceQuery == null)
        {
            advance_recieved=0;
        }
        else {
            try{
                if (advanceQuery.moveToFirst()) // Here i try to move to the first record -- in this case only record
                    advanceQuery.moveToFirst();
                advance_recieved = Integer.parseInt(advanceQuery.getString(0)); // Only assign string value if we moved to first record
                Log.e("a_recieved", String.valueOf(advance_recieved));
            }finally {
                res.close();
            }
        }

        amount = rent_recieved+ advance_recieved;
        Log.e("total_recieved", String.valueOf(amount));
        return amount;
    }

    public int totalRemainingRent(SQLiteDatabase database)
    {
        int totalRamianing =0, idCounter=0, blockCounter =0;

        int idArr[]= new int[20];
        String blockArr[]= new String[10];

        Cursor res = database.rawQuery( "SELECT ID,BLOCK FROM TENANT WHERE ID>0",null);

        if(res== null)
            return totalRamianing;

        if (res.moveToFirst()){
                do{
                    String id = res.getString(res.getColumnIndex("ID"));  // getting all ids of tenats -- in database
                    idArr[idCounter]= Integer.parseInt(id);
                    idCounter++;

                    String block = res.getString(res.getColumnIndex("BLOCK"));
                    blockArr[blockCounter]= block;
                    blockCounter++;
                    Log.e("cursor_R", id+ block + blockCounter);
                    // do what ever you want here
                }while(res.moveToNext());
            }
            res.close();

        int iCounter=0;
        int bCounter=0;

        while(iCounter<idCounter)
        {
            totalRamianing += remaining(idArr[iCounter],blockArr[bCounter],database);
            Log.e("t_remaining", String.valueOf(totalRamianing));
            iCounter++;
            bCounter++;
        }
//        for(int idIndex=0; idIndex<20; idIndex++)
//        {
//            for(int blockIndex=0; blockIndex<10; blockIndex++)
//            {
//                totalRamianing += remaining(ID[idIndex],BLOCK[blockIndex],database);
//            }
//        }
        Log.e("t_remaining", String.valueOf(totalRamianing));
        return totalRamianing;
    }

    public int remaining(int id, String block, SQLiteDatabase database)
    {
        int advancePaid=0,khata=0,rent_paid=0,totalRent=0;

        try {  // only exception will be NULL value from database .. so replacing NULL with int '0'
            advancePaid = getAdvance(id, block, database);
        }catch(Exception e){
            advancePaid = 0;
        }

        try {
            khata= getRecord(id, block, database);
        }catch(Exception e){
            khata = 0;
        }

        try {
            rent_paid = rentPaid(id, block, database);
        }catch(Exception e){
            rent_paid = 0;
        }

        try {
            totalRent = getRent(id, block, database);
        }catch(Exception e){
            totalRent = 0;
        }

        int totalPaid = advancePaid + khata + rent_paid;
        int remainingRent = totalRent - totalPaid;
        remainingRent = Math.abs(remainingRent);

        Log.e("rent_remaining", String.valueOf(remainingRent));
        return  remainingRent;
    }

    public int getMonthlyRecievedRent(SQLiteDatabase database)
    {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        String month = dateFormat.format(date);

        int amount = 0;
        // geting rent recieved
        Cursor res = database.rawQuery( "SELECT SUM(RENTRECIEVED) FROM RENT WHERE DATE = ?", new String[] { month },null);
        int rent_recieved=0;
        if (res == null)
        {
            rent_recieved=0;
        }
        else {
            try{
                if (res.moveToFirst()) // Here i try to move to the first record -- in this case only record
                    res.moveToFirst();
                rent_recieved = Integer.parseInt(res.getString(0)); // Only assign string value if we moved to first record
                Log.e("r_recieved", String.valueOf(rent_recieved));
            }finally {
                res.close();
            }
        }

        // geting advance recieved
        Cursor advanceQuery = database.rawQuery( "SELECT SUM(ADVANCE) FROM TENANT WHERE DATE = ?", new String[] { month },null);
        int advance_recieved=0;
        if (advanceQuery == null)
        {
            advance_recieved=0;
        }
        else {
            try{
                if (advanceQuery.moveToFirst()) // Here i try to move to the first record -- in this case only record
                    advanceQuery.moveToFirst();
                advance_recieved = Integer.parseInt(advanceQuery.getString(0)); // Only assign string value if we moved to first record
                Log.e("a_recieved", String.valueOf(advance_recieved));
            }finally {
                res.close();
            }
        }

        amount = rent_recieved+ advance_recieved;
        Log.e("monthly_recieved", String.valueOf(amount));
        return amount;
    }

    public int monthlyRemaining(SQLiteDatabase database)
    {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        String month = dateFormat.format(date);

        int totalRamianing =0, idCounter=0, blockCounter =0;

        int idArr[]= new int[20];
        String blockArr[]= new String[10];

        Cursor res = database.rawQuery( "SELECT ID,BLOCK FROM TENANT WHERE ID>0 AND DATE = ?", new String[] { month },null);

        if(res== null)
            return totalRamianing;

        if (res.moveToFirst()){
            do{
                String id = res.getString(res.getColumnIndex("ID"));  // getting all ids of tenats -- in database
                idArr[idCounter]= Integer.parseInt(id);
                idCounter++;

                String block = res.getString(res.getColumnIndex("BLOCK"));
                blockArr[blockCounter]= block;
                blockCounter++;
                Log.e("cursor_R", id+ block + blockCounter);
                // do what ever you want here
            }while(res.moveToNext());
        }
        res.close();

        int iCounter=0;
        int bCounter=0;

        while(iCounter<idCounter)
        {
            totalRamianing += remaining(idArr[iCounter],blockArr[bCounter],database);
            Log.e("m_remaining", String.valueOf(totalRamianing));
            iCounter++;
            bCounter++;
        }

        Log.e("m_remaining", String.valueOf(totalRamianing));
        return totalRamianing;
    }

    public ArrayList<TenantStats> getTenantStats(SQLiteDatabase database, int selection)
    {
        ArrayList<TenantStats> tenantList = new ArrayList<>();

        int paidRent=0, advance =0;

        Cursor res = database.rawQuery( "SELECT ID,BLOCK,NAME,RENT FROM TENANT WHERE ID>0",null);

        if(res== null)
            return tenantList;

        if (res.moveToFirst()){
            do{
                TenantStats tenant = new TenantStats();

                String id = res.getString(res.getColumnIndex("ID"));  // getting all ids of tenats -- in database

                String block = res.getString(res.getColumnIndex("BLOCK"));  // setting remaining rent for object
                tenant.setRemainingRent(remaining(Integer.parseInt(id),block,database));


                String name = res.getString(res.getColumnIndex("NAME"));    // setting name of object
                tenant.setName(name);


                String rent = res.getString(res.getColumnIndex("RENT"));    // setting total rent for object
                tenant.setRent(Integer.parseInt(rent));

                try {
                    paidRent = rentPaid(Integer.parseInt(id),block,database);
                }catch(Exception e) {
                    paidRent = 0;
                }

                try {
                    advance = getAdvance(Integer.parseInt(id),block,database);
                }catch(Exception e) {
                    advance = 0;
                }

                int totalPaid = paidRent + advance;
                tenant.setRecievedRent(totalPaid);                                       // setting totalPaid rent for object

                tenantList.add(tenant);

                Log.e("cursor_R", id+ block);

            }while(res.moveToNext());
        }
        res.close();

        if(selection == 0)
            return tenantList;

        tenantList = filterTenant(tenantList,selection);

        return tenantList;

    }

    public String getName(int id, String block, SQLiteDatabase database) {
        Cursor res = database.rawQuery( "SELECT NAME FROM TENANT WHERE ID = ? AND BLOCK =?", new String[] {String.valueOf(id), block},null);
        String name = "";
        if (res == null)
            return name;
        try{
            if (res.moveToFirst())
                res.moveToFirst();
            name = res.getString(0);
        }finally {
            res.close();
        }

        return name;
    }

    public ArrayList<TenantStats> filterTenant(ArrayList<TenantStats> tenants, int selection)
    {
        // 0 is ALL, 1 -> isremaining, 2 -> is sum

        for (int items = 0 ; items < tenants.size(); items++)
        {
            TenantStats tenant= tenants.get(items);
            int remainingRent=tenant.getRemainingRent();
            int rentPaid = tenant.getRecievedRent();

            if(selection == 1 && !(remainingRent > 0))
            {
                tenants.remove(items);
            }
            else if (selection == 2 && !(remainingRent == 0))
            {
                tenants.remove(items);
            }
        }

        return tenants;
    }

}

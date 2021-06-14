package com.rhyme.modiriathesab;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.SurfaceControl;

import java.util.ArrayList;


public class myDbAdapter {
    myDbHelper myhelper;
    public myDbAdapter(Context context)
    {
        myhelper = new myDbHelper(context);
    }

    public long insertUser(String name, String email, String password)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.USERNAME, name);
        contentValues.put(myDbHelper.EMAIL, email);
        contentValues.put(myDbHelper.PASSWORD, password);
        contentValues.put(myDbHelper.TOTAL, 0);
        long id = dbb.insert(myDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public long insertTransaction(String name, int amount, String title)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(myDbHelper.USERNAME, name);
        contentValues.put(myDbHelper.AMOUNT, amount);
        contentValues.put(myDbHelper.TITLE, title);
        long id = dbb.insert(myDbHelper.TABLE_NAME_2, null , contentValues);
        return id;
    }


    public ArrayList<Transaction> getTransactions(String username)
    {
        ArrayList<Transaction> result = new ArrayList<>();
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String[] columns = {myDbHelper.TITLE,myDbHelper.AMOUNT};
        Cursor cursor =db.rawQuery("SELECT * FROM accounts WHERE username=? ", new String[]{username});
        while (cursor.moveToNext())
        {
            int amount =cursor.getInt(cursor.getColumnIndex(myDbHelper.AMOUNT));
            String title =cursor.getString(cursor.getColumnIndex(myDbHelper.TITLE));
            result.add(new Transaction(username, title, amount));
        }
        cursor.close();
        return result;
    }

    public boolean isValidUser(String username, String password)
    {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        Cursor cursor =db.rawQuery("SELECT * FROM users WHERE username=? AND password=?",new String[]{username, password});
        if (cursor.moveToFirst()){
            do {
                String c = cursor.getString(0);
                if(c!=null)
                {
                    cursor.close();
                    return true;
                }
            } while(cursor.moveToNext());
        }
        cursor.close();
        return false;
    }

    public void addTotal(String username, int amount)
    {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String old = "";
        Cursor cursor =db.rawQuery("SELECT total FROM users WHERE username=?",new String[]{username});
        if (cursor.moveToFirst()){
            do {
                old  = cursor.getString(0);
            } while(cursor.moveToNext());
        }
        cursor.close();
        int newValue = Integer.parseInt(old) + amount;
        SQLiteDatabase db2 = myhelper.getWritableDatabase();
        db2.execSQL("UPDATE users SET total=? WHERE username=?",new String[]{String.valueOf(newValue), username});
    }

    public void reduceTotal(String username, int amount)
    {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String old = "";
        Cursor cursor =db.rawQuery("SELECT total FROM users WHERE username=?",new String[]{username});
        if (cursor.moveToFirst()){
            do {
                old  = cursor.getString(0);
            } while(cursor.moveToNext());
        }
        cursor.close();
        int newValue = Integer.parseInt(old) - amount;
        SQLiteDatabase db2 = myhelper.getWritableDatabase();
        db2.execSQL("UPDATE users SET total=? WHERE username=?",new String[]{String.valueOf(newValue), username});
    }

    public int getTotal(String username)
    {
        SQLiteDatabase db = myhelper.getReadableDatabase();
        String value = "";
        Cursor cursor =db.rawQuery("SELECT total FROM users WHERE username=?",new String[]{username});
        if (cursor.moveToFirst()){
            do {
                value  = cursor.getString(0);
                return Integer.parseInt(value);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return 0;
    }

    static class myDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "myDatabase";
        private static final String TABLE_NAME = "users";
        private static final String TABLE_NAME_2 = "accounts";
        private static final int DATABASE_Version = 1;
        private static final String USERNAME = "username";
        private static final String PASSWORD= "password";
        private static final String EMAIL= "email";
        private static final String TOTAL = "total";
        private static final String AMOUNT = "amount";
        private static final String TITLE = "title";
        private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME+
                " ("+ USERNAME +" VARCHAR(255) ,"+ PASSWORD +" VARCHAR(225),"+ TOTAL +" INT(20) ,"+ EMAIL + " VARCHAR(255));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private static final String CREATE_TABLE_2 = "CREATE TABLE IF NOT EXISTS "+TABLE_NAME_2+
                " ("+ USERNAME + " VARCHAR(255) ," + AMOUNT +" INT(20) ,"+ TITLE + " VARCHAR(255));";
        private static final String DROP_TABLE_2 ="DROP TABLE IF EXISTS "+TABLE_NAME_2;
        private Context context;

        public myDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;

        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
                db.execSQL(CREATE_TABLE_2);
            } catch (Exception e) {
                Log.i("mess",""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Log.i("mess","on upgrade");
                db.execSQL(DROP_TABLE);
                db.execSQL(DROP_TABLE_2);
                onCreate(db);
            }catch (Exception e) {
                Log.i("mess",""+e);
            }
        }
    }
}
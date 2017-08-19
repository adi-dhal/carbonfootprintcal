package com.example.adityadhall.carbon;

/**
 * Created by adityadhall on 16/8/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "record.db";
    public static final String TABLE_NAME = "carbonfp";
    public static final String COLUMN_ID = "_ID";
    public static final String Date = "DATE";
    public static final String COLUMN_VALUES = "FP";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version ){
        super( context, DATABASE_NAME, factory, DATABASE_VERSION );
    }

    @Override
    public void onCreate( SQLiteDatabase db ){
        String query = "CREATE TABLE " + TABLE_NAME + " ('_ID' INTEGER PRIMARY KEY AUTOINCREMENT, '" + Date +"' INTEGER, '"+
                COLUMN_VALUES + "' FLOAT)";
        db.execSQL( query );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ){
        db.execSQL( "DROP IF TABLE EXISTS " + TABLE_NAME );
        onCreate( db );
    }

    public long rec_val(int date , float carb_val ){
        long res=0;
        ContentValues value = new ContentValues();
        value.put("Date",date);
        value.put("values",carb_val);
        SQLiteDatabase db = this.getWritableDatabase();
        String query_2 ="INSERT INTO carbonfp (DATE,FP) VALUES("+date+","+carb_val+")";
        db.execSQL(query_2);
        /*if(db.isOpen())
            res= db.insert("carbonfp", null, value );
        return res;*/
        return 0;
    }

    public  Float ret_val(int date){
        Float ret_value=(float)0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + TABLE_NAME+" WHERE "+Date+" = '"+String.valueOf(date)+"'", null );
        if(cursor.moveToFirst()) {// while( !cursor.isAfterLast() ){
            int col = cursor.getColumnIndex(COLUMN_VALUES);
            ret_value = cursor.getFloat(col);
            //   cursor.moveToNext();
            //}
        }
        String error="sddhaf";
        cursor.close();
        db.close();
        return ret_value;
    }
}
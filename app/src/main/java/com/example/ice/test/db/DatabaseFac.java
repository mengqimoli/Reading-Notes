package com.example.ice.test.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ice on 4/18/17.
 */

public class DatabaseFac {
    private DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DatabaseFac(Context context){
        dbHelper=new DBHelper(context,"dbBookNotes",null,1);
    }
    public SQLiteDatabase getWrite(){
        sqLiteDatabase=dbHelper.getWritableDatabase();
        return sqLiteDatabase;
    }
    public SQLiteDatabase getRead(){
        sqLiteDatabase=dbHelper.getReadableDatabase();
        return sqLiteDatabase;
    }
    public boolean close(){
        sqLiteDatabase.close();
        dbHelper.close();
        return true;
    }
}

package com.example.ice.test.abstractclass;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

/**
 * Created by ice on 4/18/17.
 */

public abstract class Manager {
    protected SQLiteDatabase sqLiteDatabase;
    public Manager(SQLiteDatabase db){
        sqLiteDatabase=db;
    }
    public abstract void insert(Object object);
    public abstract void delete(String id);
    public abstract void query(String id);
    public abstract void update(String id, Object object);
    public boolean executeSql(String sql) throws Exception{
        sqLiteDatabase.execSQL(sql);
        return true;
    }
    public Cursor rawQuery(String sql,String[] args)
    {
        Cursor cursor=sqLiteDatabase.rawQuery(sql,args);
        return cursor;
    }

    public void close(){
        if(sqLiteDatabase!=null){
            sqLiteDatabase.close();
        }
    }
}

package com.example.ice.test.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/12/10.
 */

public class DBHelper extends SQLiteOpenHelper{
    private static final String TAG="BookNote";
    private static final int VERSION=1;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql1="create table BookClassify(bookid varchar(50) primary key,ClassifyName varchar(50))";
        String sql2="create table Book(bookid varchar(50) primary key,name varchar(50),author varchar(50),press varchar(50),ISBN varchar(50),classify varchar(50))";
        String sql3="create table user(userid varchar(50) primary key,name varchar(50),password varchar(50))";
        String sql4="create table notes(userid varchar(50),bookid varchar(50),date varchar(50),content varchar(1000)," +
                "primary key(userid,bookid,date),foreign key (userid) references user(userid)," +
                "foreign key (bookid) references Book(bookid))";
        Log.i(TAG,"create database ---------------------------");
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"update database-------------------------------");
    }
}

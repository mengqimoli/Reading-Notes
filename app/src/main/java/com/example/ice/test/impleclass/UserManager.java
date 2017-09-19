package com.example.ice.test.impleclass;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.ice.test.abstractclass.Manager;
import com.example.ice.test.entity.User;

/**
 * Created by ice on 4/25/17.
 */

public class UserManager extends Manager {
    public UserManager(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public void insert(Object object) {
        User user=(User)object;
        ContentValues contentValues=new ContentValues();
        contentValues.put("userid",user.getUserid());
        contentValues.put("name",user.getName());
        contentValues.put("password",user.getPassword());
        sqLiteDatabase.insert("user",null,contentValues);
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public void query(String id) {

    }

    @Override
    public void update(String id, Object object) {

    }
}

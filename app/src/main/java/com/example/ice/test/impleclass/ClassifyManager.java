package com.example.ice.test.impleclass;


import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.ice.test.abstractclass.Manager;
import com.example.ice.test.entity.BookClassify;

import butterknife.OnItemSelected;

/**
 * Created by ice on 4/27/17.
 */

public class ClassifyManager extends Manager{
    public ClassifyManager(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public void insert(Object object) {
        BookClassify bookClassify=(BookClassify)object;
        ContentValues contentValues=new ContentValues();
        contentValues.put("bookid",bookClassify.getBookid());
        contentValues.put("ClassifyName",bookClassify.getClassifyName());
        sqLiteDatabase.insert("BookClassify",null,contentValues);
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

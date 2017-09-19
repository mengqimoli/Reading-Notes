package com.example.ice.test.impleclass;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.ice.test.abstractclass.Manager;
import com.example.ice.test.entity.Book;
import com.example.ice.test.entity.Note;

/**
 * Created by ice on 4/27/17.
 */

public class NotesManager extends Manager {
    public NotesManager(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public void insert(Object object) {
        Note note=(Note)object;
        ContentValues contentValues=new ContentValues();
        contentValues.put("bookid",note.getBookid());
        contentValues.put("userid",note.getUserid());
        contentValues.put("date",note.getDate());
        contentValues.put("content",note.getContent());
        sqLiteDatabase.insert("notes",null,contentValues);
    }

    @Override
    public void delete(String id) {
        //设计不合理   参数应为 字符串组
    }

    @Override
    public void query(String id) {

    }

    @Override
    public void update(String id, Object object) {

    }
}

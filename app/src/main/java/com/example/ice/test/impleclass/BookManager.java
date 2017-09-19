package com.example.ice.test.impleclass;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ice.test.abstractclass.Manager;
import com.example.ice.test.entity.Book;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Created by ice on 4/17/17.
 */

public class BookManager extends Manager{

    public BookManager(SQLiteDatabase db) {
        super(db);
    }

    @Override
    public void insert(Object object) {
        Book book=(Book)object;
        ContentValues contentValues=new ContentValues();
        contentValues.put("bookid",book.getBookId());
        contentValues.put("name",book.getName());
        contentValues.put("author",book.getAuthor());
        contentValues.put("press",book.getPress());
        contentValues.put("ISBN",book.getISBN());
        contentValues.put("classify",book.getClassify());
        sqLiteDatabase.insert("Book",null,contentValues);
    }

    @Override
    public void delete(String id) {
        sqLiteDatabase.delete("Book","bookid=?",new String[]{id});
    }

    @Override
    public void query(String id) {
     //   Cursor cursor=sqLiteDatabase.query("Book",);
    }

    @Override
    public void update(String id, Object object){
        Book book=(Book)object;
        ContentValues contentValues=new ContentValues();
        if(book.getAuthor()!=null)
        {   contentValues.put("author",book.getAuthor()); }
        if(book.getClassify()!=null)
        {   contentValues.put("classify",book.getClassify());}
        if(book.getISBN()!=null)
        {contentValues.put("ISBN",book.getISBN());}
        if(book.getName()!=null)
        {contentValues.put("name",book.getName());}
        if(book.getPress()!=null)
        { contentValues.put("press",book.getPress());}

        sqLiteDatabase.update("Book",contentValues,"bookid=?",new String[]{book.getBookId()});


    }
}

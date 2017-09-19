package com.example.ice.test.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/12/10.
 * 用来将 得到的新闻信息（格式为arraylist） 存储到数据 库中
 */

public class NewInsert {
    public static boolean newinsert(ArrayList<HashMap<String,String>> arrayList, SQLiteDatabase database)
    {
        ContentValues values=new ContentValues();
        for(int i=0;i<arrayList.size();i++)
        {
            HashMap<String,String> map=arrayList.get(i);
            values.put("id",Integer.toString(i));
            values.put("title",map.get("title"));
            values.put("content",map.get("content"));
            values.put("newstime",map.get("newstime"));
            database.insert("news",null,values);
            values.clear();
        }
        return true;
    }
}

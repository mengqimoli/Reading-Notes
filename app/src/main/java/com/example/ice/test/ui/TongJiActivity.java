package com.example.ice.test.ui;

import android.database.Cursor;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ice.test.MyApplication;
import com.example.ice.test.R;
import com.example.ice.test.db.DatabaseFac;
import com.example.ice.test.impleclass.NotesManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TongJiActivity extends AppCompatActivity {

    @InjectView(R.id.tongji_swipe)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.tongji_lv)
    protected ListView listView;
    private String userid;
    private ArrayList<HashMap<String,String>> listitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tong_ji);
        ButterKnife.inject(this);

        MyApplication myApplication=(MyApplication)getApplication();
        userid=myApplication.getUsernmame();
        listitems=new ArrayList<>();
    }
    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
    private void init(){
        System.out.println("huizongActivity  init");
        String sql="select name,count(*) from notes,Book where notes.bookid=Book.bookid and notes.userid='"+userid+"' group by notes.bookid";
        NotesManager notesManager=new NotesManager(new DatabaseFac(getApplicationContext()).getRead());
        Cursor cursor=notesManager.rawQuery(sql,null);
        while (cursor.moveToNext()){
            HashMap<String,String> map=new HashMap<>();
            map.put("bookname","书籍名称："+cursor.getString(0));
            map.put("number","笔记数量："+cursor.getString(1));
            System.out.println(cursor.getString(0));
            listitems.add(map);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),listitems,
                R.layout.tongji_items,new String[]{"bookname","number"},
                new int[]{R.id.tongji_lv_item_bookname,R.id.tongji_lv_item_number});
        simpleAdapter.notifyDataSetInvalidated();
        listView.setAdapter(simpleAdapter);
    }
}

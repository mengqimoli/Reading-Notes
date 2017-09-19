package com.example.ice.test.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.ice.test.R;
import com.example.ice.test.db.DatabaseFac;
import com.example.ice.test.impleclass.NotesManager;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HuiZongActivity extends AppCompatActivity {

    @InjectView(R.id.huizong_swipe)
    protected  SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.huizong_lv)
    protected ListView listView;

    private ArrayList<HashMap<String,String>> listitems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hui_zong);
        ButterKnife.inject(this);

        listitems=new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    private void init(){
        System.out.println("huizongActivity  init");
        String sql="select name,count(*) from notes,user where notes.userid=user.userid group by notes.userid";
        DatabaseFac databaseFac=new DatabaseFac(getApplicationContext());
        NotesManager notesManager=new NotesManager(databaseFac.getRead());
        Cursor cursor=notesManager.rawQuery(sql,null);
        while (cursor.moveToNext()){
            HashMap<String,String> map=new HashMap<>();
            map.put("username","用户名："+cursor.getString(0));
            map.put("number","笔记数量："+cursor.getString(1));
            System.out.println(cursor.getString(0));
            listitems.add(map);
        }
        databaseFac.close();
        SimpleAdapter simpleAdapter=new SimpleAdapter(getApplicationContext(),listitems,
                R.layout.huizong_items,new String[]{"username","number"},
                new int[]{R.id.huizong_lv_item_username,R.id.huizong_lv_item_number});
        simpleAdapter.notifyDataSetInvalidated();
        listView.setAdapter(simpleAdapter);
    }
}

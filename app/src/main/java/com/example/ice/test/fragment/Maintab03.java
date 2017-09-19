package com.example.ice.test.fragment;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.ice.test.MyApplication;
import com.example.ice.test.R;
import com.example.ice.test.db.DatabaseFac;
import com.example.ice.test.impleclass.NotesManager;
import com.example.ice.test.ui.AddNoteActivity;
import com.example.ice.test.ui.NoteInfoActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class Maintab03 extends Fragment {

    private Button button;

    private ArrayList<HashMap<String,String>> listitem=new ArrayList<>();

    @InjectView(R.id.maintab03_swipeRefresh)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @InjectView(R.id.maintab03_listView)
    protected ListView listView;
    private SimpleAdapter simpleAdapter;
    private String userid;

    public Maintab03() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_maintab03,container,false);
        ButterKnife.inject(this,view);
        //获取application中用户编号
        MyApplication myApplication=(MyApplication)getActivity().getApplication();
        userid=myApplication.getUsernmame();


        button=(Button)view.findViewById(R.id.maintab03_add);

        // 添加笔记按钮 单击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), AddNoteActivity.class);
                startActivity(intent);
            }
        });
        //填充listitem
        updateListView();
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                updateListView();
            }
        });
    }
    //listview 单击 item
    @OnItemClick(R.id.maintab03_listView)
    public void itemClick(AdapterView<?> parent, View view, int position, long id){
        Intent intent=new Intent();
        intent.putExtra("content",listitem.get(position).get("content"));
        intent.setClass(getActivity(),NoteInfoActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        //更新listview
        updateListView();
        super.onResume();
    }

    //listview item 长按监听
    @OnItemLongClick(R.id.maintab03_listView)
    public boolean itemLongClick(AdapterView<?> parent, View view, int position, long id){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        addDelete(builder,position);
        builder.create();
        builder.show();
        return  true;
    }
    //对话框  删除
    private AlertDialog.Builder addDelete(AlertDialog.Builder builder, final int postion){
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除数据库中对应数据
                String userid=listitem.get(postion).get("userid");
                String bookid=listitem.get(postion).get("bookid");
                String date=listitem.get(postion).get("date");
                String sql="delete from notes where userid='"+userid+"' and bookid='"+bookid+"'"+" and date='"+date+"'";
                NotesManager notesManager=new NotesManager(new DatabaseFac(getActivity()).getWrite());
                try {
                    notesManager.executeSql(sql);
                    Toast.makeText(getActivity(),"删除成功",Toast.LENGTH_SHORT).show();
                    //删除listitem中数据
                    listitem.remove(postion);
                    upListview();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"删除失败",Toast.LENGTH_SHORT).show();
                }




            }
        });
        return builder;
    }
    public void updateListView() {
        try {

            //读取数据库
            listitem.clear();
            String sql = "select userid,name,date,content,notes.bookid from notes,Book where notes.bookid=Book.bookid and userid="+userid;
            DatabaseFac databaseFac = new DatabaseFac(getActivity());
            NotesManager notesManager=new NotesManager(databaseFac.getRead());
            Cursor cursor = notesManager.rawQuery(sql, null);
            System.out.println(sql);
            while (cursor.moveToNext()) {
                HashMap<String,String> map=new HashMap<>();
                map.put("userid",cursor.getString(0));
                map.put("bookname",cursor.getString(1));
                map.put("date",cursor.getString(2));
                map.put("content",cursor.getString(3));
                map.put("bookid",cursor.getString(4));
                System.out.println("maintab03 bookname:"+cursor.getString(1));
                listitem.add(map);
            }
            databaseFac.close();


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Maintab03.updateListView 查询数据库出错");
        }
        //为listview设置适配器
        simpleAdapter = new SimpleAdapter(getActivity(), listitem, R.layout.maintab03_listview_item,
                new String[]{"bookname", "date"},
                new int[]{R.id.maintab03_listitem_bookname, R.id.maintab03_listitem_time});
        simpleAdapter.notifyDataSetInvalidated();
        listView.setAdapter(simpleAdapter);
        //更新停止
        swipeRefreshLayout.setRefreshing(false);
    }
    private void upListview(){
        simpleAdapter = new SimpleAdapter(getActivity(), listitem, R.layout.maintab03_listview_item,
                new String[]{"bookname", "date"},
                new int[]{R.id.maintab03_listitem_bookname, R.id.maintab03_listitem_time});
        simpleAdapter.notifyDataSetInvalidated();
        listView.setAdapter(simpleAdapter);
    }

}

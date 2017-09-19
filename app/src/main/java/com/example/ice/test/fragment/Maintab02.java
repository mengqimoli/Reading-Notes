package com.example.ice.test.fragment;


import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.ice.test.R;
import com.example.ice.test.db.DatabaseFac;
import com.example.ice.test.impleclass.BookManager;
import com.example.ice.test.ui.AddBookActivity;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

import static com.example.ice.test.R.id.maintab02_item_bookname;

/**
 * A simple {@link Fragment} subclass.
 */
public class Maintab02 extends Fragment {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    //存储 bookname 和 content
    private ArrayList<HashMap<String, String>> listitem = new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    private EditText editText;
    private FloatingActionButton floatingActionButton;
    //存储bookname
    private String[] booknames;

    private AutoCompleteTextView autoCompleteTextView;

    @InjectView(R.id.maintab02_bt_chaxun)
    protected Button bt_chaxun;

    public Maintab02() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintab02, container, false);
        ButterKnife.inject(this, view);

        listView = (ListView) view.findViewById(R.id.maintab02_listView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.maintab02_swipeRefresh);
        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.maintab02_fab);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.maintab02_tv_chaxun);
        updateListView();

        //为 autoCompleteTextView 设置 适配器
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, booknames);
        autoCompleteTextView.setAdapter(arrayAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent().setClass(getActivity(), AddBookActivity.class));
            }
        });
        autoCompleteTextView.addTextChangedListener(new EditChangedListener());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateListView();
    }

    //listview 单击 item
    @OnItemClick(R.id.maintab02_listView)
    public void itemClick(AdapterView<?> parent, View view, int position, long id) {
        //    Toast.makeText(getActivity(),position+"  "+id,Toast.LENGTH_SHORT).show();
    }


    //listview item 长按监听
    @OnItemLongClick(R.id.maintab02_listView)
    public boolean itemLongClick(AdapterView<?> parent, View view, int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        addDelete(builder, position);
        builder.create();
        builder.show();
        Toast.makeText(getActivity(), position + "  " + id, Toast.LENGTH_SHORT).show();
        return true;
    }

    //对话框  删除
    private AlertDialog.Builder addDelete(AlertDialog.Builder builder, final int postion) {
        builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //得到书籍对应的id
                String content = listitem.get(postion).get("content");
                System.out.println("maintab02 content:" + content.split(" ")[0]);
                String bookid = content.split(" ")[1].split(":")[1];
                //从数据库中删除
                BookManager bookManager = new BookManager(new DatabaseFac(getActivity()).getWrite());

                bookManager.delete("");

                listitem.remove(postion);
                upListview();
                Toast.makeText(getActivity(), "删除", Toast.LENGTH_SHORT).show();
            }
        });
        return builder;
    }

    //添加 button 监听
    @OnClick(R.id.maintab02_bt_chaxun)
    public void cxButtonClick(View view) {
        showQueryResult(autoCompleteTextView.getText().toString());
    }

    /**
     * 用来更新 书籍管理中的书籍信息  读取数据库
     */
    public void updateListView() {
        try {

            //读取数据库
            listitem.clear();
            String sql = "select * from Book";
            DatabaseFac databaseFac = new DatabaseFac(getActivity());
            BookManager bookManager = new BookManager(databaseFac.getRead());
            Cursor cursor = bookManager.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                HashMap<String, String> map = new HashMap<>();
                String bookname = "";
                String content = "";
                String line = "";
                for (String s : cursor.getColumnNames()) {
                    if (s.equals("name")) {
                        bookname = "书籍名称:" + cursor.getString(cursor.getColumnIndex(s));
                    } else if (s.equals("bookid")) {
                        content += " " + "书籍id" + ":" + cursor.getString(cursor.getColumnIndex(s));
                    } else if (s.equals("author")) {
                        content += " " + "作者" + ":" + cursor.getString(cursor.getColumnIndex(s));
                    } else if (s.equals("press")) {
                        content += " " + "出版社" + ":" + cursor.getString(cursor.getColumnIndex(s));
                    } else if (s.equals("ISBN")) {
                        content += " " + "ISBN" + ":" + cursor.getString(cursor.getColumnIndex(s));
                    }
                    line = "----------------------------------------------------------------------";
                }
                map.put("bookname", bookname);
                map.put("content", content);
                map.put("line", line);
                listitem.add(map);
            }
            databaseFac.close();
            //booknames赋值
            booknames = getBookNames();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Maintab02.updateListView 查询数据库出错");
        }
        //为listview设置适配器
        simpleAdapter = new SimpleAdapter(getActivity(), listitem, R.layout.maintab02_listview_item,
                new String[]{"bookname", "content", "line"},
                new int[]{maintab02_item_bookname, R.id.maintab02_item_content, R.id.maintab02_item_line});
        simpleAdapter.notifyDataSetInvalidated();
        listView.setAdapter(simpleAdapter);
        //更新停止
        swipeRefreshLayout.setRefreshing(false);
    }

    //当listitem改变后 更新listview
    private void upListview() {
        simpleAdapter = new SimpleAdapter(getActivity(), listitem, R.layout.maintab02_listview_item,
                new String[]{"bookname", "content", "line"},
                new int[]{maintab02_item_bookname, R.id.maintab02_item_content, R.id.maintab02_item_line});
        simpleAdapter.notifyDataSetInvalidated();
        listView.setAdapter(simpleAdapter);
    }

    //将查询结果写入listview
    public void showQueryResult(String name) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        for (HashMap<String, String> map : listitem) {
            if (map.get("bookname").startsWith(name)) {
                list.add(map);
            }
        }
        simpleAdapter = new SimpleAdapter(getActivity(), list, R.layout.maintab02_listview_item,
                new String[]{"bookname", "content", "line"},
                new int[]{maintab02_item_bookname, R.id.maintab02_item_content, R.id.maintab02_item_line});
        simpleAdapter.notifyDataSetInvalidated();
        listView.setAdapter(simpleAdapter);
    }

    //从listitem 中 获得书名
    public String[] getBookNames() {
        String[] booknames = new String[listitem.size()];
        for (int i = 0; i < listitem.size(); i++) {
            booknames[i] = listitem.get(i).get("bookname");
        }
        return booknames;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                updateListView();
            }
        });
    }

    //监听 文本的输入

    class EditChangedListener implements TextWatcher {

        private CharSequence temp = ""; //监听前文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            showQueryResult(s.toString());
        }
    }

}

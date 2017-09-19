package com.example.ice.test.ui;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ice.test.R;
import com.example.ice.test.db.DatabaseFac;
import com.example.ice.test.entity.BookClassify;
import com.example.ice.test.impleclass.BookManager;
import com.example.ice.test.impleclass.ClassifyManager;
import com.example.ice.test.impleclass.NotesManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddClassifyActivity extends AppCompatActivity {

    @InjectView(R.id.addClassify_bookname)
    protected Spinner sp_bookname;
    @InjectView(R.id.addClassify_classify)
    protected EditText et_classify;
    @InjectView(R.id.addClassify_add)
    protected Button bt_add;

    String[] bookids;
    String[] booknames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classify);
        ButterKnife.inject(this);
    }
    protected void onResume() {
        super.onResume();
        getBookNames();
        sp_bookname.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, booknames));
    }

    @OnClick(R.id.addClassify_add)
    public void addButtonClick(){
        String bookid=bookids[sp_bookname.getSelectedItemPosition()];
        String classify=et_classify.getText().toString();
        BookClassify bookClassify=new BookClassify();
        bookClassify.setBookid(bookid);
        bookClassify.setClassifyName(classify);

        try{
            ClassifyManager classifyManager=new ClassifyManager(new DatabaseFac(getApplicationContext()).getWrite());
            classifyManager.insert(bookClassify);
            Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
            this.finish();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("addClassifyActivity: insert 错误！");
        }
    }

    //得到所有的书名
    private void getBookNames(){

        String sql = "select bookid,name from Book";
        DatabaseFac databaseFac = new DatabaseFac(getApplicationContext());
        BookManager bookManager = new BookManager(databaseFac.getRead());
        Cursor cursor = bookManager.rawQuery(sql, null);
        int i=0;
        bookids=new String[cursor.getCount()];
        booknames=new String[cursor.getCount()];
        while (cursor.moveToNext()){
            System.out.println(cursor.getString(0)+" "+cursor.getString(1));
            bookids[i]=cursor.getString(0);
            booknames[i]=cursor.getString(1);
            i++;
        }
        cursor.close();
        databaseFac.close();
    }
}

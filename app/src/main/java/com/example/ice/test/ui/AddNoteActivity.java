package com.example.ice.test.ui;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ice.test.MyApplication;
import com.example.ice.test.R;
import com.example.ice.test.db.DatabaseFac;
import com.example.ice.test.entity.Note;
import com.example.ice.test.impleclass.BookManager;
import com.example.ice.test.impleclass.NotesManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemSelected;

public class AddNoteActivity extends AppCompatActivity {

    @InjectView(R.id.addNote_bookname)
    protected Spinner sp_bookname;
    @InjectView(R.id.addNote_time)
    protected EditText et_time;
    @InjectView(R.id.addNote_content)
    protected EditText et_content;

    private String userid;

    String[] bookids;
    String[] booknames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        ButterKnife.inject(this);

        userid=((MyApplication)getApplication()).getUsernmame();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getBookNames();
        sp_bookname.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, booknames));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间);
        et_time.setText(df.format(new Date()));
    }
    //spinner 添加监听事件
    @OnItemSelected(R.id.addNote_bookname)
    public void spinnerOnItemSelected(){

    }

    //添加笔记触发事件
    public void addNoteClick(View view){
        String bookid=bookids[sp_bookname.getSelectedItemPosition()];
        String time=et_time.getText().toString();
        String content=et_content.getText().toString();
        Note note=new Note();
        note.setUserid(userid);
        note.setBookid(bookid);
        note.setDate(time);
        note.setContent(content);
        try{
            NotesManager notesManager=new NotesManager(new DatabaseFac(getApplicationContext()).getWrite());
            notesManager.insert(note);
            Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
            this.finish();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("addNoteActivity: insert 错误！");
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

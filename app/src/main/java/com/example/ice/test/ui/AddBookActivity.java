package com.example.ice.test.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ice.test.R;
import com.example.ice.test.db.DBHelper;
import com.example.ice.test.db.DatabaseFac;
import com.example.ice.test.entity.Book;
import com.example.ice.test.impleclass.BookManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddBookActivity extends AppCompatActivity {

    @InjectView(R.id.addBook_bookid)
    protected EditText et_bookid;
    @InjectView(R.id.addBook_name)
    protected EditText et_name;
    @InjectView(R.id.addBook_author)
    protected EditText et_author;
    @InjectView(R.id.addBook_press)
    protected EditText et_press;
    @InjectView(R.id.addBook_ISBN)
    protected EditText et_ISBN;
    @InjectView(R.id.addBook_classify)
    protected EditText et_classify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        ButterKnife.inject(this);
    }
    @OnClick(R.id.addBook_addBook)
    public void addBookClick(View view){
        Book book=new Book();
        book.setAuthor(et_author.getText().toString());
        book.setBookId(et_bookid.getText().toString());
        book.setClassify(et_classify.getText().toString());
        book.setPress(et_press.getText().toString());
        book.setISBN(et_ISBN.getText().toString());
        book.setName(et_name.getText().toString());
        String message="";
        try{
            BookManager bookManager=new BookManager(new DatabaseFac(getApplicationContext()).getWrite());
            bookManager.insert(book);
            et_press.setText(null);
            et_author.setText(null);
            et_press.setText(null);
            et_bookid.setText(null);
            et_classify.setText(null);
            et_ISBN.setText(null);
            et_name.setText(null);
            message="插入成功";
            //退出当前activity
            this.finish();

        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("addBookActivity 插入书籍出错");
            message="插入失败";
        }
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();

    }
}

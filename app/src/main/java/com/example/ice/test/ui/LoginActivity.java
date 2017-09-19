package com.example.ice.test.ui;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ice.test.MainActivity;
import com.example.ice.test.MyApplication;
import com.example.ice.test.R;
import com.example.ice.test.db.DatabaseFac;
import com.example.ice.test.entity.User;
import com.example.ice.test.impleclass.UserManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.login_et_username)
    protected EditText et_username;
    @InjectView(R.id.login_et_password)
    protected EditText et_password;
    @InjectView(R.id.login_bt_login)
    protected Button bt_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

   //     插入用户 useid=20142967  password=123456 name=zhangsan

    }
    //按下登录按钮
    public void loginButtonClick(View view){
        String userid=et_username.getText().toString();
        String password=et_password.getText().toString();
        String sql="select password from user where userid=?";
        DatabaseFac databaseFac=new DatabaseFac(getApplicationContext());
        UserManager userManager=new UserManager(databaseFac.getRead());
        Cursor cursor=userManager.rawQuery(sql,new String[]{userid});
        if(cursor.moveToNext()){
            if(cursor.getString(0).equals(password))
            {
                System.out.println(userid+" 登录成功***************************");
                MyApplication myApplication=(MyApplication)getApplication();
                myApplication.setUsernmame(userid);
                startActivity(new Intent().setClass(getApplicationContext(),MainActivity.class));

            }
        }
        if(cursor!=null){
            cursor.close();
        }
        databaseFac.close();
        this.finish();
   /*     MyApplication myApplication=(MyApplication)getApplication();
        myApplication.setUsernmame("123456");



        startActivity(new Intent().setClass(getApplicationContext(),MainActivity.class));
        this.finish();*/
    }
    @OnClick(R.id.login_bt_zhuce)
    public void zhuceButtonClick(View view){
        startActivity(new Intent().setClass(getApplicationContext(),ZhuCeActivity.class));
    }
}

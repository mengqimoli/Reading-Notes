package com.example.ice.test.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ice.test.R;
import com.example.ice.test.db.DatabaseFac;
import com.example.ice.test.entity.User;
import com.example.ice.test.impleclass.UserManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ZhuCeActivity extends AppCompatActivity {

    @InjectView(R.id.zhuce_bt_zhuce)
    protected Button bt_zhuce;
    @InjectView(R.id.zhuce_et_userid)
    protected EditText et_userid;
    @InjectView(R.id.zhuce_et_username)
    protected EditText et_username;
    @InjectView(R.id.zhuce_et_password)
    protected EditText et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        ButterKnife.inject(this);
    }
    @OnClick(R.id.zhuce_bt_zhuce)
    public void zhuceButtonClick(View view){
        String userid=et_userid.getText().toString();
        String username=et_username.getText().toString();
        String password=et_password.getText().toString();

        try{
            DatabaseFac databaseFac=new DatabaseFac(getApplicationContext());
            UserManager userManager=new UserManager(databaseFac.getWrite());
            User user=new User();
            user.setName(username);
            user.setUserid(userid);
            user.setPassword(password);
            userManager.insert(user);
            databaseFac.close();
            Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
            this.finish();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
        }

    }

}

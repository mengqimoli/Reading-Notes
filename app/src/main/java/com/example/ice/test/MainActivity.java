package com.example.ice.test;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ice.test.db.DBHelper;
import com.example.ice.test.fragment.Maintab01;
import com.example.ice.test.fragment.Maintab02;
import com.example.ice.test.fragment.Maintab03;
import com.example.ice.test.ui.HuiZongActivity;
import com.example.ice.test.ui.TongJiActivity;
import com.example.ice.test.ui.VersionInfoActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Maintab01 maintab01;
    private Maintab02 maintab02;
    private Maintab03 maintab03;
    private FragmentManager fragmentManager;

    protected LinearLayout linearLayout_maintab01;
    protected LinearLayout linearLayout_maintab02;
    protected LinearLayout linearLayout_maintab03;
    //再按一次退出
    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        fragmentManager=getFragmentManager();
        setTabSelection(0);
    }

    //点击事件 获得点击的控件的 id值
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tabbar_main_maintab01:
                setTabSelection(0);break;
            case R.id.tabbar_main_maintab02:
                setTabSelection(1);break;
            case R.id.tabbar_main_maintab03:
                setTabSelection(2);break;
        }
    }

    public void initView(){
        linearLayout_maintab03=(LinearLayout)findViewById(R.id.tabbar_main_maintab03);
        linearLayout_maintab01=(LinearLayout)findViewById(R.id.tabbar_main_maintab01);
        linearLayout_maintab02=(LinearLayout)findViewById(R.id.tabbar_main_maintab02);

        linearLayout_maintab02.setOnClickListener(this);
        linearLayout_maintab01.setOnClickListener(this);
        linearLayout_maintab03.setOnClickListener(this);
    }
    //设置某个 fragment 显示
    private void setTabSelection(int index){
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index){
            case 0:
                if(maintab01==null){
                    maintab01=new Maintab01();
                    transaction.add(R.id.fragment_main,maintab01);
                }else{
                    transaction.show(maintab01);
                }
                break;
            case 1:
                if(maintab02==null){
                    maintab02=new Maintab02();
                    transaction.add(R.id.fragment_main,maintab02);
                }else{
                    transaction.show(maintab02);
                }
                break;
            case 2:
                if(maintab03==null){
                    maintab03=new Maintab03();
                    transaction.add(R.id.fragment_main,maintab03);
                }else{
                    transaction.show(maintab03);
                }
                break;
        }
        transaction.commit();

    }
    //隐藏 fragment
    private void hideFragment(FragmentTransaction transaction){
        if(maintab01!=null){
            transaction.hide(maintab01);
        }
        if(maintab02!=null){
            transaction.hide(maintab02);
        }
        if(maintab03!=null){
            transaction.hide(maintab03);
        }
    }
    //在按一次返回键退出

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //menu 点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==R.id.action_huizong) {
            startActivity(new Intent().setClass(getApplicationContext(), HuiZongActivity.class));
            return true;
        }
        if (id==R.id.action_tongji){
            startActivity(new Intent().setClass(getApplicationContext(), TongJiActivity.class));
            return true;
        }
        if(id==R.id.action_version){
            startActivity(new Intent().setClass(getApplicationContext(), VersionInfoActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

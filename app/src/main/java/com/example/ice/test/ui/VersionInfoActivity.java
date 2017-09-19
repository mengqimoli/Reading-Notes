package com.example.ice.test.ui;
/**
 * 展示 版本信息
 */

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ice.test.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class VersionInfoActivity extends AppCompatActivity {

    @InjectView(R.id.versionInfo_tv)
    protected TextView tv_versionInfo;

    private String version = "1.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_info);
        ButterKnife.inject(this);

        PackageManager packageManager = getApplicationContext().getPackageManager();
        try {
            PackageInfo pi = packageManager.getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tv_versionInfo.setText("当前版本是：" + version + "\n" + "版本时间：2017/5/2" + "\n" + "作者：" + "WangYiLin");
    }
}

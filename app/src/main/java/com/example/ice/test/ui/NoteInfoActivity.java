package com.example.ice.test.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ice.test.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NoteInfoActivity extends AppCompatActivity {

    @InjectView(R.id.noteInfo_tv_content)
    protected TextView tv_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_info);
        ButterKnife.inject(this);

        Intent intent=getIntent();
        tv_content.setText(intent.getStringExtra("content"));
    }
}

package com.example.ice.test;

import android.app.Application;

/**
 * Created by ice on 4/25/17.
 */

public class MyApplication extends Application{
    private String userid;

    public String getUsernmame() {
        return userid;
    }

    public void setUsernmame(String usernmame) {
        this.userid = usernmame;
    }
}

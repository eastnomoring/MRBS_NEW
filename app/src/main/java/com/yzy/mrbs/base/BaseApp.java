package com.yzy.mrbs.base;

import android.app.Application;

/**
 * Created by ZhiYuan on 2016/4/27.
 */
public class BaseApp extends Application {
    private String s;
    private long l;
    private int i;

    public int getInt () {
        return i;
    }

    public void setInt (int i) {
        this.i = i;
    }

    public long getLong () {
        return l;
    }

    public void setLong (long l) {
        this.l = l;
    }

    public String getString () {
        return s;
    }

    public void setString (String s) {
        this.s = s;
    }
}

package com.yzy.mrbs.model;

import com.yzy.mrbs.base.BaseModel;

/**
 * Created by ZhiYuan on 2016/5/13.
 */
public class Notice extends BaseModel {

    // model columns
    public final static String COL_ID = "id";
    public final static String COL_MESSAGE = "message";

    private String id;
    private String message;

    public Notice () {}

    public String getId () {
        return this.id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getMessage () {
        return this.message;
    }

    public void setMessage (String message) {
        this.message = message;
    }
}
